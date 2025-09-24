package clovis.task;

public class Todo extends Task{
    public Todo(String name) {
        super(name);
        this.setType("T");
    }

    @Override
    public String toString() {
        return super.getStatus() + super.getName();
    }

    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|" + this.getName();
    }
}
