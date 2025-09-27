package clovis;

import java.util.ArrayList;
import clovis.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }
    public void delete(int index) {
        tasks.remove(get(index));
    }
    public Task get(int index) throws ClovisException.TargetIndexOutOfRange {
        return tasks.get(index);
    }
    public int size() {
        return tasks.size();
    }
    public Task getLatestTask() {
        return tasks.get(this.size() - 1);
    }
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    public void checkForAnyTasks() throws ClovisException.NoActiveTasks{
        if  (tasks.isEmpty()) {
            throw new ClovisException.NoActiveTasks();
        }
    }
    public void markTask (int index) throws ClovisException.TaskAlreadyMarkedCorrectly, ClovisException.TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkMarkingIndex(index);
        tasks.get(index).setDone();
    }
    public void unmarkTask (int index) throws ClovisException.TaskAlreadyMarkedCorrectly, ClovisException.TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkUnmarkingIndex(index);
        tasks.get(index).resetDone();
    }

    public void checkMarkingIndex (int taskIndex) throws ClovisException.TaskAlreadyMarkedCorrectly, ClovisException.TargetIndexOutOfRange{
        if (tasks.get(taskIndex).isDone()) {
            throw new ClovisException.TaskAlreadyMarkedCorrectly();
        }
    }

    public void checkUnmarkingIndex (int taskIndex) throws ClovisException.TaskAlreadyMarkedCorrectly, ClovisException.TargetIndexOutOfRange{
        if (!tasks.get(taskIndex).isDone()) {
            throw new ClovisException.TaskAlreadyMarkedCorrectly();
        }
    }

    public void checkIndexOutOfScope(int index) throws ClovisException.TargetIndexOutOfRange {
        if (index < 0 || index > size() - 1) {
            throw new ClovisException.TargetIndexOutOfRange();
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }
}
