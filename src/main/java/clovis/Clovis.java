package clovis;

import clovis.task.Task;
import clovis.task.Deadline;
import clovis.task.Todo;
import clovis.task.Event;
import static clovis.Ui.*;
import static clovis.Storage.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Clovis {

    public static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        printClovisIntro();
        Storage s = new Storage("data/clovis.txt");
        int taskIndex = 0;
        Scanner input = new Scanner(System.in);
        while (true) {
            String line = input.nextLine().trim();
            String[] words = line.split("\\s+");
            words = trimWords(words);
            String cmdType = words[0];
            printDivider();
            try {
                switch (cmdType) {
                case "list":
                    checkForAnyTasks();
                    printTasks(tasks);
                    break;
                case "bye":
                    System.out.println("Bye. Don't come again!");
                    taskIndex = 0;
                    System.exit(0);
                    break;
                case "mark":
                    checkForAnyTasks();
                    checkForArgs(words);
                    int taskNumMark = checkMarkingIndex(words);
                    tasks.get(taskNumMark).setDone();
                    System.out.println("Marked Task " + (taskNumMark + 1) + " successfully!");
                    System.out.println(tasks.get(taskNumMark).toString());
                    break;
                case "unmark":
                    checkForAnyTasks();
                    checkForArgs(words);
                    int taskNumUnmark = checkUnmarkingIndex(words);
                    tasks.get(taskNumUnmark).resetDone();
                    System.out.println("Unmarked Task " + (taskNumUnmark + 1) + " successfully!");
                    System.out.println(tasks.get(taskNumUnmark).toString());
                    break;
                case "deadline":
                    checkForArgs(words);
                    tasks.add(parseDeadline(words));
                    printTaskCreation(tasks.get(taskIndex), taskIndex);
                    taskIndex += 1;
                    break;
                case "todo":
                    checkForArgs(words);
                    tasks.add(parseTodo(words));
                    printTaskCreation(tasks.get(taskIndex), taskIndex);
                    taskIndex += 1;
                    break;
                case "event":
                    checkForArgs(words);
                    tasks.add(parseEvent(words));
                    printTaskCreation(tasks.get(taskIndex), taskIndex);
                    taskIndex += 1;
                    break;
                case "delete":
                    checkForArgs(words);
                    int delIndex = checkTargetIndex(words);
                    String deletedToString = tasks.get(delIndex).toString();
                    tasks.remove(delIndex);
                    printDelAck(delIndex, deletedToString);
                    taskIndex -= 1;
                    break;
                case "save":
                    checkForAnyTasks();
                    System.out.println("Saving tasks to file...");
                    createDataDir();
                    s.save(tasks);
                    printSavedTasks();
                    break;
                default:
                    throw new ClovisException.InvalidInput();
                }
            } catch (ClovisException.ArgumentValueMissing e) {
                System.out.println("Missing Task Description");
            } catch (ClovisException.InvalidInput e) {
                System.out.println("Don't give me nonsense! Re-enter!");
            } catch (ClovisException.TaskAlreadyMarkedCorrectly e) {
                System.out.println("The task was already marked correctly!");
            } catch (ClovisException.MissingArgument e) {
                System.out.println("Missing argument!");
            } catch (ClovisException.NoActiveTasks e) {
                System.out.println("There are currently no active tasks!");
            } catch (ClovisException.MissingDeadlineArgument e) {
                System.out.println("Missing deadline!");
            } catch (ClovisException.MissingEventArguments e) {
                System.out.println("Missing event from or to dates!");
            } catch (ClovisException.TargetIndexOutOfRange e) {
                System.out.println("Target index out of range!");
            } catch (IOException e) {
                System.out.println("You're cooked");
            }
            printDivider();
        }

    }

    private static void checkForAnyTasks() throws ClovisException.NoActiveTasks{
        if  (tasks.isEmpty()) {
            throw new ClovisException.NoActiveTasks();
        }
    }











    public static String[] trimWords(String[] words) {
        String[] output = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            output[i] = words[i].trim();
        }
        return output;
    }


    public static int findParamIndex(String[] words, String keyword) throws ClovisException.ArgumentValueMissing {
        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(keyword)) {
                return i;
            }
        }
        //TODO add keyword to show which argument was missing
        throw new ClovisException.ArgumentValueMissing();
    }

    private static void checkIndexOutOfScope(int index) throws ClovisException.TargetIndexOutOfRange {
        if (index < 0 || index > tasks.size() - 1) {
            throw new ClovisException.TargetIndexOutOfRange();
        }
    }

    public static void checkForArgs(String[] words) throws ClovisException.MissingArgument {
        if (words.length == 1) {
            throw new ClovisException.MissingArgument();
        }
    }

    public static String assembleStrFromArrIndexes(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static String assembleStrFromArrIndexes(String[] array, int startIndex) {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static int checkMarkingIndex (String[] words) throws ClovisException.TaskAlreadyMarkedCorrectly {
        int taskIndex = checkTargetIndex(words);
        if (tasks.get(taskIndex).isDone()) {
            throw new ClovisException.TaskAlreadyMarkedCorrectly();
        }
        return taskIndex;
    }

    public static int checkUnmarkingIndex (String[] words) throws ClovisException.TaskAlreadyMarkedCorrectly {
        int taskIndex = checkTargetIndex(words);
        if (!tasks.get(taskIndex).isDone()) {
            throw new ClovisException.TaskAlreadyMarkedCorrectly();
        }
        return taskIndex;
    }

    public static int checkTargetIndex(String[] words) throws ClovisException.TargetIndexOutOfRange{
        int targetIndex = Integer.parseInt(words[1]) - 1;
        checkIndexOutOfScope(targetIndex);
        return targetIndex;
    }

    public static Event parseEvent(String[] words) throws ClovisException.MissingEventArguments {
        int fromIndex;
        int toIndex;
        try {
            fromIndex = findParamIndex(words, "/from");
            toIndex = findParamIndex(words, "/to");
        } catch (ClovisException.ArgumentValueMissing e) {
            throw new ClovisException.MissingEventArguments();
        }
        String description = assembleStrFromArrIndexes(words, 1, fromIndex);
        String startTime = assembleStrFromArrIndexes(words, fromIndex + 1, toIndex);
        String endTime = assembleStrFromArrIndexes(words, toIndex + 1);
        return new Event(description,startTime,endTime);
    }

    public static Deadline parseDeadline(String[] words) throws ClovisException.MissingDeadlineArgument {
        int dateIndex;
        try {
            dateIndex = findParamIndex(words, "/by");
        } catch (ClovisException.ArgumentValueMissing e) {
            throw new ClovisException.MissingDeadlineArgument();
        }
        String description = assembleStrFromArrIndexes(words,1,dateIndex);
        String deadlineTime = assembleStrFromArrIndexes(words,dateIndex+1);
        return new Deadline(description,deadlineTime);
    }

    public static Todo parseTodo (String[] words) {
        return new Todo(assembleStrFromArrIndexes(words,1));
    }

}
