package me.suhyuk.spring.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("/notfound")
    public String notFound() {
        throw new NotFoundException();
    }

    @GetMapping("/server")
    public String serverError() {
        throw new ServerException();
    }

    @GetMapping("/missed")
    public String missed() {
        MissedException missed = new MissedException();
        missed.setMessage("찾을 수 없는 URL 입니다");
        throw missed;
    }

    @GetMapping("illegal")
    public String illegal() {
        throw new IllegalArgumentException("잘못된 URL 및 매개변수입니다");
    }

    // 아래와 같이 컨트롤러에서만 사용하는 예외는 @ExceptionHandler 을 N개 이상 나열하여 구현 합니다
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    RuntimeError notFoundError(NotFoundException exception) {
        RuntimeError error = new RuntimeError();
        error.setMessage("에러가 발생 ");
        error.setReason("실수");
        return error;
    }
}
