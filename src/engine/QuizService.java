package engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


import static java.lang.String.valueOf;

@Service
public class QuizService {
    private QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    List<Quiz> quizList = new ArrayList<>();

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        try {
            return quizRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public Reply checkAnswer(Long id, UserAnswer answer) {
        Reply reply = new Reply();

        if (quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            if (valueOf(quizRepository.findById(id).get().getAnswer()).equals(valueOf(answer.getAnswer()))) {
                return new Reply(true, reply.CORRECT);
            } else {
                return new Reply(false, reply.WRONG);
            }
        }
    }

    public String getAllQuizzes() {

        List<Quiz> allQuizes = new ArrayList<>();
        quizRepository.findAll().forEach(allQuizes::add);
        String text = "[\n ";
        for (Quiz quiz : allQuizes) {
            try {
                text += objectMapper.writeValueAsString(quiz);
            } catch (Exception e) {
                text += "";
            }
        }
        text = text.replace("}{", "},\n {");
        text += "\n]";

        return text;
    }
}
