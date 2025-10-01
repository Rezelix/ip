package clovis.exceptions;

public class TaskAlreadyMarkedCorrectly extends Exception {
    public String getMessage() {
        return "The task was already marked correctly!";
    }
}
