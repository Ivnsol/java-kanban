package manager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        File file = new File("example.txt");
        InMemoryTaskManager memory = loadFromFile(file);
        return memory;
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        manager.fileReader();
        return manager;
    }

}

