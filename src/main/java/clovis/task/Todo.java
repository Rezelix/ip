package clovis.task;

public class Todo extends Task{
    public Todo(String name) {
        super(name);
        this.setType("T");
    }

    public Todo(String name, boolean isDone) {
        super(name);
        this.setType("T");
        if (isDone){
            this.setDone();
        }
    }

    @Override
    public String toString() {
        return super.getStatus() + super.getName();
    }

    /**
     * Returns a string that contains all the Task Object's data that is used to save to a text document.
     * Loading from the document to individual parameters later will be easy if the separator
     * of each parameter can be defined. In this case, the separator is '|'
     * @return A String containing all details of the Task Object with a defined separator
     */
    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|" + this.getName();
    }
}
