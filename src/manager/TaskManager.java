package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.Collection;
import java.util.List;


public interface TaskManager {

    // Метод получения списка всех задач для эпика
    Collection<Epic> getAllEpic();


    // Метод получения списка всех задач для обычных задач
    Collection<Task> getAllTask();


    // Метод получения списка всех задач для подзадач
    Collection<SubTask> getAllSubTask();


    // Метод удаления всех задач для эпика
    void removeAllTasksForEpic();


    // Метод удаления всех задач для обычных задач
    void removeAllTask();


    // Метод удаления всех задач для подзадач
    void removeAllSubTask();

    void removeEpicById(int epicIds);

    void removeTaskById(int taskId);

    void removeSubtaskId(int subTaskId);

    // Метод получения задачи по идентификатору для эпика
    Task getTaskByIdForEpic(int epicIds);

    // Метод получения задачи по идентификатору для обычных задач
    Task getTaskByIdForTask(int taskId);

    // Метод получения задачи по идентификатору для подзадач
    Task getTaskByIdForSubTask(int subTaskId);

    // Метод создания эпика
    void addEpic(Epic epic);

    // Метод добавления subTask
    void addSubTask(SubTask subTask);

    // Метод добавления задачи
    void addTask(Task task);


    // Метод обновления эпика по идентификатору
    void updateEpic(Epic epic);

    // Метод обновления обычной задачи по идентификатору
    void updateTask(Task task);

    // Метод обновления подзадачи по идентификатору
    void updateSubTask(SubTask subTask);

    // Получение списка задач для определенного эпика
    void getTasksForEpic(int epicId);

    void printEpicTask();

    List<Task> printHistory();
}



