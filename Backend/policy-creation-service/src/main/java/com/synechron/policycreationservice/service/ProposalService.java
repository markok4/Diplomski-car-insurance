package com.synechron.policycreationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synechron.policycreationservice.consumer.CarConsumer;
import com.synechron.policycreationservice.dto.kafka.CarDTO;
import com.synechron.policycreationservice.dto.ProposalDTO;
import com.synechron.policycreationservice.dto.ProposalInitializeDTO;
import com.synechron.policycreationservice.dto.kafka.ProposalSubscriberValidMessage;
import com.synechron.policycreationservice.mapper.ProposalMapper;
import com.synechron.policycreationservice.model.Proposal;
import com.synechron.policycreationservice.model.ProposalStatus;
import com.synechron.policycreationservice.producer.ProposalProducer;
import com.synechron.policycreationservice.repository.ProposalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

import java.util.List;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalMapper proposalDTOMapper;
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String IS_SUBSCRIBER_VALID_REQUEST_TOPIC = "subscriber.validity.request";
    private final String IS_SUBSCRIBER_VALID_RESPONSE_TOPIC = "subscriber.validity.response";
    private JsonDeserializer<ProposalSubscriberValidMessage> proposalSubscriberValidMessageJsonDeserializer = new JsonDeserializer<>(ProposalSubscriberValidMessage.class);
    private JsonSerializer<ProposalSubscriberValidMessage> proposalSubscriberValidMessageJsonSerializer = new JsonSerializer<>();
    private final Map<String, CompletableFuture<ProposalDTO>> futuresMap = new ConcurrentHashMap<>();
    @Autowired
    private ProposalProducer proposalProducer;

    @Autowired
    private CarConsumer carConsumer;

    public Page<ProposalDTO> getAll(Pageable pageable) {
        try {
            Page<Proposal> proposalPage = proposalRepository.findAll(pageable);
            List<ProposalDTO> proposalDtoList = proposalDTOMapper.listToDTO(proposalPage.getContent());
            return new PageImpl<>(proposalDtoList, pageable, proposalPage.getTotalElements());
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<ProposalDTO> getAllNotDeleted(Pageable pageable) {
        try {
            Page<Proposal> proposalPage = proposalRepository.findAllByIsDeletedFalse(pageable);
            List<ProposalDTO> proposalDtoList = proposalDTOMapper.listToDTO(proposalPage.getContent());
            return new PageImpl<>(proposalDtoList, pageable, proposalPage.getTotalElements());
        } catch (Exception e) {
            throw e;
        }
    }

    public ProposalDTO create(ProposalInitializeDTO proposalData) {
        Authentication authentication = SecurityContextHolder.
                getContext
                        ().getAuthentication();
        String userEmail = authentication.getName();
        Proposal proposal = Proposal.builder()
                .salesAgentEmail(userEmail)
                .proposalStatus(ProposalStatus.INITIALIZED)
                .isDeleted(false)
                .build();
        return proposalDTOMapper.toDTO(proposalRepository.save(proposal));
    }

    public CompletableFuture<ProposalDTO> setSubscriber(long proposalId, long subscriberId) {

        Optional<Proposal> result = proposalRepository.findById(proposalId);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Proposal not found");
        }
        if (result.get().getProposalStatus() == ProposalStatus.CONFIRMED) {
            throw new IllegalStateException("Proposal already confirmed");
        }

        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<ProposalDTO> future = new CompletableFuture<>();
        futuresMap.put(correlationId, future);

        ProposalSubscriberValidMessage message = new ProposalSubscriberValidMessage(correlationId, subscriberId, proposalId);
        kafkaTemplate.send(IS_SUBSCRIBER_VALID_REQUEST_TOPIC, proposalSubscriberValidMessageJsonSerializer.serialize(IS_SUBSCRIBER_VALID_REQUEST_TOPIC, message));
        return future;
    }

    @KafkaListener(topics = IS_SUBSCRIBER_VALID_RESPONSE_TOPIC, groupId = "policy-service-group")
    public void listenForSubscriberResponse(byte[] responseData) {
        ProposalSubscriberValidMessage responseMessage = proposalSubscriberValidMessageJsonDeserializer.deserialize(IS_SUBSCRIBER_VALID_RESPONSE_TOPIC, responseData);
        CompletableFuture<ProposalDTO> future = futuresMap.remove(responseMessage.getCorrelationId());

        try {
            if (responseMessage.getSubscriberId() == -1) {
                throw new EntityNotFoundException("Subscriber not found.");
            }

            Optional<Proposal> result = proposalRepository.findById(responseMessage.getProposalId());
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Proposal not found.");
            }
            Proposal proposal = result.get();
            proposal.setSubscriberId(responseMessage.getSubscriberId());
            proposal.setProposalStatus(ProposalStatus.SUBSCRIBER_ADDED);
            Proposal savedProposal = proposalRepository.save(proposal);

            if (future != null) {
                future.complete(proposalDTOMapper.toDTO(savedProposal));
            }
        } catch (EntityNotFoundException e) {
            if (future != null) {
                future.completeExceptionally(e);
            }
        }
    }

    // TODO implementirati dobavljanje info o Subscriber-u i Driver-u iz user-management servisa
    public ProposalDTO getById(Long id) throws JsonProcessingException {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposal not found for ID: " + id));

        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<CarDTO> carFuture = new CompletableFuture<>();
        carConsumer.addCarFuture(correlationId, carFuture);

        proposalProducer.sendIdCar(proposal.getCarId(), correlationId);

        CarDTO car;
        try {
            car = carFuture.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "Car details not available in time");
        }

        ProposalDTO proposalDTO = proposalDTOMapper.toDTO(proposal);
        proposalDTO.setCar(car);
        return proposalDTO;
    }

}
