package clovis.task;

public class Deadline extends Task{
    protected String dateTimeDeadline;

    public Deadline(String description, String dateTimeDeadline){
        super(description);
        this.setType("D");
        this.dateTimeDeadline = dateTimeDeadline;
    }

    public Deadline(String description,boolean isDone, String dateTimeDeadline){
        super(description);
        this.setType("D");
        this.dateTimeDeadline = dateTimeDeadline;
        if (isDone){
            this.setDone();
        }
    }

    public String getDateTimeDeadline() {
        return this.dateTimeDeadline;
    }

    @Override
    public String toString() {
        return super.getStatus() + super.getName() + " (by: " + this.getDateTimeDeadline() + ")";
    }

    /**
     * Returns a string that contains all the Task Object's data that is used to save to a text document.
     * Loading from the document to individual parameters later will be easy if the separator
     * of each parameter can be defined. In this case, the separator is '|'
     * @return A String containing all details of the Task Object with a defined separator
     */
    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|"
                + this.getName() + "|" + this.getDateTimeDeadline();
    }
}
