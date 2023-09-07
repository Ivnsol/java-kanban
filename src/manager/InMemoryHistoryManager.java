package manager;

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> history = new LinkedList<>();

    private static final int HISTORY_SIZE = 10;
    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > HISTORY_SIZE) {
            history.poll();
        }
    }

    @Override
    public LinkedList<Task> getHistory() {
    return history;
    }
}
