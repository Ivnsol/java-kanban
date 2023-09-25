package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Epic> epics;
    private Map<Integer, SubTask> subTasks;
    private Map<Integer, Task> tasks;
    private final HistoryManager historyManager;
    private int nextId;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.nextId = 1;
    }

    public void printEpicTask() { //метод для вывода всех задач для эпика в консоль
    for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
        Integer key = entry.getKey();
        Epic epic = entry.getValue();
        System.out.println(key + ". " + epic.getTitle());
        }
    }
    @Override
    // Метод получения списка всех задач для эпика
        public Collection<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }


    @Override
    // Метод получения списка всех задач для обычных задач
    public Collection<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    // Метод получения списка всех задач для подзадач
    public Collection<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
        }

    @Override
    // Метод удаления всех задач для эпика
    public void removeAllTasksForEpic(Epic epic) {
        epics.clear();
        for (Integer subTasksId : epic.getSubTasksIds()) {
            subTasks.remove(subTasksId);
        }
    }

    @Override
    // Метод удаления всех задач для обычных задач
    public void removeAllTask(Task task) {
        tasks.clear();
    }

    @Override
    // Метод удаления всех задач для подзадач
    public void removeAllSubTask(SubTask subTask) {
        subTasks.clear();
        Epic epic = epics.get(subTask.getEpicId());
        updateEpic(epic, subTask.getEpicId());
    }

    @Override
    public void removeEpicById(int epicIds){
        Epic ep = epics.get(epicIds);
        for (Integer subTasksId : ep.getSubTasksIds()) {
            subTasks.remove(subTasksId);
        }
        ep.getSubTasksIds().clear();
        epics.remove(epicIds);
        historyManager.remove(epicIds);
    }

    @Override
    public void removeTaskById(int taskId){
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void removeSubtaskId(int subTaskId){
        subTasks.remove(subTaskId);
        Epic ep = epics.get(subTaskId);
        ep.getSubTasksIds().remove(subTaskId);
        updateEpic(ep, subTaskId);
        historyManager.remove(subTaskId);
    }


    @Override
    // Метод получения задачи по идентификатору для эпика
    public void getTaskByIdForEpic(int epicIds) {
        Epic epic = epics.get(epicIds);
        historyManager.add(epic);
    }

    @Override
    // Метод получения задачи по идентификатору для обычных задач
    public void getTaskByIdForTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
    }

    @Override
    // Метод получения задачи по идентификатору для подзадач
    public void getTaskByIdForSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        historyManager.add(subTask);
    }

    @Override
    // Метод создания эпика
    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epics.put(epic.getId(), epic);
        nextId++;
    }

    @Override
    // Метод добавления subTask
    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.setSubTasksIds(subTask.getId());
        updateEpic(epic, subTask.getEpicId());
        nextId++;
    }

    @Override
    // Метод добавления задачи
    public void addTask(Task task) {
        task.setId(nextId);
        tasks.put(task.getId(), task);
        nextId++;
    }

    @Override
    // Метод обновления эпика по идентификатору
    public void updateEpic(Epic epic, int id) {
        epics.put(epic.getId(), epic);
        if (!epic.getStatus().equals("DONE")) {
            for (Integer subTasksId : epic.getSubTasksIds()) {
                SubTask subTask = subTasks.get(subTasksId);
                if (subTask.getStatus().equals(Status.DONE)) {
                    epic.setStatus("DONE");
                    break;
                } else if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
                    epic.setStatus("IN_PROGRESS");
                }
            }
        }
        tasks.put(epic.getId(), epic);
    }

    @Override
    // Метод обновления обычной задачи по идентификатору
    public void updateTask(Task task, int id) {
        task.setId(id);
        tasks.put(task.getId(), task);
    }

    @Override
    // Метод обновления подзадачи по идентификатору
    public void updateSubTask(SubTask subTask, int id) {
        subTask.setId(id);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        updateEpic(epic, subTask.getEpicId());
        /* вариант обновления №2, но поскольку для эпика есть отдельный метод, прописал отдельно
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            SubTask subTaskStatus = entry.getValue();
            if (subTaskStatus.getStatus().equals("DONE")) {
                epic.setStatus("DONE");
            } else if (subTaskStatus.getStatus().equals("IN_PROGRESS") ||
                        subTaskStatus.getStatus().equals("DONE")) {
                epic.setStatus("IN_PROGRESS");
            } */

    }


    @Override
    // Получение списка задач для определенного эпика
    public void getTasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        for (Integer subTasksId : epic.getSubTasksIds()) {
            SubTask subTask = subTasks.get(subTasksId);
            System.out.println(subTask);
        }
    }

    @Override
    public List<Task> printHistory(){
        return historyManager.getHistory();
    }
}



