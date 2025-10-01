package clovis.exceptions;

public class DataDirCouldNotBeMade extends Exception {
    public String getMessage() {
        return "Could not make data directory!";
    }
}
