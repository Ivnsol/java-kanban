package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected Types type;

    protected ArrayList<Integer> subTasksIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
        this.type = Types.EPIC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksIds, epic.subTasksIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTasksIds);
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void deleteSubTasksFromEpic(int id) {
        subTasksIds.remove(Integer.valueOf(id));
    }


    public void setSubTasksIds(Integer subTasksId) {
        subTasksIds.add(subTasksId);
    }

}

