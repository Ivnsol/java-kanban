package manager;

public class Managers {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    public Managers(TaskManager taskManager, HistoryManager historyManager) {
        this.taskManager = taskManager;
        this.historyManager = historyManager;
    }

    public static Managers getDefault() {
        TaskManager taskManager = new InMemoryTaskManager();
        HistoryManager historyManager = new InMemoryHistoryManager();
        return new Managers(taskManager, historyManager);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}