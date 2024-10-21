package com.synechron.claimsmanagementservice.repository;

import com.synechron.claimsmanagementservice.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
}
