package engine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Validated
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping (path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping (path = "/api/quizzes")
    public String getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping (path = "/api/quizzes/{id}/solve")
    public Reply answerQuiz(@PathVariable Long id, @RequestBody UserAnswer answer) {
        return quizService.checkAnswer(id, answer);
    }

    @PostMapping (path = "/api/quizzes")
    public Quiz createQuiz(@Valid @RequestBody Quiz newQuiz) {
        return quizService.saveQuiz(newQuiz);
    }
}
