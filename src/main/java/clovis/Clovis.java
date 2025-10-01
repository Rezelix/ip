package clovis;


import clovis.Exceptions.ClovisException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Clovis {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Clovis(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        parser = new Parser(ui,tasks,storage);
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
                parser.switchCase(cmd,words);
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
}
