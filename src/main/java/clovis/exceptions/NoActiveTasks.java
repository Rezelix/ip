package clovis.exceptions;

public class NoActiveTasks extends Exception {
    public String getMessage() {
        return "There are currently no active tasks!";
    }
}
