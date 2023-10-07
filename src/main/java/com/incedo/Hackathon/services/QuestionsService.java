package com.incedo.Hackathon.services;

import com.incedo.Hackathon.models.Question;
import com.incedo.Hackathon.repository.QuestionsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public List<Question> getAllQuestions() {
        return questionsRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionsRepository.findById(id);
        return optionalQuestion.orElse(null);
    }

    public List<Question> saveAllQuestions(List<Question> questions) {
        return questionsRepository.saveAll(questions);
    }

    public Question saveQuestion(Question question) {
        return questionsRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        return questionsRepository.save(question);
    }

    public boolean deleteQuestionById(Long id) {
        if (questionsRepository.existsById(id)) {
            questionsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Question> getQuestionsForPanelist(boolean forPanelist) {
        return questionsRepository.findByForPanelist(forPanelist);
    }
}
