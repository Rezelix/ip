package clovis.Exceptions;

public class MissingEventArguments extends Exception {
    public String getMessage() {
        return "Missing event from or to dates!";
    }
}
