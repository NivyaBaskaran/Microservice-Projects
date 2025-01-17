package com.interprep.quiz_service.controller;

import com.interprep.quiz_service.model.QuestionWrapper;
import com.interprep.quiz_service.model.QuizDto;
import com.interprep.quiz_service.model.Response;
import com.interprep.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// controller for quiz
@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
//    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){   //Data Transfer object
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumOfQuestions(), quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }


}