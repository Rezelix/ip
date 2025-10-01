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

    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|"
                + this.getName() + "|" + this.getDateTimeDeadline();
    }
}
