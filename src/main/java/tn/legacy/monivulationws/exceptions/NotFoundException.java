package tn.legacy.monivulationws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tn.legacy.monivulationws.Util.ErrorMessage;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException(String msg) {
        super(msg);
    }
}
