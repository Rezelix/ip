package clovis.Exceptions;

public class MissingDeadlineArgument extends Exception {
    public String getMessage() {
        return "Missing deadline!";
    }
}
