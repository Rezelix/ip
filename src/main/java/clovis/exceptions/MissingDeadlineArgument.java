package clovis.exceptions;

public class MissingDeadlineArgument extends Exception {
    public String getMessage() {
        return "Missing deadline!";
    }
}
