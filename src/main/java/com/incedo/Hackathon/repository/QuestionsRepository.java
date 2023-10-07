package com.incedo.Hackathon.repository;

import com.incedo.Hackathon.models.ERole;
import com.incedo.Hackathon.models.Question;
import com.incedo.Hackathon.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.forPanelist = :forPanelist")
    List<Question> findByForPanelist(@Param("forPanelist") boolean forPanelist);

}
