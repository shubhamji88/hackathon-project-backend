package com.incedo.Hackathon.controllers;

import com.incedo.Hackathon.models.Result;
import com.incedo.Hackathon.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getResultById(@PathVariable Long id) {
        Result result = resultService.getResultById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body("Result with ID " + id + " not found");
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PANELIST') or hasRole('JUDGE')")
    public ResponseEntity<?> updateResult(@PathVariable Long id, @RequestBody Result updatedResult) {
        Result updated = resultService.updateResult(id, updatedResult);

        if (updated != null) {
            return ResponseEntity.ok("Result updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Result with ID " + id + " not found or check for @UniqueConstraint(columnNames = {\"teamId\", \"isIdeaPhase\"})" );
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('PANELIST') or hasRole('JUDGE')")
    public ResponseEntity<?> saveResult(@RequestBody Result result) {
        Result savedResult = resultService.saveResult(result);
        if (savedResult != null) {
            return ResponseEntity.ok("Result saved successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to save result");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PANELIST') or hasRole('JUDGE')")
    public ResponseEntity<?> deleteResult(@PathVariable Long id) {
        boolean deleted = resultService.deleteResult(id);
        if (deleted) {
            return ResponseEntity.ok("Result deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Result with ID " + id + " not found");
        }
    }

    @GetMapping("/idea-phase")
    @PreAuthorize("hasRole('PANELIST')")
    public ResponseEntity<List<Result>> getResultsForIdeaPhase() {
        List<Result> results = resultService.getResultsForIdeaPhase();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/implementation-phase")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<List<Result>> getResultsForImplementationPhase() {
        List<Result> results = resultService.getResultsForImplementationPhase();
        return ResponseEntity.ok(results);
    }
}