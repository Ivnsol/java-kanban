public class SubTask extends Task {
    protected int taskId;

    public SubTask(String title) {
        super(title);
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
