package com.incedo.Hackathon.repository;

import com.incedo.Hackathon.models.Team;
import com.incedo.Hackathon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
