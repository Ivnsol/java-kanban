package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected LocalDateTime startTime;
    protected Duration duration;
    protected Types type;
    protected String title;
    protected int id;
    protected String status;
    protected String description;
    
    public Task(String title, String description,LocalDateTime startTime,int duration) {
        this.title = title;
        this.status = "NEW";
        this.description = description;
        this.type = Types.TASK;
        this.startTime = startTime;
        this.duration = Duration.ofSeconds(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id, status, description);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) return null;
        else return startTime.plus(duration);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return  id + ", " +
                type + ", " +
                title + ", " +
                status + ", " +
                description + ", " +
                (startTime != null ? startTime.format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm")):
                        "null") + ", " +
                (duration != null ? duration.toSeconds() : "null");
    }

    public Types getType() {
        return type;
    }
    public void setType(Types type) {
        this.type = type;
    }

}