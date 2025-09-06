public class Todo extends Task{
    public Todo(String name) {
        super(name);
        this.setType("T");
    }

    @Override
    public String toString() {
        return super.getStatus() + super.getName();
    }
}
