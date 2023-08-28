package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;




public class ManagerTask {
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    public int nextId = 1;

    // Метод получения списка всех задач для определенных эпиков
    public ArrayList<Task> getAllTasksForEpic(Epic epic) {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : epic.getTasks()) {
            allTasks.add(task);
            allTasks.addAll(task.getSubTasks());
        }
        return allTasks;
    }

    // Метод получения списка всех задач для обычных задач
    public ArrayList<Task> getAllTasksForTask(Task task) {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(task);
        allTasks.addAll(task.getSubTasks());
        return allTasks;
    }

    // Метод получения списка всех задач для подзадач
    public ArrayList<Task> getAllTasksForSubTask(SubTask subTask) {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(subTask);
        return allTasks;
    }

    // Метод удаления всех задач для эпика
    public void removeAllTasksForEpic(Epic epic) {
        for (Task task : epic.getTasks()) {
            removeAllTasksForTask(task);
        }
        epics.remove(epic.getId());
    }

    // Метод удаления всех задач для обычных задач
    public void removeAllTasksForTask(Task task) {
        for (SubTask subTask : task.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }
        tasks.remove(task.getId());
    }

    // Метод удаления всех задач для подзадач
    public void removeAllTasksForSubTask(SubTask subTask) {
        subTasks.remove(subTask.getId());
    }

    // Метод получения задачи по идентификатору для эпика
    public Task getTaskByIdForEpic(Epic epic, int id) {
        for (Task task : epic.getTasks()) {
            if (task.getId() == id) {
                return task;
            }
            for (SubTask subTask : task.getSubTasks()) {
                if (subTask.getId() == id) {
                    return subTask;
                }
            }
        }
        return null;
    }

    // Метод получения задачи по идентификатору для обычных задач
    public Task getTaskByIdForTask(Task task, int id) {
        if (task.getId() == id) {
            return task;
        }
        for (SubTask subTask : task.getSubTasks()) {
            if (subTask.getId() == id) {
                return subTask;
            }
        }
        return null;
    }

    // Метод получения задачи по идентификатору для подзадач
    public SubTask getTaskByIdForSubTask(SubTask subTask, int id) {
        if (subTask.getId() == id) {
            return subTask;
        }
        return null;
    }

    // Метод создания объекта для эпика и добавление его в коллекцию epics
    public void createEpic(String title) {
        Epic epic = new Epic(title);
        epics.put(epic.getId(), epic);
        nextId++;
    }

    // Метод добавления задачи в эпик
    public void addTaskToEpic(Epic epic, Task task) {
        epic.addTask(task);
        tasks.put(task.getId(), task);
    }

    // Метод добавления подзадачи в задачу
    public void addSubTaskToTask(Task task, SubTask subTask) {
        task.addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);
    }

    // Метод обновления эпика по идентификатору
    public void updateEpic(Epic epic, int id) {
        epic.setId(id);
        epics.put(epic.getId(), epic);
    }

    // Метод обновления обычной задачи по идентификатору
    public void updateTask(Task task, int id) {
        task.setId(id);
        tasks.put(task.getId(), task);
    }

    // Метод обновления подзадачи по идентификатору
    public void updateSubTask(SubTask subTask, int id) {
        subTask.setId(id);
        subTasks.put(subTask.getId(), subTask);
    }

    // Получение списка задач для определенного эпика
    public ArrayList<Task> getTasksForEpic(Epic epic) {
        return epic.getTasks();
    }
}