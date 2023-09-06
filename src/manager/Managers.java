package manager;

public class Managers {
    public Managers() {
    }

    public static TaskManager getDefault(){
        return new <TaskManager> InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new <HistoryManager> InMemoryHistoryManager();
    }
}
