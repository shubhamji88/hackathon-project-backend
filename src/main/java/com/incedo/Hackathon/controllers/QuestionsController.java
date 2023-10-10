package com.incedo.Hackathon.controllers;

import com.incedo.Hackathon.models.Question;
import com.incedo.Hackathon.payload.response.MessageResponse;
import com.incedo.Hackathon.services.QuestionsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    private final QuestionsService questionsService;

    @Autowired
    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionsService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        Question question = questionsService.getQuestionById(id);
        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveQuestion(@RequestBody Question question) {
        Question savedQuestion = questionsService.saveQuestion(question);
        if (savedQuestion != null) {
            return ResponseEntity.ok("Question saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to save question"));
        }
    }

    @PostMapping("/saveAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveAllQuestions(@RequestBody List<Question> questions) {
        List<Question> savedQuestions = questionsService.saveAllQuestions(questions);
        if (!savedQuestions.isEmpty()) {
            return ResponseEntity.ok("Questions saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to save questions"));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question) {
        if (question.getId() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Question ID is required."));
        }

        Question updatedQuestion = questionsService.updateQuestion(question);
        if (updatedQuestion != null) {
            return ResponseEntity.ok("Question updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuestionById(@PathVariable Long id) {
        boolean deleted = questionsService.deleteQuestionById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/forPanelist")
    @PreAuthorize("hasRole('PANELIST')")
    public ResponseEntity<List<Question>> getQuestionsForPanelist() {
        List<Question> questions = questionsService.getQuestionsForPanelist(true);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/forJudge")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<List<Question>> getQuestionsForJudge() {
        List<Question> questions = questionsService.getQuestionsForPanelist(false);
        return ResponseEntity.ok(questions);
    }
}