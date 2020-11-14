package polytech.covidalert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FormNotCompletedException extends ResponseStatusException {

    public FormNotCompletedException(HttpStatus status, String msg) {
        super(status,msg);
    }


}