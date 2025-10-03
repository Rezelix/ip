package clovis;

import clovis.exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Clovis {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Clovis(String filePath) throws FileNotFoundException, DataDirCouldNotBeMade {
        ui = new Ui();
        storage = new Storage(filePath, ui);
        storage.createDataDir();
        tasks = new TaskList(storage.load());
        parser = new Parser(ui, tasks, storage);
    }

    public static void main(String[] args) {
        try {
            new Clovis("data/tasks.txt").run();
        } catch (FileNotFoundException | DataDirCouldNotBeMade e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        ui.printClovisIntro();
        String line = ui.readCommand();
        while (!line.contains("bye")) {
            String[] words = Parser.splitWords(line, "\\s+");
            String cmd = words[0];
            try {
                parser.switchCase(cmd, words);
                ui.printDivider();
            } catch (ArgumentValueMissing | InvalidInput | TaskAlreadyMarkedCorrectly | MissingArgument |
                     NoActiveTasks | MissingDeadlineArgument | MissingEventArguments | TargetIndexOutOfRange |
                     DataDirCouldNotBeMade | KeywordNotFound | IOException | WrongArgumentFormat e) {
                ui.printError(e.getMessage());
                ui.printDivider();
            }
            line = ui.readCommand();
        }
        ui.printBye();
    }
}
