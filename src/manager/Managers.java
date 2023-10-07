package manager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        manager.fileReader();
        return manager;
    }
//    public static TaskManager getDefaultFileManager() {
//        HistoryManager historyManager = getDefaultHistory();
//        File file = new File();
//        return new FileBackedTasksManager(historyManager);
//    }

}

