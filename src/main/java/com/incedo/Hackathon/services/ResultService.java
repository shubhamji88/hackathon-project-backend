package com.incedo.Hackathon.services;

import com.incedo.Hackathon.models.Result;
import com.incedo.Hackathon.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResultService {


    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public boolean deleteResult(Long id) {
        try {
            resultRepository.deleteById(id);
            return true; // Deletion succeeded
        } catch (Exception e) {
            return false; // Result with the given ID was not found
        }
    }

    public List<Result> getResultsForIdeaPhase() {
        return resultRepository.findByIsIdeaPhase(true);
    }

    public List<Result> getResultsForImplementationPhase() {
        return resultRepository.findByIsIdeaPhase(false);
    }

    public Result updateResult(Long id, Result updatedResult) {
        try {
            Result existingResult = resultRepository.findById(id).orElse(null);

            if (existingResult != null) {
                // Update the fields of the existing result with the values from updatedResult
                existingResult.setFinalScore(updatedResult.getFinalScore());
                existingResult.setIdeaPhase(updatedResult.isIdeaPhase());
                // You can update other fields as needed

                return resultRepository.save(existingResult);
            }

            return null; // Result with the given ID was not found
        } catch (Exception e) {
            // Handle the unique constraint violation
            return null; // You can also return an error message or throw a custom exception
        }
    }
}

