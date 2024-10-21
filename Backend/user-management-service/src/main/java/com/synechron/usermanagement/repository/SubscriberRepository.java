package com.synechron.usermanagement.repository;

import com.synechron.usermanagement.model.Subscriber;
import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    @Query("SELECT s FROM Subscriber s " +
            "JOIN User u ON s.id = u.id " +
            "JOIN Person p ON s.id = p.id " +
            "WHERE u.email LIKE %:searchTerm% " +
            "OR p.firstName LIKE %:searchTerm% " +
            "OR p.lastName LIKE %:searchTerm% " +
            "OR CONCAT(p.firstName, ' ', p.lastName) LIKE %:searchTerm%")
    List<Subscriber> findBySearchTerm(@Param("searchTerm") String searchTerm);

    boolean existsById(Long id);
    Boolean existsByEmail(String email);

    public Subscriber findByEmail(String email);
    
    @Query("SELECT s FROM Subscriber s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(%:credentials%) OR " +
            "LOWER(s.lastName) LIKE LOWER(%:credentials%) OR " +
            "LOWER(s.email) LIKE LOWER(%:credentials%)")
    Page<Subscriber> searchByCredentials(@Param("credentials") String credentials, Pageable pageable);
}
