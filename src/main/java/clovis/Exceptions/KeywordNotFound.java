package clovis.Exceptions;

public class KeywordNotFound extends Exception {
    public String getMessage() {
        return "Could not find keyword!";
    }
}
