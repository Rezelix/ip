package clovis;


import java.io.IOException;

public class Clovis {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Clovis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
//        try {
//            //tasks = new TaskList(storage.load());
//            tasks = new TaskList();
//        } catch (ClovisException e) {
//            //ui.showLoadingError();
//            tasks = new TaskList();
//        }
    }

    public static void main(String[] args) {
        new Clovis("data/tasks.txt").run();
    }

    public void run(){
        ui.printClovisIntro();
        while (true) {
            String line = ui.readCommand();
            try {
                String[] words = Parser.splitWords(line);
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
                default:
                    throw new ClovisException.InvalidInput();
                }

            } catch (ClovisException.ArgumentValueMissing e) {
                ui.printError("Missing Task Description");
                ui.printDivider();
            } catch (ClovisException.InvalidInput e) {
                ui.printError("Don't give me nonsense! Re-enter!");
                ui.printDivider();
            } catch (ClovisException.TaskAlreadyMarkedCorrectly e) {
                ui.printError("The task was already marked correctly!");
                ui.printDivider();
            } catch (ClovisException.MissingArgument e) {
                ui.printError("Missing argument!");
                ui.printDivider();
            } catch (ClovisException.NoActiveTasks e) {
                ui.printError("There are currently no active tasks!");
                ui.printDivider();
            } catch (ClovisException.MissingDeadlineArgument e) {
                ui.printError("Missing deadline!");
                ui.printDivider();
            } catch (ClovisException.MissingEventArguments e) {
                ui.printError("Missing event from or to dates!");
                ui.printDivider();
            } catch (ClovisException.TargetIndexOutOfRange e) {
                ui.printError("Target index out of range!");
                ui.printDivider();
            } catch (IOException e) {
                ui.printError("You're cooked");
                ui.printDivider();
            }
        }

    }

    private void handleUnmarking(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int unmarkIndex = Parser.getTargetIndex(words);
        tasks.unmarkTask(unmarkIndex);
        ui.printUnmarkAck(unmarkIndex, tasks.get(unmarkIndex));
        ui.printDivider();
    }

    private void handleMarking(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int markIndex = Parser.getTargetIndex(words);
        tasks.markTask(markIndex);
        ui.printMarkAck(markIndex,tasks.get(markIndex));
        ui.printDivider();
    }

    private void handleDeletion(String[] words) {
        Parser.checkForArgs(words);
        tasks.checkForAnyTasks();
        int delIndex = Integer.parseInt(words[1]) - 1;
        String deletedTaskStr = tasks.get(delIndex).toString();
        tasks.delete(delIndex);
        ui.printTaskDeletion(deletedTaskStr, delIndex, tasks.size());
        ui.printDivider();
    }

    private void handleSaving() throws IOException {
        tasks.checkForAnyTasks();
        ui.printSaving();
        storage.createDataDir();
        storage.save(tasks.getAllTasks());
        ui.printSavedTasks();
        ui.printDivider();
    }

    private void handleEvent(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseEvent(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
        ui.printDivider();
    }

    private void handleDeadline(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseDeadline(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
        ui.printDivider();
    }

    private void handleTodo(String[] words) {
        Parser.checkForArgs(words);
        tasks.add(Parser.parseTodo(words));
        ui.printTaskCreation(tasks.getLatestTask(), tasks.size());
        ui.printDivider();
    }

    private void handleList() {
        tasks.checkForAnyTasks();
        ui.printTasks(tasks.getAllTasks());
        ui.printDivider();
    }

}
