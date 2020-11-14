package polytech.covidalert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceAlreadyExistsException extends ResponseStatusException {

    public ResourceAlreadyExistsException(HttpStatus status, String msg) {
        super(status,msg);
    }


}