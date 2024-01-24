package handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception이 발생하면 이 클래스로 들어옴.
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value= TokenExpiredException.class)
    public String TokenCheck(TokenExpiredException e) { // 여기에도!
        return "Token_Fail";
    }
}
