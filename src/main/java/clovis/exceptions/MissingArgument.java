package clovis.exceptions;

public class MissingArgument extends Exception {
    public String getMessage() {
        return "Missing argument!";
    }
}
