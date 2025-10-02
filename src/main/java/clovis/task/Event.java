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
        return this.getStatus() + this.getName() + " (from: " + this.getStartDateTime()
                + " to: " + this.getEndDateTime() + ")";
    }
    /**
     * Returns a string that contains all the Task Object's data that is used to save to a text document.
     * Loading from the document to individual parameters later will be easy if the separator
     * of each parameter can be defined. In this case, the separator is '|'
     * @return A String containing all details of the Task Object with a defined separator
     */
    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|" + this.getName()
                + "|" + this.getStartDateTime() + "|" + this.getEndDateTime();
    }
}
