package clovis;

import clovis.task.Task;

import java.util.ArrayList;

public class Ui {
    private static final String DIVIDER = "__________________________________________________________\n";
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

    public static void printDivider() {
        System.out.print(DIVIDER);
    }

    public static void printAck(String line) {
        System.out.println("added: " + line);
    }

    public static void printDelAck(int delIndex, String delStr) {
        System.out.println("Deleted the task: " + (delIndex + 1) + "." + delStr);
    }

    public static void printSavedTasks() {
        System.out.println("Successfully saved all Tasks");
    }

    public static void printTotalInList(int numOfTasks) throws ClovisException.NoActiveTasks {
        System.out.println("You currently have " + numOfTasks + " tasks in your list");
    }

    public static void printTaskCreation(Task task,int taskIndex) {
        printAck(task.toString());
        printTotalInList(taskIndex + 1);
    }

    public static void printTaskDeletion(Task task,int taskIndex) {
        printDelAck(taskIndex,task.toString());
        printTotalInList(taskIndex + 1);
    }

    public static void printTasks(ArrayList<Task> tasks) throws ClovisException.NoActiveTasks{
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + "." + tasks.get(i).toString());
        }
    }
}
