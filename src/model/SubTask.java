package model;

import java.util.Objects;

public class SubTask extends Task {
    private final Types type;
    protected int epicId;

    public SubTask(String title, String description, int epicId) {
        super(title,
                description);
        this.type = Types.SUBTASK;
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id + ", " +
                type + ", " +
                title + ", " +
                status + ", " +
                description + ", " +
                epicId;
    }
}
