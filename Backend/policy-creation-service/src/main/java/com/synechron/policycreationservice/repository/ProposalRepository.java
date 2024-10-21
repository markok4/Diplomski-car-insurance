package com.synechron.policycreationservice.repository;

import com.synechron.policycreationservice.model.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    Page<Proposal> findAllByIsDeletedFalse(Pageable pageable);
}
