package com.synechron.policycreationservice.repository;

import com.synechron.policycreationservice.dto.PolicyDTO;
import com.synechron.policycreationservice.model.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface PolicyRepository extends JpaRepository<Policy, Long> {

   // @Query("SELECT new com.synechron.policycreationservice.dto.PolicyDTO(p.id, p.amount, p.dateSigned, p.moneyReceivedDate, p.isDeleted, p.proposal.proposalStatus, p.proposal.carPlates) FROM Policy p WHERE p.subscriberId = :subscriberId")

    List<Policy>  findAllBySubscriberId(Long subscriberId);


    @Query("SELECT p FROM Policy p WHERE p.subscriberId IN :subscriberIds")
    List<Policy> findAllBySubscriberIdIn(@Param("subscriberIds") List<Long> subscriberIds);

    List<Policy> findAllByProposalCarIdIn(List<Long> carIds);

    @Query("SELECT p FROM Policy p WHERE CAST(p.dateSigned AS date) = :date")
    List<Policy> findAllByDateSigned(@Param("date") LocalDate date);

    List<Policy> findAllByProposalCarIdInAndSubscriberIdIn(List<Long> carIds, List<Long> subscriberIds);

    @Query("SELECT p FROM Policy p WHERE p.proposal.carId IN :carIds AND CAST(p.dateSigned AS date) = :date")
    List<Policy> findAllByProposalCarIdInAndDateSigned(@Param("carIds") List<Long> carIds, @Param("date") LocalDate date);

    @Query("SELECT p FROM Policy p WHERE p.subscriberId IN :subscriberIds AND CAST(p.dateSigned AS date) = :date")
    List<Policy> findAllBySubscriberIdInAndDateSigned(@Param("subscriberIds") List<Long> subscriberIds, @Param("date") LocalDate date);

    @Query("SELECT p FROM Policy p WHERE p.proposal.carId IN :carIds AND p.subscriberId IN :subscriberIds AND CAST(p.dateSigned AS date) = :date")
    List<Policy> findAllByProposalCarIdInAndSubscriberIdInAndDateSigned(@Param("carIds") List<Long> carIds, @Param("subscriberIds") List<Long> subscriberIds, @Param("date") LocalDate date);

    List<PolicyDTO> getPoliciesBySubscriberId(Long userId);

    @Query("SELECT p FROM Policy p WHERE p.proposal.salesAgentEmail = :salesAgentEmail")
    Page<Policy> findPoliciesBySalesAgentEmail(@Param("salesAgentEmail") String salesAgentEmail, Pageable pageable);
}
