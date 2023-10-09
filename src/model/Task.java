package model;

import java.util.Objects;

public class Task {
    protected Types type;
    protected String title;
    protected int id;
    protected String status;
    protected String description;
    
    public Task(String title, String description) {
        this.title = title;
        this.status = "NEW";
        this.description = description;
        this.type = Types.TASK;
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
                description;
    }

    public Types getType() {
        return type;
    }
    public void setType(Types type) {
        this.type = type;
    }

}