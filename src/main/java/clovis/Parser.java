package clovis;
import clovis.Exceptions.*;
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

    public static int findParamIndex(String[] words, String keyword) throws ArgumentValueMissing {
        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(keyword)) {
                return i;
            }
        }
        throw new ArgumentValueMissing();
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

    public static Deadline parseDeadline(String[] words) throws MissingDeadlineArgument {
        int dateIndex;
        try {
            dateIndex = findParamIndex(words, "/by");
        } catch (ArgumentValueMissing e) {
            throw new MissingDeadlineArgument();
        }
        String description = assembleStr(words,1,dateIndex);
        String deadlineTime = assembleStr(words,dateIndex+1);
        return new Deadline(description,deadlineTime);
    }

    public static Event parseEvent(String[] words) throws MissingEventArguments {
        int fromIndex;
        int toIndex;
        try {
            fromIndex = findParamIndex(words, "/from");
            toIndex = findParamIndex(words, "/to");
        } catch (ArgumentValueMissing e) {
            throw new MissingEventArguments();
        }
        String description = assembleStr(words, 1, fromIndex);
        String startTime = assembleStr(words, fromIndex + 1, toIndex);
        String endTime = assembleStr(words, toIndex + 1);
        return new Event(description,startTime,endTime);
    }

    public static int getTargetIndex(String[] words) {
        return Integer.parseInt(words[1]) - 1;
    }

    public static void checkForArgs(String[] words) throws MissingArgument {
        if (words.length == 1) {
            throw new MissingArgument();
        }
    }

    public void switchCase(String cmd, String[] words) throws ArgumentValueMissing, InvalidInput,
            TaskAlreadyMarkedCorrectly, MissingArgument, NoActiveTasks, MissingDeadlineArgument,
            MissingEventArguments, TargetIndexOutOfRange, IOException, DataDirCouldNotBeMade, KeywordNotFound {
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
            throw new InvalidInput();
        }
    }

    private void handleUnmarking(String[] words) throws MissingArgument, NoActiveTasks,
            TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int unmarkIndex = Parser.getTargetIndex(words);
        tasks.unmarkTask(unmarkIndex);
        ui.printUnmarkAck(unmarkIndex, tasks.get(unmarkIndex));
    }

    private void handleMarking(String[] words) throws MissingArgument, NoActiveTasks,
            TargetIndexOutOfRange, TaskAlreadyMarkedCorrectly {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int markIndex = Parser.getTargetIndex(words);
        tasks.markTask(markIndex);
        ui.printMarkAck(markIndex,tasks.get(markIndex));
    }

    private void handleDeletion(String[] words) throws MissingArgument, TargetIndexOutOfRange, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int delIndex = Integer.parseInt(words[1]) - 1;
        String deletedTaskStr = tasks.get(delIndex).toString();
        tasks.delete(delIndex);
        ui.printTaskDeletion(deletedTaskStr, delIndex, tasks.size());
    }

    private void handleSaving() throws IOException, NoActiveTasks, DataDirCouldNotBeMade {
        tasks.checkForAnyTasks();
        ui.printSaving();
        storage.createDataDir();
        storage.save(tasks.getAllTasks());
        ui.printSavedTasks();
    }

    private void handleEvent(String[] words) throws MissingArgument, MissingEventArguments, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseEvent(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleDeadline(String[] words) throws MissingArgument, MissingDeadlineArgument, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseDeadline(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleTodo(String[] words) throws MissingArgument, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseTodo(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    private void handleList() throws NoActiveTasks {
        tasks.checkForAnyTasks();
        ui.printTasks(tasks.getAllTasks());
    }

    private void handleFindKeyword(String[] words) throws MissingArgument, KeywordNotFound {
        Parser.checkForArgs(words);
        ui.printTasks(tasks.find(words[1]));
    }
}
