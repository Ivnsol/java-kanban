package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();;


    //private final int HISTORY_SIZE = 10;
    //private List<Task> latestTask = new ArrayList<>();;
    private int nextEpicId = 1;
    private int nextTaskId = 1;
    private int nextSubtaskId = 1;

    @Override
    // Метод получения списка всех задач для эпика
        public void getAllEpic() {
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                Integer key = entry.getKey();
                Epic epic = entry.getValue();
                System.out.println( key + ". " + epic.getTitle());
            }
        }


    @Override
    // Метод получения списка всех задач для обычных задач
    public void getAllTask() {
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Integer key = entry.getKey();
            Task task = entry.getValue();
            System.out.println(key + ". " + task.getTitle());
        }
    }

    @Override
    // Метод получения списка всех задач для подзадач
    public void getAllSubTask() {
            for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
                Integer key = entry.getKey();
                SubTask subTask = entry.getValue();
                System.out.println(key + ". " + subTask.getTitle());
            }
        }

    @Override
    // Метод удаления всех задач для эпика
    public void removeAllTasksForEpic(Epic epic) {
        epics.clear();
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
    // Метод получения задачи по идентификатору для эпика
    public void getTaskByIdForEpic(int epicIds) {
        epics.get(epicIds);
        historyManager.add(epics.get(epicIds));
        /*latestTask.add(epics.get(epicIds));
        if (latestTask.size() > HISTORY_SIZE) {
            latestTask.remove(0);
        } */
    }

    @Override
    // Метод получения задачи по идентификатору для обычных задач
    public void getTaskByIdForTask(int taskId) {
        tasks.get(taskId);
        historyManager.add(tasks.get(taskId));
        /*latestTask.add(tasks.get(taskId));
        if (latestTask.size() > HISTORY_SIZE) {
            latestTask.remove(0);
        }*/
    }

    @Override
    // Метод получения задачи по идентификатору для подзадач
    public void getTaskByIdForSubTask(int subTaskId) {
        subTasks.get(subTaskId);
        historyManager.add(subTasks.get(subTaskId));
        /*latestTask.add(subTasks.get(subTaskId));
        if (latestTask.size() > HISTORY_SIZE) {
            latestTask.remove(0);
        }*/
    }

    @Override
    // Метод создания эпика
    public void addEpic(Epic epic) {
        epic.setId(nextEpicId);
        epics.put(epic.getId(), epic);
        nextEpicId++;
        nextSubtaskId = 1;
    }

    @Override
    // Метод добавления subTask
    public void addSubTask(SubTask subTask) {
        subTask.setId(nextSubtaskId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.setSubTasksIds(subTask.getId());
        updateEpic(epic, subTask.getEpicId());
        nextSubtaskId++;
    }

    @Override
    // Метод добавления задачи
    public void addTask(Task task) {
        task.setId(nextTaskId);
        tasks.put(task.getId(), task);
        nextTaskId++;
    }

    @Override
    // Метод обновления эпика по идентификатору
    public void updateEpic(Epic epic, int id) {
        tasks.put(epic.getId(), epic);
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
    public void updateTask (Task task, int id) {
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

    public void getHistory(){
        historyManager.getHistory();
    }
}



