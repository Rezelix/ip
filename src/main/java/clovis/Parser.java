package clovis;

import clovis.exceptions.ArgumentValueMissing;
import clovis.exceptions.DataDirCouldNotBeMade;
import clovis.exceptions.InvalidInput;
import clovis.exceptions.KeywordNotFound;
import clovis.exceptions.MissingArgument;
import clovis.exceptions.MissingDeadlineArgument;
import clovis.exceptions.MissingEventArguments;
import clovis.exceptions.NoActiveTasks;
import clovis.exceptions.TargetIndexOutOfRange;
import clovis.exceptions.TaskAlreadyMarkedCorrectly;
import clovis.exceptions.WrongArgumentFormat;

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

    /**
     * Returns a String Array consisting of words taken from a phrase.
     * Whitespace is removed based off the regex parameter.
     * If a single word is entered in the param line, the output String Array will only have a single element
     *
     * @param line
     * @param regex
     * @return String Array of the words in line
     */
    public static String[] splitWords(String line, String regex) {
        return line.trim().split(regex);
    }

    /**
     * Returns the index within a String Array where a specified keyword is found.
     * Throws the exception ArgumentValueMissing if keyword is not found
     *
     * @param words
     * @param keyword
     * @return index of keyword within String Array
     * @throws ArgumentValueMissing If the user-specified target keyword/index is missing in the String.
     */
    public static int findParamIndex(String[] words, String keyword) throws ArgumentValueMissing {
        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(keyword)) {
                return i;
            }
        }
        throw new ArgumentValueMissing();
    }

    /**
     * Returns a String consisting of multiple words in a String Array formed to a phrase.
     * The parameters startIndex and endIndex indicate from which index to which index
     * the words from the String Array should be from
     *
     * @param array
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String assembleStr(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    /**
     * Returns a String consisting of multiple words in a String Array formed to a single string.
     * The parameters startIndex indicate from which index the words from the String Array
     * should the resulting string start from.
     *
     * @param array
     * @param startIndex
     * @return
     */
    public static String assembleStr(String[] array, int startIndex) throws MissingArgument {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        if (output.equals("")) {
            throw new MissingArgument();
        }
        return output;
    }

    /**
     * Instantiates a Todo object, which is a task with only a description.
     *
     * @param words
     * @return Todo object, which is a task with only a description.
     */
    public static Todo parseTodo(String[] words) throws MissingArgument {
        return new Todo(assembleStr(words, 1));
    }

    /**
     * Instantiates a Deadline object, which is a task with a description and a deadline date.
     * The MissingDeadlineArgument is thrown if there is no deadline parameter input by the user.
     *
     * @param words
     * @return Deadline Task Object, with a description string and a by date/time
     * @throws MissingDeadlineArgument If missing deadline argument in task creation.
     */
    public static Deadline parseDeadline(String[] words) throws MissingDeadlineArgument, MissingArgument {
        int dateIndex;
        try {
            dateIndex = findParamIndex(words, "/by");
        } catch (ArgumentValueMissing e) {
            throw new MissingDeadlineArgument();
        }
        String description = assembleStr(words, 1, dateIndex);
        String deadlineTime = assembleStr(words, dateIndex + 1);
        return new Deadline(description, deadlineTime);
    }

    /**
     * Instantiates an Event object, which is a task with a description, a starting date and an ending date.
     *
     * @param words
     * @return Event Object containing a description, a start date and to date
     * @throws MissingEventArguments If missing either from or to date in event task creation.
     */
    public static Event parseEvent(String[] words) throws MissingEventArguments, MissingArgument {
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
        return new Event(description, startTime, endTime);
    }

    /**
     * Returns the index of the user specified target index.
     * Usually used for task deletion, where a user will pick a task to delete if they wish to.
     *
     * @param words
     * @return index of targeted task
     */
    public static int getTargetIndex(String[] words) throws WrongArgumentFormat {
        int result;
        try {
            result = Integer.parseInt(words[1]) - 1;
        } catch (NumberFormatException e) {
            throw new WrongArgumentFormat();
        }
        return result;
    }

    /**
     * Checks if there are missing arguments in the user's entry.
     * Used early within user command where an additional parameter is required.
     *
     * @param words
     * @throws MissingArgument If the user did not enter an argument for the command they tried to do.
     */
    public static void checkForArgs(String[] words) throws MissingArgument {
        if (words.length == 1) {
            throw new MissingArgument();
        }
    }

    /**
     * Switch case which takes the user input and calls methods to execute commands.
     *
     * @param cmd
     * @param words
     * @throws ArgumentValueMissing       If the user-specified target keyword/index is missing in the String.
     * @throws InvalidInput               If the user has entered an invalid input.
     * @throws TaskAlreadyMarkedCorrectly If the user tries to mark a task that is already marked.
     * @throws MissingArgument            If the user did not enter an argument for the command they tried to do.
     * @throws NoActiveTasks              If reading or writing to task objects when they are none activated.
     * @throws MissingDeadlineArgument    If missing deadline argument in task creation.
     * @throws MissingEventArguments      If missing either from or to date in event task creation.
     * @throws TargetIndexOutOfRange      If the user-specified target index is out of range in the ArrayList.
     * @throws IOException                If a file could not be read or written to.
     * @throws DataDirCouldNotBeMade      If the data directory could not be made.
     * @throws KeywordNotFound            If the user specified keyword cannot be found in any of the objects in the ArrayList.
     */
    public void switchCase(String cmd, String[] words) throws ArgumentValueMissing, InvalidInput,
            TaskAlreadyMarkedCorrectly, MissingArgument, NoActiveTasks, MissingDeadlineArgument,
            MissingEventArguments, TargetIndexOutOfRange, IOException, DataDirCouldNotBeMade,
            KeywordNotFound, WrongArgumentFormat {
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
            ui.printBye();
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

    /**
     * Calls methods from multiple classes to execute to unmarking a task
     *
     * @param words
     * @throws MissingArgument            If the user did not enter an argument for the command they tried to do.
     * @throws NoActiveTasks              If reading or writing to task objects when they are none activated.
     * @throws TaskAlreadyMarkedCorrectly If the user tries to mark a task that is already marked.
     * @throws TargetIndexOutOfRange      If the user-specified target index is out of range in the ArrayList.
     */
    private void handleUnmarking(String[] words) throws MissingArgument, NoActiveTasks, TaskAlreadyMarkedCorrectly, TargetIndexOutOfRange, WrongArgumentFormat {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int unmarkIndex = Parser.getTargetIndex(words);
        tasks.unmarkTask(unmarkIndex);
        ui.printUnmarkAck(unmarkIndex, tasks.get(unmarkIndex));
    }

    /**
     * Calls methods from multiple classes to execute to mark a task
     *
     * @param words
     * @throws MissingArgument            If the user did not enter an argument for the command they tried to do.
     * @throws NoActiveTasks              If reading or writing to task objects when they are none activated.
     * @throws TargetIndexOutOfRange      If the user-specified target index is out of range in the ArrayList.
     * @throws TaskAlreadyMarkedCorrectly If the user tries to mark a task that is already marked.
     */
    private void handleMarking(String[] words) throws MissingArgument, NoActiveTasks, TargetIndexOutOfRange, TaskAlreadyMarkedCorrectly, WrongArgumentFormat {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int markIndex = Parser.getTargetIndex(words);
        tasks.markTask(markIndex);
        ui.printMarkAck(markIndex, tasks.get(markIndex));
    }

    /**
     * Calls methods from multiple classes to execute to delete a task
     *
     * @param words
     * @throws MissingArgument       If the user did not enter an argument for the command they tried to do.
     * @throws TargetIndexOutOfRange If the user-specified target index is out of range in the ArrayList.
     * @throws NoActiveTasks         If reading or writing to task objects when they are none activated.
     */
    private void handleDeletion(String[] words) throws MissingArgument, TargetIndexOutOfRange, NoActiveTasks, WrongArgumentFormat {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int delIndex = getTargetIndex(words);
        String deletedTaskStr = tasks.get(delIndex).toString();
        tasks.delete(delIndex);
        ui.printTaskDeletion(deletedTaskStr, delIndex, tasks.size());
    }

    /**
     * Calls methods from multiple classes to execute to save tasks
     *
     * @throws IOException
     * @throws NoActiveTasks         If reading or writing to task objects when they are none activated.
     * @throws DataDirCouldNotBeMade If the data directory could not be made.
     */
    private void handleSaving() throws IOException, NoActiveTasks, DataDirCouldNotBeMade {
        tasks.checkForAnyTasks();
        ui.printSaving();
        storage.createDataDir();
        storage.save(tasks.getAllTasks());
        ui.printSavedTasks();
    }

    /**
     * Calls methods from multiple classes to execute to create an Event Task Object
     *
     * @param words
     * @throws MissingArgument       If the user did not enter an argument for the command they tried to do.
     * @throws MissingEventArguments If missing either from or to date in event task creation.
     * @throws NoActiveTasks         If reading or writing to task objects when they are none activated.
     */
    private void handleEvent(String[] words) throws MissingArgument, MissingEventArguments, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseEvent(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    /**
     * Calls methods from multiple classes to execute to create a Deadline Task Object
     *
     * @param words
     * @throws MissingArgument         If the user did not enter an argument
     * @throws MissingDeadlineArgument If missing deadline argument in task creation.
     * @throws NoActiveTasks           If reading or writing to task objects when they are none activated.
     */
    private void handleDeadline(String[] words) throws MissingArgument, MissingDeadlineArgument, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseDeadline(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    /**
     * Calls methods from multiple classes to execute to create a Todo Task Object
     *
     * @param words
     * @throws MissingArgument If the user did not enter an argument
     * @throws NoActiveTasks   If reading or writing to task objects when they are none activated.
     */
    private void handleTodo(String[] words) throws MissingArgument, NoActiveTasks {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseTodo(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
    }

    /**
     * Calls methods from multiple classes to execute to list all the active tasks
     *
     * @throws NoActiveTasks If there are no task active
     */
    private void handleList() throws NoActiveTasks {
        tasks.checkForAnyTasks();
        ui.printTasks(tasks.getAllTasks());
    }

    /**
     * Calls methods from multiple classes to execute to find Tasks containing a user-specified keyword
     *
     * @param words
     * @throws MissingArgument If user didn't add a keyword to search for
     * @throws KeywordNotFound If the keyword could not be found
     */
    private void handleFindKeyword(String[] words) throws MissingArgument, KeywordNotFound {
        Parser.checkForArgs(words);
        ui.printTasks(tasks.find(words[1]));
    }
}
