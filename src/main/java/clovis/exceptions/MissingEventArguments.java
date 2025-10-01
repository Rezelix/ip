package clovis.exceptions;

public class MissingEventArguments extends Exception {
    public String getMessage() {
        return "Missing event from or to dates!";
    }
}
