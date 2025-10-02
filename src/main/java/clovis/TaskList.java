package clovis;

import clovis.exceptions.*;
import java.util.ArrayList;

import clovis.task.Task;

/**
 *
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructor which creates a class whose member is a mutable Array of Task objects
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor which creates a class whose member is a mutable Array of Task objects
     * Used when pre-existing tasks exist in an external storage.
     * @param tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a Task object to the mutable Array
     * @param task
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes an index specified Task object in the mutable array
     * Throws IndexOutOfBoundsException when the target index does not span the mutable Array
     * @param index
     * @throws IndexOutOfBoundsException
     */
    public void delete(int index) throws IndexOutOfBoundsException {
        tasks.remove(get(index));
    }

    /**
     * Returns a task object from a specific index in the mutable array
     * Throws IndexOutOfBoundsException when the target index does not span the mutable Array
     * @param index
     * @return A Task Object
     * @throws IndexOutOfBoundsException
     */
    public Task get(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    /**
     * Returns the total number of Task Objects in the mutable array
     * @return number of elements
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the latest/the last Task Object in the ArrayList
     * @return A Task Object
     */
    public Task getLatestTask() {
        return tasks.get(this.size() - 1);
    }

    /**
     * Returns the entire ArrayList of Task Objects
     * @return a mutable Array consisting of Task Objects
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Checks if there are any Task objects in the instantiated ArrayList.
     * Throws the Exception NoActiveTasks when there are none.
     * @throws NoActiveTasks
     */
    public void checkForAnyTasks() throws NoActiveTasks{
        if  (tasks.isEmpty()) {
            throw new NoActiveTasks();
        }
    }

    /**
     * Sets a flag in an index specified Task Object in the Arraylist to declare that it is marked done
     * Throws the Exception TaskAlreadyMarkedCorrectly if the specified task was already marked
     * Throws the Exception TargetIndexOutOfRange when the specified index does not span the elements in the ArrayList
     * @param index
     * @throws TaskAlreadyMarkedCorrectly
     * @throws TargetIndexOutOfRange
     */
    public void markTask (int index) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkMarkingIndex(index);
        tasks.get(index).setDone();
    }

    /**
     * Resets a flag in an index specified Task Object in the Arraylist to declare that it is labelled not done
     * Throws the Exception TaskAlreadyMarkedCorrectly if the specified task was already unmarked
     * Throws the Exception TargetIndexOutOfRange when the specified index does not span the elements in the ArrayList
     * @param index
     * @throws TaskAlreadyMarkedCorrectly
     * @throws TargetIndexOutOfRange
     */
    public void unmarkTask (int index) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        checkIndexOutOfScope(index);
        checkUnmarkingIndex(index);
        tasks.get(index).resetDone();
    }

    /**
     * Checks if the specified Task Object has the flag value that indicates the task was already marked.
     * Throws the Exception TaskAlreadyMarkedCorrectly if it is marked
     * @param taskIndex
     * @throws TaskAlreadyMarkedCorrectly
     * @throws TargetIndexOutOfRange
     */
    public void checkMarkingIndex (int taskIndex) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        if (tasks.get(taskIndex).isDone()) {
            throw new TaskAlreadyMarkedCorrectly();
        }
    }

    /**
     * Checks if the specified Task Object has the flag value that indicates the task was not done
     * @param taskIndex
     * @throws TaskAlreadyMarkedCorrectly
     * @throws TargetIndexOutOfRange
     */
    public void checkUnmarkingIndex (int taskIndex) throws TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange{
        if (!tasks.get(taskIndex).isDone()) {
            throw new TaskAlreadyMarkedCorrectly();
        }
    }

    /**
     * Checks if the specified index for a Task Object search in the ArrayList spans the elements in the list.
     * Throws the exception TargetIndexOutOfRange if it does not span the range of elements
     * @param index
     * @throws TargetIndexOutOfRange
     */
    public void checkIndexOutOfScope(int index) throws TargetIndexOutOfRange {
        if (index < 0 || index > size() - 1) {
            throw new TargetIndexOutOfRange();
        }
    }

    /**
     * Deletes all task objects in the ArrayList
     */
    public void deleteAllTasks() {
        tasks.clear();
    }

    /**
     * Returns an identical or a subset ArrayList of Task Objects which contain the specified keyword in its description
     * @param keyword
     * @return a mutable ArrayList consisting of Task Objects containing the keyword in its description
     * @throws KeywordNotFound
     */
    public ArrayList<Task> find(String keyword) throws KeywordNotFound {
        ArrayList<Task> foundKeyword = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                foundKeyword.add(task);
            }
        }
        //Todo implement when no keyword found
        return foundKeyword;
    }
}
