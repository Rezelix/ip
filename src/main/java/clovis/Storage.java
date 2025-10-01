package clovis;

import clovis.exceptions.DataDirCouldNotBeMade;
import clovis.task.Task;
import clovis.task.Todo;
import clovis.task.Deadline;
import clovis.task.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filepath;
    private Ui ui;

    /**
     * Constructs a Storage object that is used to call methods referencing
     * any memory-related methods. The object is provided a filepath by the
     * user to read from or write to.
     * @param filepath
     * @param ui
     */
    public Storage(String filepath, Ui ui) {
        this.filepath = filepath;
        this.ui = ui;
    }

    /**
     * Creates a text file to the instantiated filepath and writes all the data
     * of each task into the file.
     * @param tasks
     * @throws IOException
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(this.filepath);
        for (Task task : tasks) {
            fw.write(task.toExportString() + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Creates a directory named "data" in the same directory the Clovis is in.
     * This directory is required for the data text file
     * @throws DataDirCouldNotBeMade
     */
    public void createDataDir() throws DataDirCouldNotBeMade {
        File dir = new File("data");
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new DataDirCouldNotBeMade();
            }
        }
    }

    /**Loads tasks from disk to memory
     * @return a mutable ArrayList of Task objects in the order they were in the instantiated filepath if it exists.
     * @throws DataDirCouldNotBeMade
     */
    public ArrayList<Task> load() throws DataDirCouldNotBeMade {
        File file = getFile();
        Scanner sc = null;
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            sc = new Scanner(file);
            arrayListConstructor(sc, tasks);
        } catch (FileNotFoundException e) {
            createDataDir();
            return tasks;
        }
        return tasks;
    }

    /**
     * Helper function for load(). Receives each line of the text file containing Task object(s)
     * data and parses the read data into the different parameters of
     * either the Todo, Deadline or Event Object.
     *
     * @param sc
     * @param tasks
     */
    public static void arrayListConstructor(Scanner sc, ArrayList<Task> tasks) {
        String[] words;
        while (sc.hasNextLine()) {
             words = sc.nextLine().trim().split("\\|");
             boolean taskIsDone = Boolean.parseBoolean(words[1]);
             String taskName = words[2];
             switch (words[0]) {
             case "T":
                 tasks.add(new Todo(taskName,taskIsDone));
                 break;
             case "D":
                 tasks.add(new Deadline(taskName,taskIsDone,words[3]));
                 break;
             case "E":
                 tasks.add(new Event(taskName,taskIsDone,words[3],words[4]));
                 break;
             }
        }
    }

    /**
     * Returns a file object and creates one if one is not found in the instantiated filepath.
     * @return a File object that is located at the instantiated file path the user had entered
     */
    private File getFile() {
        File file = new File(this.filepath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException | SecurityException e) {
                ui.printError("Failed to create file");
            }
        }
        return file;
    }

}
