package clovis.task;

public class Task {
    private String name;
    private boolean isDone;
    private String type;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns the string of the name member of the Task Object
     * @return String of Task Object's name member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Task Object
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the string of the type member of the Task Object
     * @return String of Task Object's type member
     */
    public String getTypeAbbrev() {
        return this.type;
    }

    /**
     * Sets the type of the Task Object
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the isDone member of the Task Object to 1 to indicate that the task is done
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets the isDone member of the Task Object to 0 to indicate that the task is not done
     */
    public void resetDone() {
        this.isDone = false;
    }

    /**
     * Checks if the Task Object is marked done
     * Returns 1 if task is done
     * Returns 0 if task is not done
     * @return boolean indicating task is done
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a single character as a String Object, depending if the Task is marked done or not.
     * Returns 'X' if task is done
     * Returns ' ' if task is not done
     * @return String indicating task is done
     */
    public String isDoneMark() {
        return this.isDone() ? "X" : " ";
    }

    /**
     * Returns a String containing details of the Task Object (type and if done)
     * Used for toString()
     * @return String containing type and marked details of the Task Object
     */
    public String getStatus() {
        return "[" + this.getTypeAbbrev() + "][" + this.isDoneMark() + "] ";
    }

    /**
     * Returns a String to show that the class does not have an overridden toString()
     * @return String that tells the developer that an overridden toString() is not implemented for the class
     */
    public String toString() {
        return "This task's class does not have an overridden toString()";
    }

    /**
     * Returns a string that contains all the Task Object's data that is used to save to a text document.
     * Loading from the document to individual parameters later will be easy if the separator
     * of each parameter can be defined. In this case, the separator is '|'
     *
     * @return A String containing all details of the Task Object with a defined separator
     */
    public String toExportString() {
        return "This task's class does not have an overridden toExportString()";
    }
}
