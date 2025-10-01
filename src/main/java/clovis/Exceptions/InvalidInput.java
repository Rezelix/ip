package clovis.Exceptions;

public class InvalidInput extends Exception {
    public String getMessage() {
        return "Don't give me nonsense! Re-enter!";
    }
}
