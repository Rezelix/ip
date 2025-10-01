package clovis;

import clovis.task.Task;
import clovis.Exceptions.NoActiveTasks;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "__________________________________________________________\n";
    private final Scanner scanner;

    public static void printClovisIntro() {
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

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public static void printDivider() {
        System.out.print(DIVIDER);
    }

    public static void printAck(String line) {
        System.out.println("added: " + line);
    }

    public static void printDelAck(int delIndex, String delStr) {
        System.out.println("Deleted the task: " + (delIndex + 1) + "." + delStr);
    }

    public static void printSaving() {
        System.out.println("Saving tasks to file...");
    }

    public static void printSavedTasks() {
        System.out.println("Successfully saved all Tasks");
    }

    public static void printTotalInList(int numOfTasks) throws NoActiveTasks {
        System.out.println("You currently have " + numOfTasks + " tasks in your list");
    }

    public static void printTaskCreation(Task task,int taskIndex) throws NoActiveTasks {
        printAck(task.toString());
        printTotalInList(taskIndex);
    }

    public static void printTaskDeletion(String deletedTask,int delIndex, int tasksIndex) throws NoActiveTasks {
        printDelAck(delIndex,deletedTask);
        printTotalInList(tasksIndex);
    }

    public static void printTasks(ArrayList<Task> tasks){
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + "." + tasks.get(i).toString());
        }
    }

    public void printTask(Task task) {
        System.out.println(task.toString());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printMarkAck (int markTaskIndex, Task markTask) {
        System.out.println("Marked Task " + (markTaskIndex+1) + " successfully!");
        printTask(markTask);
    }

    public void printUnmarkAck (int unmarkTaskIndex, Task unmarkTask) {
        System.out.println("Unmarked Task " + (unmarkTaskIndex+1) + " successfully!");
        printTask(unmarkTask);
    }

    public void printDeleteAll() {
        System.out.println("Deleted all Tasks");
    }

    public void printBye() {
        System.out.println("Bye. Don't come again!");
    }
}
