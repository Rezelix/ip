package clovis.exceptions;

public class WrongArgumentFormat extends Exception {
    public String getMessage() {
        return "You have given me a invalid value for this command!";
    }
}
