package clovis;

import clovis.Exceptions.ClovisException;
import clovis.task.Deadline;
import clovis.task.Event;
import clovis.task.Todo;

import java.io.IOException;

public class Parser {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    public Parser(Ui ui, TaskList tasks, Storage storage) {
        this.ui = ui;
        this.tasks = tasks;
        this.storage = storage;
    }

    public static String[] splitWords(String line, String regex) {
        return line.trim().split(regex);
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

    public static String assembleStr(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static String assembleStr(String[] array, int startIndex) {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static Todo parseTodo (String[] words) {
        return new Todo(assembleStr(words,1));
    }

    public static Deadline parseDeadline(String[] words) throws ClovisException.MissingDeadlineArgument {
        int dateIndex;
        try {
            dateIndex = findParamIndex(words, "/by");
        } catch (ClovisException.ArgumentValueMissing e) {
            throw new ClovisException.MissingDeadlineArgument();
        }
        String description = assembleStr(words,1,dateIndex);
        String deadlineTime = assembleStr(words,dateIndex+1);
        return new Deadline(description,deadlineTime);
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
        String description = assembleStr(words, 1, fromIndex);
        String startTime = assembleStr(words, fromIndex + 1, toIndex);
        String endTime = assembleStr(words, toIndex + 1);
        return new Event(description,startTime,endTime);
    }

    public static int getTargetIndex(String[] words) {
        return Integer.parseInt(words[1]) - 1;
    }

    public static void checkForArgs(String[] words) throws ClovisException.MissingArgument {
        if (words.length == 1) {
            throw new ClovisException.MissingArgument();
        }
    }

    public void switchCase(String cmd, String[] words) throws IOException {
        switch (cmd) {
        case "list":
            handleList();
            break;
        case "todo":
            handleTodo(words);
            break;
        case "deadline":
            handleDeadline(words);
            break;
        case "event":
            handleEvent(words);
            break;
        case "save":
            handleSaving();
            break;
        case "delete":
            handleDeletion(words);
            break;
        case "bye":
            ui.printMessage("Bye. Don't come again!");
            System.exit(0);
            break;
        case "mark":
            handleMarking(words);
            break;
        case "unmark":
            handleUnmarking(words);
            break;
        case "deleteAll":
            tasks.deleteAllTasks();
            ui.printDeleteAll();
            break;
        case "find":
            handleFindKeyword(words);
            break;
        default:
                throw new ClovisException.InvalidInput();
        }
    }

    private void handleUnmarking(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int unmarkIndex = Parser.getTargetIndex(words);
        tasks.unmarkTask(unmarkIndex);
        ui.printUnmarkAck(unmarkIndex, tasks.get(unmarkIndex));
    }

    private void handleMarking(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int markIndex = Parser.getTargetIndex(words);
        tasks.markTask(markIndex);
        ui.printMarkAck(markIndex,tasks.get(markIndex));
    }

    private void handleDeletion(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int delIndex = Integer.parseInt(words[1]) - 1;
        String deletedTaskStr = tasks.get(delIndex).toString();
        tasks.delete(delIndex);
        ui.printTaskDeletion(deletedTaskStr, delIndex, tasks.size());
    }

    private void handleSaving() throws IOException {
        tasks.checkForAnyTasks();
        ui.printSaving();
        storage.createDataDir();
        storage.save(tasks.getAllTasks());
        ui.printSavedTasks();
    }

    private void handleEvent(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseEvent(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleDeadline(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseDeadline(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleTodo(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseTodo(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleList() {
        tasks.checkForAnyTasks();
        ui.printTasks(tasks.getAllTasks());
    }

    private void handleFindKeyword(String[] words) {
        Parser.checkForArgs(words);
        ui.printTasks(tasks.find(words[1]));
    }
}
