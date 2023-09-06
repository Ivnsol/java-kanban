package manager;

import model.Task;

public interface HistoryManager {

    int HISTORY_SIZE = 10;

    void add(Task task);

    void getHistory();
}
