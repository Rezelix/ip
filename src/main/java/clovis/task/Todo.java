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

    @Override
    public String toExportString() {
        return this.getTypeAbbrev() + "|" + (this.isDone() ? 1 : 0) + "|" + this.getName();
    }
}
