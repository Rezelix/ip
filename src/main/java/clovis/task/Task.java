package clovis.task;

public class Task {
    private String name;
    private boolean isDone;
    private String type;
    public Task(String name) {
        this.name = name;
        this.isDone = false;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTypeAbbrev() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
    public void setDone() {
        this.isDone = true;
    }
    public void resetDone() {
        this.isDone = false;
    }
    public boolean isDone() {
        return this.isDone;
    }
    public String isDoneMark() {
        return this.isDone() ? "X" : " ";
    }
    public String getStatus() {
        return "[" + this.getTypeAbbrev() + "][" + this.isDoneMark() + "] ";
    }
    public String toString() {
        return "This task's class does not have an overridden toString()";
    }
}
