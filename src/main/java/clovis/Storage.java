package clovis;

import clovis.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<Task> load() {
        throw new ClovisException.NotYetImplemented();
    }

}
