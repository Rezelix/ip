public class Deadline extends Task{
    private String dateTimeDeadline;

    public Deadline(String description, String dateTimeDeadline){
        super(description);
        super.setType("D");
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
