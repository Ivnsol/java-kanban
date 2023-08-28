package model;

import java.util.Objects;

public class SubTask extends Task {
    protected int taskId;

    public SubTask(String title) {
        super(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return taskId == subTask.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taskId);
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
