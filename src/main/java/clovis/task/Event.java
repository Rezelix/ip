package clovis.task;

public class Event extends Task{
    protected String startDateTime;
    protected String endDateTime;

    public Event(String name, String startDateTime, String endDateTime) {
        super(name);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.setType("E");
    }

    public Event(String name,boolean isDone, String startDateTime, String endDateTime) {
        super(name);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.setType("E");
        if (isDone){
            this.setDone();
        }
    }

    public String getStartDateTime() {
        return this.startDateTime;
    }

    public String getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public String toString() {
        return this.getStatus() + this.getName() + " (from: " + this.getStartDateTime() + " to: " + this.getEndDateTime() + ")";
    }
    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|" + this.getName() + "|" + this.getStartDateTime() + "|" + this.getEndDateTime();
    }
}
