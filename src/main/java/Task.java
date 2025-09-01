public class Task {
    private String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = true;
    }
    public void resetDone() {
        this.done = false;
    }
    public boolean isDone() {
        return done;
    }

}
