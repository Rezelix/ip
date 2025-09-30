package clovis;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Clovis {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Clovis(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            new Clovis("data/tasks.txt").run();
        } catch (FileNotFoundException e) {
            System.out.println("Cooked");
        }
    }

    public void run(){
        ui.printClovisIntro();
        while (true) {
            String line = ui.readCommand();
            try {
                String[] words = Parser.splitWords(line,"\\s+");
                String cmd = words[0];

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
                ui.printDivider();

            } catch (ClovisException.ArgumentValueMissing e) {
                ui.printError("Missing Task Description");
            } catch (ClovisException.InvalidInput e) {
                ui.printError("Don't give me nonsense! Re-enter!");
                ui.printDivider();
            } catch (ClovisException.TaskAlreadyMarkedCorrectly e) {
                ui.printError("The task was already marked correctly!");
            } catch (ClovisException.MissingArgument e) {
                ui.printError("Missing argument!");
            } catch (ClovisException.NoActiveTasks e) {
                ui.printError("There are currently no active tasks!");
            } catch (ClovisException.MissingDeadlineArgument e) {
                ui.printError("Missing deadline!");
            } catch (ClovisException.MissingEventArguments e) {
                ui.printError("Missing event from or to dates!");
            } catch (ClovisException.TargetIndexOutOfRange e) {
                ui.printError("Target index out of range!");
            } catch (IOException e) {
                ui.printError("You're cooked");
            }
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
