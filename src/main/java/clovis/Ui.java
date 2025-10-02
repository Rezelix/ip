package clovis;

import clovis.task.Task;
import clovis.exceptions.NoActiveTasks;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "__________________________________________________________\n";
    private final Scanner scanner;

    /**
     * Prints the intro screen for Clovis
     */
    public void printClovisIntro() {
        String logo = "  _____ _            _\n" +
                " / ____| |          (_)\n" +
                "| |    | | _____   ___ ___\n" +
                "| |    | |/ _ \\ \\ / / / __|\n" +
                "| |____| | (_) \\ V /| \\__ \\\n" +
                " \\_____|_|\\___/ \\_/ |_|___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do you want from me this time?");
        printDivider();
    }

    /**
     * Constructs a class consisting of a Scanner object to receive user input in their terminal
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the string in the next line the user has entered.
     * Also known as the next input before the user has pressed enter.
     * @return A String Object
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a sequence of '_' to the terminal to help separate user input and program output.
     */
    public void printDivider() {
        System.out.print(DIVIDER);
    }

    /**
     * Prints the string in a String object to the terminal to tell the user that
     * the task has been successfully added to the ArrayList
     * @param line
     */
    public void printAck(String line) {
        System.out.println("added: " + line);
    }

    /**
     * Prints the string in a String object to the terminal to tell the user that
     * the task been successfully deleted from the ArrayList, along with its known index in the list.
     *
     * @param delIndex
     * @param delStr
     */
    public void printDelAck(int delIndex, String delStr) {
        System.out.println("Deleted the task: " + (delIndex + 1) + "." + delStr);
    }

    /**
     * Prints a string to tell the user that Clovis is about the start saving the defined ArrayList to a text file.
     */
    public void printSaving() {
        System.out.println("Saving tasks to file...");
    }

    /**
     * Prints a string to tell the user that Clovis has saved the ArrayList to a text file.
     */
    public void printSavedTasks() {
        System.out.println("Successfully saved all Tasks");
    }

    /**
     * Prints a string to the terminal to tell the user how many tasks are currently in the ArrayList
     * Printed after adding or deleting a Task Object in the ArrayList
     * @param numOfTasks
     * @throws NoActiveTasks
     */
    public void printTotalInList(int numOfTasks) throws NoActiveTasks {
        System.out.println("You currently have " + numOfTasks + " tasks in your list");
    }

    /**
     * Prints two strings in the terminal to tell the user that the task was added to the ArrayList and the total
     * number of Task Objects in the ArrayList
     * @param task
     * @param taskIndex
     * @throws NoActiveTasks
     */
    public void printTaskCreation(Task task,int taskIndex) throws NoActiveTasks {
        printAck(task.toString());
        printTotalInList(taskIndex);
    }

    /**
     * Prints two strings in the terminal to tell the user that the task was deleted from the ArrayList
     * and the total number of Task Objects in the ArrayList
     * @param deletedTask
     * @param delIndex
     * @param tasksIndex
     * @throws NoActiveTasks
     */
    public void printTaskDeletion(String deletedTask,int delIndex, int tasksIndex) throws NoActiveTasks {
        printDelAck(delIndex,deletedTask);
        printTotalInList(tasksIndex);
    }

    /**
     * Prints the String object returned from the toString() methods each of the Task Objects in the ArrayList.
     * toString() returns the description of the Task Object,
     * along with any date or time parameters, depending on the task type
     * @param tasks
     */
    public void printTasks(ArrayList<Task> tasks){
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + "." + tasks.get(i).toString());
        }
    }

    /**
     * Prints the String object returned from the toString() method of one Task Object.
     * toString() returns the description of the Task Object,
     * along with any date or time parameters, depending on the task type
     * @param task
     */
    public void printTask(Task task) {
        System.out.println(task.toString());
    }

    /**
     * Prints the string of a String Object to the terminal.
     * Used for the bye message
     * @param message
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints the string of a String object to the terminal
     * Used for exceptions and errors
     * @param message
     */
    public void printError(String message) {
        System.out.println(message);
    }

    /**
     * Prints a string to the terminal to tell the user that the task has been marked
     * @param markTaskIndex
     * @param markTask
     */
    public void printMarkAck (int markTaskIndex, Task markTask) {
        System.out.println("Marked Task " + (markTaskIndex+1) + " successfully!");
        printTask(markTask);
    }

    /**
     * Prints a string to the terminal to tell the user that the task has been unmarked
     * @param unmarkTaskIndex
     * @param unmarkTask
     */
    public void printUnmarkAck (int unmarkTaskIndex, Task unmarkTask) {
        System.out.println("Unmarked Task " + (unmarkTaskIndex+1) + " successfully!");
        printTask(unmarkTask);
    }

    /**
     * Prints a string to the terminal to tell the user that all the tasks have been deleted.
     */
    public void printDeleteAll() {
        System.out.println("Deleted all Tasks");
    }

    /**
     * Prints a string to the terminal to tell the user that the system is closing.
     */
    public void printBye() {
        System.out.println("Bye. Don't come again!");
    }
}
