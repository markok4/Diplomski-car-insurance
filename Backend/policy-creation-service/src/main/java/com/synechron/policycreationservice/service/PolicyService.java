package com.synechron.policycreationservice.service;

import com.synechron.policycreationservice.dto.PolicyDTO;
import com.synechron.policycreationservice.dto.kafka.*;
import com.synechron.policycreationservice.dto.kafka.PolicySearchCarRequestMessage;
import com.synechron.policycreationservice.dto.kafka.PolicySearchCarResponseMessage;
import com.synechron.policycreationservice.dto.kafka.PolicySearchSubscriberRequestMessage;
import com.synechron.policycreationservice.dto.kafka.PolicySearchSubscriberResponseMessage;
import com.synechron.policycreationservice.mapper.PolicyMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.synechron.policycreationservice.dto.ProposalDTO;
import com.synechron.policycreationservice.model.Policy;
import com.synechron.policycreationservice.repository.PolicyRepository;
import com.synechron.policycreationservice.utils.SearchData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PolicyService {
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private PolicyMapper policyDTOMapper;
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String SUBSCRIBER_REQUEST_TOPIC = "policy.subscriber.request";
    private final String CAR_REQUEST_TOPIC = "policy.car.request";
    private final String SUBSCRIBER_RESPONSE_TOPIC = "policy.subscriber.response";
    private final String CAR_RESPONSE_TOPIC = "policy.car.response";

    private final String USER_REQUEST_TOPIC = "user.request.dobar1";
    private final String USER_RESPONSE_TOPIC = "user.response111";
    private final Map<String, CompletableFuture<List<PolicyDTO>>> futuresMap = new ConcurrentHashMap<>();
    private final Map<String, SearchData> progressMap = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<Page<PolicyDTO>>> userEmailFutures = new ConcurrentHashMap<>();
    private CompletableFuture<List<PolicyDTO>> policyDtoFuture= new CompletableFuture<>();
    private JsonSerializer<PolicyUserIdRequestMessage> policyUserIdRequestMessageSerializer = new JsonSerializer<>();
    private JsonDeserializer<PolicyUserIdResponseMessage> policyUserIdResponseMessageDeserializer =new JsonDeserializer<>(PolicyUserIdResponseMessage.class);
    private JsonSerializer<PolicySearchSubscriberRequestMessage> subscriberRequestMessageSerializer = new JsonSerializer<>();
    private JsonDeserializer<PolicySearchSubscriberResponseMessage> subscriberResponseMessageDeserializer = new JsonDeserializer<>(PolicySearchSubscriberResponseMessage.class);
    private JsonSerializer<PolicySearchCarRequestMessage> carRequestMessageSerializer = new JsonSerializer<>();
    private JsonDeserializer<PolicySearchCarResponseMessage> carResponseMessageDeserializer = new JsonDeserializer<>(PolicySearchCarResponseMessage.class);

    public Page<Policy> getAll(int page, int size, String sortBy) {
        Pageable pageable;
        if (sortBy.equals("plates")) {
            pageable = PageRequest.of(page, size, Sort.by("proposal.carPlates")); // Assuming proposal.carPlates is the correct field
        } else if (sortBy.equals("date")) {
            pageable = PageRequest.of(page, size, Sort.by("dateSigned").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        return policyRepository.findAll(pageable);
    }

    public Page<Policy> getPoliciesBySalesAgentEmail(int page, int size, String salesAgentEmail, String sortBy) {
        Pageable pageable;
        if (sortBy.equals("plates")) {
            pageable = PageRequest.of(page, size, Sort.by("proposal.carPlates")); // Assuming proposal.carPlates is the correct field
        } else if (sortBy.equals("date")) {
            pageable = PageRequest.of(page, size, Sort.by("dateSigned").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        return policyRepository.findPoliciesBySalesAgentEmail(salesAgentEmail, pageable);
    }

    public PolicyDTO getById(Long id) throws JsonProcessingException {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("An error occurred while retrieving the policy by ID: " + id));
        ProposalDTO proposalDTO = proposalService.getById(policy.getProposal().getId());
        PolicyDTO policyDTO = policyDTOMapper.toDTO(policy);
        policyDTO.setProposal(proposalDTO);
        return policyDTO;
    }

    public CompletableFuture<List<PolicyDTO>> search(int page, int size, String nameOrEmail, LocalDate date, Long brandId, Long modelId, Long year) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<List<PolicyDTO>> future = new CompletableFuture<>();
        futuresMap.put(correlationId, future);

        SearchData searchData = new SearchData();
        searchData.setDate(date);
        if (nameOrEmail == null) {
            searchData.setHasUserServiceResponded(true);
        }
        if (brandId == null && modelId == null && year == null) {
            searchData.setHasCarServiceResponded(true);
        }

        progressMap.put(correlationId, searchData);

        if (!searchData.isHasUserServiceResponded()) {
            getSubscriberIdsByNameOrEmail(correlationId, nameOrEmail);
        }
        if (!searchData.isHasCarServiceResponded()) {
            getCarIdsByBrandOrModelOrYear(correlationId, brandId, modelId, year);
        }
        if (searchData.isComplete()) {
            completeSearch(correlationId);
        }
        return future;
    }


    public void getCarIdsByBrandOrModelOrYear(String correlationId, Long brandId, Long modelId, Long year) {
        PolicySearchCarRequestMessage message = new PolicySearchCarRequestMessage(correlationId, brandId, modelId, year);
        kafkaTemplate.send(CAR_REQUEST_TOPIC, carRequestMessageSerializer.serialize(CAR_REQUEST_TOPIC, message));
    }

    @KafkaListener(topics = SUBSCRIBER_RESPONSE_TOPIC, groupId = "policy-service-group")
    public void listenForSubscriberResponse(byte[] responseData) {
        PolicySearchSubscriberResponseMessage responseMessage = subscriberResponseMessageDeserializer.deserialize(SUBSCRIBER_RESPONSE_TOPIC, responseData);

        List<Long> subscriberIds = responseMessage.getSubscriberIds();
        System.out.println(subscriberIds);
        String correlationId = responseMessage.getCorrelationId();
        SearchData searchData = progressMap.get(correlationId);
        searchData.setHasUserServiceResponded(true);
        searchData.setSubscriberIds(subscriberIds);

        if (searchData.isComplete()) {
            completeSearch(correlationId);
        }

    }

    @KafkaListener(topics = CAR_RESPONSE_TOPIC, groupId = "policy-service-group")
    public void listenForCarResponse(byte[] responseData) {
        PolicySearchCarResponseMessage responseMessage = carResponseMessageDeserializer.deserialize(CAR_RESPONSE_TOPIC, responseData);

        List<Long> carIds = responseMessage.getCarIds();

        String correlationId = responseMessage.getCorrelationId();
        SearchData searchData = progressMap.get(correlationId);
        searchData.setHasCarServiceResponded(true);
        searchData.setCarIds(carIds);

        if (searchData.isComplete()) {
            completeSearch(correlationId);
        }
    }
    private void completeSearch(String correlationId) {
        SearchData searchData = progressMap.remove(correlationId);
        CompletableFuture<List<PolicyDTO>> future = futuresMap.remove(correlationId);
        List<Policy> policies;
        LocalDate date = searchData.getDate();
        List<Long> carIds = searchData.getCarIds();
        List<Long> subscriberIds = searchData.getSubscriberIds();
        if (subscriberIds == null) {
            if (carIds == null) {
                if (date == null) {
                    policies = policyRepository.findAll();
                } else {
                    policies = policyRepository.findAllByDateSigned(date);
                }
            } else {
                if (date == null) {
                    policies = policyRepository.findAllByProposalCarIdIn(carIds);
                } else {
                    policies = policyRepository.findAllByProposalCarIdInAndDateSigned(carIds, date);
                }
            }
        } else {
            if (carIds == null) {
                if (date == null) {
                    policies = policyRepository.findAllBySubscriberIdIn(subscriberIds);
                } else {
                    policies = policyRepository.findAllBySubscriberIdInAndDateSigned(subscriberIds, date);
                }
            } else {
                if (date == null) {
                    policies = policyRepository.findAllByProposalCarIdInAndSubscriberIdIn(carIds, subscriberIds);
                } else {
                    policies = policyRepository.findAllByProposalCarIdInAndSubscriberIdInAndDateSigned(carIds, subscriberIds, date);
                }
            }
        }

        if (future != null) {
            future.complete(policyDTOMapper.listToDTO(policies));
        }
    }

    public void getSubscriberIdsByNameOrEmail(String correlationId, String nameOrEmail) {
        PolicySearchSubscriberRequestMessage message = new PolicySearchSubscriberRequestMessage(correlationId, nameOrEmail);
        kafkaTemplate.send(SUBSCRIBER_REQUEST_TOPIC, subscriberRequestMessageSerializer.serialize(SUBSCRIBER_REQUEST_TOPIC, message));
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public CompletableFuture<Page<PolicyDTO>> sendEmailToUserService(int page, int size) {
        CompletableFuture<Page<PolicyDTO>> future = new CompletableFuture<>();
        String correlationId = UUID.randomUUID().toString();
        userEmailFutures.put(correlationId, future);
        String userEmail = getCurrentUserEmail();
        PolicyUserIdRequestMessage requestMessage = new PolicyUserIdRequestMessage(correlationId, userEmail, page, size);

        kafkaTemplate.send(USER_REQUEST_TOPIC, policyUserIdRequestMessageSerializer.serialize(USER_REQUEST_TOPIC, requestMessage));
        return future;
    }

    @KafkaListener(topics = USER_RESPONSE_TOPIC, groupId = "policy-service-group")
    public void processUserId(byte[] responseData) {
        PolicyUserIdResponseMessage responseMessage = policyUserIdResponseMessageDeserializer.deserialize(USER_RESPONSE_TOPIC, responseData);

        String correlationId = responseMessage.getCorrelationId();
        Long userId = responseMessage.getUserId();
        int page = responseMessage.getPage();
        int size = responseMessage.getSize();
        System.out.println("ID: " + userId + ", Page: " + page + ", Size: " + size);

        CompletableFuture<Page<PolicyDTO>> future = userEmailFutures.get(correlationId);
        if (future != null) {
            List<Policy> policiesList = policyRepository.findAllBySubscriberId(userId);
            List<PolicyDTO> policyDTOList = policiesList.stream()
                    .map(policyDTOMapper::toDTO)
                    .collect(Collectors.toList());
            Page<PolicyDTO> policyDTOsPage = new PageImpl<>(policyDTOList, PageRequest.of(page, size), policyDTOList.size());
            future.complete(policyDTOsPage);
        }
    }



}