import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Task> tasks = new ArrayList<>();

    public Epic(String title) {
        super(title);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}

