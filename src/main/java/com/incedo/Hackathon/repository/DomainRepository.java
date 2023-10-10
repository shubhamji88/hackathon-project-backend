package com.incedo.Hackathon.repository;

import com.incedo.Hackathon.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    // You can add custom query methods here if needed
}
