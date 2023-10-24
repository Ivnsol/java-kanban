package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SubTask extends Task {
    protected int epicId;

    public SubTask(String title, String description, int epicId,LocalDateTime startTime,int duration)  {
        super(title,
                description,
                startTime,
                duration);
        this.epicId = epicId;
        this.type = Types.SUBTASK;
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
                epicId + ", " +
                (startTime != null ? startTime.format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm")):
                        "null") + ", " +
                (duration != null ? duration.toSeconds() : "null");
    }
}