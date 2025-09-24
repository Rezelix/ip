package clovis;

import clovis.task.Task;
import clovis.task.Deadline;
import clovis.task.Todo;
import clovis.task.Event;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";
    static final int MAX_NUM_OF_TASKS = 100;
    static final String TASK_FILEPATH = "data/clovis.txt";

    public static Task[] tasks = new Task[MAX_NUM_OF_TASKS];

    public static void main(String[] args) {
        printClovisIntro();
        int taskIndex = 0;
        Scanner input = new Scanner(System.in);
        while (true) {
            String line = input.nextLine();
            String[] words = line.split(" ");
            words = trimWords(words);
            String cmdType = words[0];
            System.out.print(DIVIDER);
            switch (cmdType) {
            case "list":
                printTasks(tasks);
                if (taskIndex == 0) {
                    System.out.println("No Tasks have been entered");
                }
                break;
            case "bye":
                System.out.println("Bye. Don't come again!");
                taskIndex = 0;
                System.exit(0);
                break;
            case "mark":
                try {
                    int taskNumMark = markEval(words, taskIndex);
                    tasks[taskNumMark - 1].setDone();
                } catch (ClovisException.InvalidInput e) {
                    System.out.println("Invalid input! It shouldn't be 0 or span outside of the active tasks!");
                    break;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Enter the task number after 'mark' (e.g. mark 1)");
                    break;
                } catch (ClovisException.HumanError e) {
                    System.out.println("Clovis.task.Task " + taskIndex + " was already marked done!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! It should be a number!");
                }
                break;
            case "unmark":
                try {
                    int taskNumUnmark = unmarkEval(words, taskIndex);
                    tasks[taskNumUnmark - 1].resetDone();
                } catch (ClovisException.InvalidInput e) {
                    System.out.println("Invalid input! It shouldn't be 0 or span outside of the active tasks!");
                    break;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Enter the task number after 'unmark' (e.g. unmark 1)");
                    break;
                } catch (ClovisException.HumanError e) {
                    System.out.println("Clovis.task.Task " + taskIndex + " wasn't done yet!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! It should be a number!");
                }
                break;
            case "deadline":
                try {
                    int dateIndex = deadlineEval(words,line,taskIndex);
                    String subStrTask = assemStrFromArrIndexes(words,1,dateIndex);
                    String subStrDeadline = assemStrFromArrIndexes(words,dateIndex+1);
                    tasks[taskIndex] = new Deadline(subStrTask, subStrDeadline);
                    printAck(tasks[taskIndex].toString());
                    printTotalInList(taskIndex + 1);
                    taskIndex += 1;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Missing Arguments, either no description or no deadline");
                    break;
                }
                break;
            case "todo":
                try {
                    checkForDescription(words);
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("You are missing your task description!");
                    break;
                }
                tasks[taskIndex] = new Todo(assemStrFromArrIndexes(words,1));
                printAck(tasks[taskIndex].toString());
                printTotalInList(taskIndex + 1);
                taskIndex += 1;
                break;
            case "event":
                try {
                    eventEval(words);
                    int fromIndex = findParamIndex(words, "/from");
                    int toIndex = findParamIndex(words, "/to");
                    String subStrEvent = assemStrFromArrIndexes(words, 1, fromIndex);
                    String subStrFrom = assemStrFromArrIndexes(words, fromIndex + 1, toIndex);
                    String subStrTo = assemStrFromArrIndexes(words, toIndex + 1);
                    tasks[taskIndex] = new Event(subStrEvent, subStrFrom, subStrTo);
                    printAck(tasks[taskIndex].toString());
                    printTotalInList(taskIndex + 1);
                    taskIndex += 1;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("You are missing your task description or other parameters!");
                }
                break;
            case "save":
                if (taskIndex == 0) {
                    System.out.println("No Tasks have been entered");
                    break;
                }
                System.out.println("Saving tasks to file...");
                try {
                    createDataDir();
                    FileWriter fw = new FileWriter(TASK_FILEPATH);
                    for (int i = 0; tasks[i] != null; i++) {
                        try {
                            fw.write(tasks[i].toExportString() + System.lineSeparator());
                        } catch (IOException e) {
                            System.out.println("Failed to save task " + tasks[i].toString());
                            break;
                        }
                        System.out.println("Successfully wrote task " + i + " : " + tasks[i].toString());
                    }
                    System.out.println("Tasks saved!");
                    fw.close();
                    break;
                } catch (IOException e) {
                    System.out.println("Error while writing tasks to file!");
                    break;
                }
            default:
                try {
                    throw new ClovisException.InvalidInput();
                } catch (ClovisException.InvalidInput e) {
                    System.out.println("Don't give me nonsense! Re-enter!");
                }
                break;
            }
            printDivider();
        }

    }

    private static void printClovisIntro() {
        String logo = "  _____ _            _\n" +
                " / ____| |          (_)\n" +
                "| |    | | _____   ___ ___\n" +
                "| |    | |/ _ \\ \\ / / / __|\n" +
                "| |____| | (_) \\ V /| \\__ \\\n" +
                " \\_____|_|\\___/ \\_/ |_|___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do you want from me this time?");
        System.out.print(DIVIDER);
    }

    private static void createDataDir() {
        File dir = new File("data");
        if (!dir.exists()) {
            System.out.println("Data directory does not exist, I'm making it now");
            if (dir.mkdir()) {
                System.out.println("Created tasks directory @ " + dir.getAbsolutePath());
            } else {
                System.out.println("Failed to create tasks directory, try again");
            }
        }
    }

    public static void printDivider() {
        System.out.print(DIVIDER);
    }

    public static void printTasks(Task[] tasks) {
        for (int i = 0; tasks[i] != null; i++) {
            System.out.println(i + 1 + "." + tasks[i].toString());
        }
    }

    public static void printAck(String line) {
        System.out.println("added: " + line);
    }

    public static void printTotalInList(int numOfTasks) {
        System.out.println("You currently have " + numOfTasks + " tasks in your list");
    }

    public static String[] trimWords(String[] words) {
        String[] output = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            output[i] = words[i].trim();
        }
        return output;
    }

    public static int findParamIndex(String[] array, String keyword) throws ClovisException.ArgumentValueMissing {
        for (int i = 1; i < array.length; i++) {
            if (array[i].equals(keyword)) {
                return i;
            }
        }
        throw new ClovisException.ArgumentValueMissing();
    }

    public static void checkForDescription (String[] words) throws ClovisException.ArgumentValueMissing {
        if (words.length == 1) {
            throw new ClovisException.ArgumentValueMissing();
        }
    }

    public static String assemStrFromArrIndexes(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static String assemStrFromArrIndexes(String[] array, int startIndex) {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static int markEval (String[] words, int taskIndex) {
        checkForDescription(words);
        int taskNumMark = Integer.parseInt(words[1]);
        if (taskNumMark == 0 || taskNumMark >= taskIndex + 1) {
            throw new ClovisException.InvalidInput();
        }
        if (tasks[taskNumMark - 1].isDone()) {
            throw new ClovisException.HumanError();
        }
        return taskNumMark;
    }

    public static int unmarkEval (String[] words, int taskIndex) {
        checkForDescription(words);
        int taskNumUnMark = Integer.parseInt(words[1]);
        if (taskNumUnMark == 0 || taskNumUnMark >= taskIndex + 1) {
            throw new ClovisException.InvalidInput();
        }
        if (!tasks[taskNumUnMark - 1].isDone()) {
            throw new ClovisException.HumanError();
        }
        return taskNumUnMark;
    }

    public static int deadlineEval (String[] words, String line,  int taskIndex) {
        checkForDescription(words);
        int dateIndex = findParamIndex(words, "/by");
        if (dateIndex == words.length) {
            throw new ClovisException.ArgumentValueMissing();
        }
        return dateIndex;
    }

    public static void eventEval (String[] words) {
        checkForDescription(words);
    }
}
