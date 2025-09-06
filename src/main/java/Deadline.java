public class Deadline extends Task{
    protected String dateTimeDeadline;

    public Deadline(String description, String dateTimeDeadline){
        super(description);
        this.setType("D");
        this.dateTimeDeadline = dateTimeDeadline;
    }

    public String getDateTimeDeadline() {
        return this.dateTimeDeadline;
    }

    @Override
    public String toString() {
        return super.getStatus() + super.getName() + " (by: " + this.getDateTimeDeadline() + ")";
    }
}
