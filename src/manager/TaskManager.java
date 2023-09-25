package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.*;


public interface TaskManager {

    // Метод получения списка всех задач для эпика
      Collection<Epic> getAllEpic();



    // Метод получения списка всех задач для обычных задач
     Collection<Task> getAllTask();


    // Метод получения списка всех задач для подзадач
     Collection<SubTask> getAllSubTask();




    // Метод удаления всех задач для эпика
    void removeAllTasksForEpic(Epic epic);

    // Метод удаления всех задач для обычных задач
    void removeAllTask(Task task);

    // Метод удаления всех задач для подзадач
    void removeAllSubTask(SubTask subTask);


    void removeEpicById(int epicIds);

    void removeTaskById(int taskId);

    void removeSubtaskId(int subTaskId);

    // Метод получения задачи по идентификатору для эпика
    void getTaskByIdForEpic(int epicIds);

    // Метод получения задачи по идентификатору для обычных задач
    void getTaskByIdForTask(int taskId);

    // Метод получения задачи по идентификатору для подзадач
    void getTaskByIdForSubTask(int subTaskId);

    // Метод создания эпика
    void addEpic(Epic epic);

    // Метод добавления subTask
    void addSubTask(SubTask subTask);

    // Метод добавления задачи
    void addTask(Task task);

    // Метод обновления эпика по идентификатору
    void updateEpic(Epic epic, int id);

    // Метод обновления обычной задачи по идентификатору
    void updateTask(Task task, int id);

    // Метод обновления подзадачи по идентификатору
    void updateSubTask(SubTask subTask, int id);

    // Получение списка задач для определенного эпика
    void getTasksForEpic(int epicId);

    void printEpicTask();

    List<Task> printHistory();
}



