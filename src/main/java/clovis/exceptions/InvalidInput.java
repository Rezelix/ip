package clovis.exceptions;

public class InvalidInput extends Exception {
    public String getMessage() {
        return "Don't give me nonsense! Re-enter!";
    }
}
