package clovis;

import clovis.Exceptions.*;
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
    public void delete(int index) throws TargetIndexOutOfRange {
        tasks.remove(get(index));
    }
    public Task get(int index) throws TargetIndexOutOfRange {
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
    public void checkForAnyTasks() throws NoActiveTasks{
        if  (tasks.isEmpty()) {
            throw new NoActiveTasks();
        }
    }
    public void markTask (int index) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkMarkingIndex(index);
        tasks.get(index).setDone();
    }
    public void unmarkTask (int index) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkUnmarkingIndex(index);
        tasks.get(index).resetDone();
    }

    public void checkMarkingIndex (int taskIndex) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        if (tasks.get(taskIndex).isDone()) {
            throw new TaskAlreadyMarkedCorrectly();
        }
    }

    public void checkUnmarkingIndex (int taskIndex) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        if (!tasks.get(taskIndex).isDone()) {
            throw new TaskAlreadyMarkedCorrectly();
        }
    }

    public void checkIndexOutOfScope(int index) throws TargetIndexOutOfRange {
        if (index < 0 || index > size() - 1) {
            throw new TargetIndexOutOfRange();
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public ArrayList<Task> find(String keyword) throws KeywordNotFound {
        ArrayList<Task> foundKeyword = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                foundKeyword.add(task);
            }
        }
        return foundKeyword;
    }
}
