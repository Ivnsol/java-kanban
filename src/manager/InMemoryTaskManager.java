package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toSet;


public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Epic> epics;
    protected final Map<Integer, SubTask> subTasks;
    protected final Map<Integer, Task> tasks;
    protected final HistoryManager historyManager = new InMemoryHistoryManager();
    protected int nextId;


    public InMemoryTaskManager() {
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
    public void removeAllTasksForEpic() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    // Метод удаления всех задач для обычных задач
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    // Метод удаления всех задач для подзадач
    public void removeAllSubTask() {
        subTasks.clear();
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            value.getSubTasksIds().clear();
        }
    }

    @Override
    public void removeEpicById(int epicIds) {
        Epic ep = epics.get(epicIds);
        for (Integer subTasksId : ep.getSubTasksIds()) {
            subTasks.remove(subTasksId);
            historyManager.remove(subTasksId);
        }
        ep.getSubTasksIds().clear();
        epics.remove(epicIds);
        historyManager.remove(epicIds);
    }

    @Override
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void removeSubtaskId(int subTaskId) {
        Epic ep = epics.get(subTasks.get(subTaskId).getEpicId());
        ep.deleteSubTasksFromEpic(subTaskId);
        historyManager.remove(subTaskId);
        subTasks.remove(subTaskId);
        updateEpic(ep);
    }


    @Override
    // Метод получения задачи по идентификатору для эпика
    public Task getTaskByIdForEpic(int epicIds) {
        Epic epic = epics.get(epicIds);
        historyManager.add(epic);
        return epic;
    }

    @Override
    // Метод получения задачи по идентификатору для обычных задач
    public Task getTaskByIdForTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        return task;
    }

    @Override
    // Метод получения задачи по идентификатору для подзадач
    public Task getTaskByIdForSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        historyManager.add(subTask);
        return subTask;
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
        try { //проверка на совпадения
            checkTasksSameStartTime(subTask);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.setSubTasksIds(subTask.getId());
        updateEpic(epic);
        nextId++;
    }

    @Override
    // Метод добавления задачи
    public void addTask(Task task) {
        task.setId(nextId);
        try { //проверка на совпадения
            checkTasksSameStartTime(task);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        tasks.put(task.getId(), task);
        nextId++;
    }

    @Override
    // Метод обновления эпика по идентификатору
    //TODO: разбить метод
    public void updateEpic(Epic epic) {
        //chage for status
        var statuses = epic.getSubTasksIds().stream()
                .map(subTasks::get)
                .map(Task::getStatus)
                .collect(toSet());
        if (statuses.size() == 1) {
            String statusesAsString = statuses.iterator().next().toString();
            epic.setStatus(statusesAsString);
        } else if (statuses.isEmpty()) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
        }

        //start time
        Optional<LocalDateTime> startTimeForEpic = epic.getSubTasksIds().stream()
                .map(subTasks::get)
                .map(Task::getStartTime)
                .min(LocalDateTime::compareTo);
        if (startTimeForEpic.isPresent()) {

            // duration time
            Optional<Duration> durationForEpic = Optional.of(epic.getSubTasksIds().stream()
                    .map(subTasks::get)
                    .map(Task::getDuration)
                    .reduce(Duration::plus)
                    .orElse(Duration.ZERO));

            LocalDateTime timeForCheck = startTimeForEpic.get().plus(durationForEpic.get());
            // end time
            if (startTimeForEpic.isPresent()) {
                LocalDateTime subEndTime = LocalDateTime.MIN;
                Duration subDurationForEnd = Duration.ZERO;
                for (Integer subTasksId : epic.getSubTasksIds()) {
                    SubTask sub = subTasks.get(subTasksId);
                    if (subEndTime.isBefore(sub.getEndTime())) {
                        subEndTime = sub.getEndTime();
                        subDurationForEnd = sub.getDuration();
                    }
                }
                if (subEndTime.plus(subDurationForEnd).isBefore(timeForCheck)) {
                    epic.setEndTime(subEndTime);
                    epic.setDuration(subDurationForEnd);
                } else {
                    epic.setEndTime(timeForCheck);
                    epic.setDuration(subDurationForEnd);
                }
            }
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    // Метод обновления обычной задачи по идентификатору
    public void updateTask(Task task) {
        task.setId(task.getId());
        try { //проверка на совпадения
            checkTasksSameStartTime(task);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    // Метод обновления подзадачи по идентификатору
    public void updateSubTask(SubTask subTask) {
        subTask.setId(subTask.getId());
        try { //проверка на совпадения
            checkTasksSameStartTime(subTask);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        updateEpic(epic);
    }


    @Override
    // Получение списка задач для определенного эпика
    public List<SubTask> getTasksForEpic(int epicId) {
        List<SubTask> subTaskFromEpic = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer subTasksId : epic.getSubTasksIds()) {
            subTaskFromEpic.add(subTasks.get(subTasksId));

        }
        return subTaskFromEpic;
    }

    @Override
    public List<Task> printHistory() {
        return historyManager.getHistory();
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        TreeSet<Task> prioritizedTasks = new TreeSet<>((task1, task2) -> {
            LocalDateTime dateTime1 = task1.getStartTime();
            LocalDateTime dateTime2 = task2.getStartTime();

            if (dateTime1.equals(dateTime2)) {
                return 0; // Элементы равны
            } else if (dateTime1.equals(LocalDateTime.of(0, 1, 1, 0, 0))) {
                return 1; // Элемент dateTime1 - "0", должен идти в конце
            } else if (dateTime2.equals(LocalDateTime.of(0, 1, 1, 0, 0))) {
                return -1; // Элемент dateTime2 - "0", должен идти в конце
            } else {
                return dateTime1.compareTo(dateTime2); // Сравниваем по умолчанию
            }
        });
        //take startTime from Task
        prioritizedTasks.addAll(tasks.values());
        //take startTime from subTask
        prioritizedTasks.addAll(subTasks.values());

        return prioritizedTasks;
    }

    public void checkTasksSameStartTime(Task task) {
        TreeSet<Task> prioritizedTasks = getPrioritizedTasks(); //update Set of tasks
        Optional<Task> check = prioritizedTasks.stream()
                .filter(t -> t.getStartTime().equals(task.getStartTime()))
                .findFirst();

        if (check.isPresent()) {
            throw new IllegalStateException("Время начала задачи " + task.getTitle()
                    + " не должен совпадать c добавленной ранее" + check.get().getTitle()
                    + "задача не записана, пожалуйста создайте задачу заново");
        }
    }
}


