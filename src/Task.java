import java.util.ArrayList;

public class Task {
    protected String title;
    protected int id;
    protected String status;
    protected String description;
    protected int epicId;
    protected ArrayList<SubTask> subTasks = new ArrayList<>();
    public Task(String title) {
        this.title = title;
        this.status = "NEW";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}