package com.interprep.quiz_service;

import com.interprep.quiz_service.dao.QuizDao;
import com.interprep.quiz_service.feign.QuizInterface;
import com.interprep.quiz_service.model.QuestionWrapper;
import com.interprep.quiz_service.model.Quiz;
import com.interprep.quiz_service.model.Response;
import com.interprep.quiz_service.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuizInterface quizInterface;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private List<Integer> questionIds;
    private List<QuestionWrapper> questionWrappers;
    private List<Response> responses;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(1);
        quiz.setTitle("Sample Quiz");
        questionIds = Arrays.asList(1, 2);
        quiz.setQuestionsIds(questionIds);

        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setId(1);
        questionWrapper.setQuestionTitle("Sample Question");
        questionWrappers = Arrays.asList(questionWrapper);

        responses = Arrays.asList(new Response(1, "Answer"));
    }

    @Test
    void testCreateQuiz() {
        Mockito.when(quizInterface.getQuestionsForQuiz(eq("Sample Category"), eq(2)))
                .thenReturn(new ResponseEntity<>(questionIds, HttpStatus.OK));
        Mockito.when(quizDao.save(any(Quiz.class))).thenReturn(quiz);

        ResponseEntity<String> response = quizService.createQuiz("Sample Category", 2, "Sample Quiz");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    void testGetQuizQuestions() {
        Mockito.when(quizDao.findById(eq(1))).thenReturn(Optional.of(quiz));
        Mockito.when(quizInterface.getQuestionsFromId(eq(questionIds)))
                .thenReturn(new ResponseEntity<>(questionWrappers, HttpStatus.OK));

        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionWrappers, response.getBody());
    }

    @Test
    void testCalculateResult() {
        Mockito.when(quizInterface.getScore(any(List.class)))
                .thenReturn(new ResponseEntity<>(1, HttpStatus.OK));

        ResponseEntity<Integer> response = quizService.calculateResult(1, responses);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }
}
