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

    public String getStartDateTime() {
        return this.startDateTime;
    }

    public String getEndDateTime() {
        return this.endDateTime;
    }

    public String toString() {
        return this.getStatus() + this.getName() + " (from: " + this.getStartDateTime() + " to: " + this.getEndDateTime() + ")";
    }
}
