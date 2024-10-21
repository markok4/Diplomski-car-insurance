package com.synechron.policycreationservice.mapper;

import com.synechron.policycreationservice.dto.PolicyDTO;
import com.synechron.policycreationservice.model.Policy;
import com.synechron.policycreationservice.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PolicyMapper implements IMapper<Policy, PolicyDTO> {

    private final ProposalMapper proposalMapper;

    public PolicyMapper(ProposalMapper proposalMapper) {
        this.proposalMapper = proposalMapper;
    }
    @Override
    public Policy toEntity(PolicyDTO policyDto) {
        if (policyDto == null) {
            return null;
        }
        Policy policy = new Policy();
        policy.setId(policyDto.getId());
        try{
            policy.setDateSigned(DateUtil.stringToDate(policyDto.getDateSigned()));
        }catch(DateTimeParseException e){
            throw new RuntimeException("Error parsing date: " + policyDto.getDateSigned(), e);
        }
        try{
            policy.setExpiringDate(DateUtil.stringToDate(policyDto.getExpiringDate()));
        }catch(DateTimeParseException e){
            throw new RuntimeException("Error parsing date: " + policyDto.getExpiringDate(), e);
        }
        try{
            policy.setMoneyReceivedDate(DateUtil.stringToDate(policyDto.getMoneyReceivedDate()));
        }catch(DateTimeParseException e){
            throw new RuntimeException("Error parsing date: " + policyDto.getMoneyReceivedDate(), e);
        }
        policy.setAmount(policyDto.getAmount());
        policy.setIsDeleted(policyDto.getIsDeleted());
        policy.setProposal(proposalMapper.toEntity(policyDto.getProposal()));

        return policy;
    }

    @Override
    public PolicyDTO toDTO(Policy policy) {
        if (policy == null) {
            return null;
        }

        PolicyDTO policyDto = new PolicyDTO();
        policyDto.setId(policy.getId());
        policyDto.setDateSigned(policy.getDateSigned() != null ? DateUtil.dateToString(policy.getDateSigned()) : null);
        policyDto.setExpiringDate(policy.getExpiringDate() != null ? DateUtil.dateToString(policy.getExpiringDate()) : null);
        policyDto.setMoneyReceivedDate(policy.getMoneyReceivedDate() != null ? DateUtil.dateToString(policy.getMoneyReceivedDate()) : null);
        policyDto.setAmount(policy.getAmount());
        policyDto.setIsDeleted(policy.getIsDeleted());
        // Assuming you have a method to convert Proposal to ProposalDto
        policyDto.setProposal(proposalMapper.toDTO(policy.getProposal()));

        return policyDto;
    }

    @Override
    public List<PolicyDTO> listToDTO(List<Policy> policies) {
        if (policies == null) {
            return null;
        }
        return policies.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
