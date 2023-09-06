package manager;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> history = new ArrayList<>();
    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > HISTORY_SIZE) {
            history.remove(0);
        }
    }

    @Override
    public void getHistory() {
        System.out.println("История задач: ");
        for (Task task : history) {
            System.out.println(task.getTitle());
        }
    }
}
