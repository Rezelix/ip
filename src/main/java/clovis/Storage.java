package clovis;

import clovis.Exceptions.ClovisException;
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

    public Storage(String filepath) {
        this.filepath = filepath;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(this.filepath);
        for (Task task : tasks) {
            fw.write(task.toExportString() + System.lineSeparator());
        }
        fw.close();
    }

    public void createDataDir() {
        File dir = new File("data");
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new ClovisException.DataDirCouldNotBeMade();
            }
        }
    }

    public ArrayList<Task> load() throws FileNotFoundException {
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

    private File getFile() {
        File file = new File(this.filepath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to create file");
            }
        }
        return file;
    }

}
