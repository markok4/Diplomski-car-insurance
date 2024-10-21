package com.synechron.policycreationservice.mapper;

import com.synechron.policycreationservice.dto.InsurancePlanDTO;
import com.synechron.policycreationservice.model.InsuranceItem;
import com.synechron.policycreationservice.model.InsurancePlan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsurancePlanMapper implements IMapper<InsurancePlan, InsurancePlanDTO> {
    @Override
    public InsurancePlan toEntity(InsurancePlanDTO insurancePlanDto) {
        if (insurancePlanDto == null) {
            return null;
        }

        InsurancePlan insurancePlan = new InsurancePlan();
        insurancePlan.setId(insurancePlanDto.getId());
        insurancePlan.setName(insurancePlanDto.getName());
        insurancePlan.setIsPremium(insurancePlanDto.getIsPremium());
        insurancePlan.setIsDeleted(insurancePlanDto.getIsDeleted());
        // The insuranceItems relationship would need to be handled if necessary
        // This might involve looking up and setting the actual InsuranceItem entities by their IDs

        return insurancePlan;
    }

    @Override
    public InsurancePlanDTO toDTO(InsurancePlan insurancePlan) {
        if (insurancePlan == null) {
            return null;
        }

        InsurancePlanDTO insurancePlanDto = new InsurancePlanDTO();
        insurancePlanDto.setId(insurancePlan.getId());
        insurancePlanDto.setName(insurancePlan.getName());
        insurancePlanDto.setIsPremium(insurancePlan.getIsPremium());
        insurancePlanDto.setIsDeleted(insurancePlan.getIsDeleted());
        // For the list of insurance item IDs, we extract just the IDs from the InsuranceItem entities
        List<Long> insuranceItemIds = insurancePlan.getInsuranceItems().stream()
                .map(InsuranceItem::getId)
                .collect(Collectors.toList());
        insurancePlanDto.setInsuranceItemIds(insuranceItemIds);

        return insurancePlanDto;
    }

    @Override
    public List<InsurancePlanDTO> listToDTO(List<InsurancePlan> insurancePlans) {
        return null;
    }
}
