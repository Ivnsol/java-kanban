package model;

import model.SubTask;

import java.util.ArrayList;
import java.util.Objects;

public class Task {
    protected String title;
    protected int id;
    protected String status;
    protected String description;

    protected ArrayList<SubTask> subTasks = new ArrayList<>();
    public Task(String title, String description) {
        this.title = title;
        this.status = "NEW";
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id  && Objects.equals(title, task.title) && Objects.equals(status, task.status)  && Objects.equals(subTasks, task.subTasks);
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


    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}