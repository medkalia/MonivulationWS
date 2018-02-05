package tn.legacy.monivulationws.Util;

import java.time.LocalDateTime;

public class ErrorMessage {

    public String message;
    public LocalDateTime date ;

    public ErrorMessage() {
        date = DateUtil.getCurrentTime();
    }
}
