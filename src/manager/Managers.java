package manager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        File file = new File("example.txt");
        return new FileBackedTasksManager(file);
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }


}

