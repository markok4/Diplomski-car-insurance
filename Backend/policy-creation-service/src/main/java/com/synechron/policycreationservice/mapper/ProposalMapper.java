package com.synechron.policycreationservice.mapper;

import com.synechron.policycreationservice.dto.ProposalDTO;
import com.synechron.policycreationservice.model.Proposal;
import com.synechron.policycreationservice.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProposalMapper implements IMapper<Proposal, ProposalDTO> {

    private final InsurancePlanMapper insurancePlanMapper;

    public ProposalMapper(InsurancePlanMapper insurancePlanMapper) {
        this.insurancePlanMapper = insurancePlanMapper;
    }

    @Override
    public Proposal toEntity(ProposalDTO proposalDto) {
        if (proposalDto == null) {
            return null;
        }

        Proposal proposal = new Proposal();
        proposal.setId(proposalDto.getId());
        proposal.setIsValid(proposalDto.getIsValid());
        try {
            proposal.setCreationDate(DateUtil.stringToDate(proposalDto.getCreationDate()));
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error parsing date: " + proposalDto.getCreationDate(), e);
        }
        proposal.setProposalStatus(proposalDto.getProposalStatus());
        proposal.setAmount(proposalDto.getAmount());
        proposal.setCarPlates(proposalDto.getCarPlates());
        proposal.setIsDeleted(proposalDto.getIsDeleted());
        proposal.setInsurancePlan(insurancePlanMapper.toEntity(proposalDto.getInsurancePlan()));

        return proposal;
    }

    @Override
    public ProposalDTO toDTO(Proposal proposal) {
        if (proposal == null) {
            return null;
        }

        ProposalDTO proposalDto = new ProposalDTO();
        proposalDto.setId(proposal.getId());
        proposalDto.setIsValid(proposal.getIsValid());
        proposalDto.setCreationDate(DateUtil.dateToString(proposal.getCreationDate()));
        proposalDto.setProposalStatus(proposal.getProposalStatus());
        proposalDto.setAmount(proposal.getAmount());
        proposalDto.setCarPlates(proposal.getCarPlates());
        proposalDto.setIsDeleted(proposal.getIsDeleted());
        proposalDto.setInsurancePlan(insurancePlanMapper.toDTO(proposal.getInsurancePlan()));
        proposalDto.setSubscriberId(proposal.getSubscriberId());

        return proposalDto;
    }

    /*
    public ProposalResponseDTO toResponseDTO(Proposal proposal) {
        return ProposalResponseDTO.builder()
                .id(proposal.getId())
                .isValid(proposal.getIsValid())
                .proposalStatus(proposal.getProposalStatus().name())
                .amount(proposal.getAmount())
                .creationDate(proposal.getCreationDate())
                .carPlates(proposal.getCarPlates())
                .salesAgentEmail(proposal.getSalesAgentEmail())
                .isDeleted(proposal.getIsDeleted())
                .build();
    }
     */

    @Override
    public List<ProposalDTO> listToDTO(List<Proposal> proposals) {
        if (proposals == null) {
            return Collections.emptyList();
        }
        return proposals.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
