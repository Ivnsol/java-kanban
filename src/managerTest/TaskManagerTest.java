package managerTest;

import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @BeforeEach
    public void init() {
        Epic epic1 = new Epic("epicName1", "epicDis1",null,0);
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic("epicName2", "epicDis2",null,0);
        taskManager.addEpic(epic2);
        Task task3 = new Task("taskName3", "taskDis3", LocalDateTime.of(2023, 10, 23, 15, 10), 0);
        taskManager.addTask(task3);
        Task task4 = new Task("taskName4", "taskDis4", LocalDateTime.of(2023, 10, 23, 15, 11), 0);
        taskManager.addTask(task4);
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1, LocalDateTime.of(2023, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName6", "subTaskDis", 1, LocalDateTime.of(2023, 10, 23, 15, 13), 0);
        taskManager.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("subTaskName7", "subTaskDis", 1, LocalDateTime.of(2023, 10, 23, 15, 14), 0);
        taskManager.addSubTask(subTask7);
    }

    @AfterEach
    public void cleanup() {
        taskManager.removeAllTasksForEpic();
        taskManager.removeAllTask();
    }

    @Test
    void getAllEpicTest() {
        Collection<Epic> epicList = taskManager.getAllEpic();
        assertEquals(2, epicList.size());
    }

    @Test
    void getAllTask() {
        Collection<Task> tasks = taskManager.getAllTask();
        assertEquals(2, tasks.size());
    }

    @Test
    void getAllSubTask() {
        Collection<SubTask> subTasks = taskManager.getAllSubTask();
        assertEquals(3, subTasks.size());
    }

    @Test
    void removeAllTasksForEpic() {
        taskManager.removeAllTasksForEpic();
        Collection<Epic> epicList = taskManager.getAllEpic();
        assertTrue(epicList.isEmpty());
    }

    @Test
    void removeAllTask() {
        taskManager.removeAllTask();
        Collection<Task> tasks = taskManager.getAllTask();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void removeAllSubTask() {
        taskManager.removeAllSubTask();
        Collection<SubTask> subTasks = taskManager.getAllSubTask();
        assertTrue(subTasks.isEmpty());
    }

    @Test
    void removeEpicById() {
        //find epic id1 and check Collections before delete
        Collection<SubTask> subTasksBeforeDelete = taskManager.getAllSubTask();
        Collection<Epic> epicsBeforeDelete = taskManager.getAllEpic();
        Optional<Epic> epic = Optional.ofNullable((Epic) taskManager.getTaskByIdForEpic(1));
        assertEquals(3, subTasksBeforeDelete.size());
        assertEquals(2, epicsBeforeDelete.size());
        assertTrue(epic.isPresent());

        //throws NullPointerException after delete and check collections
        taskManager.removeEpicById(1);

        assertThrows(NullPointerException.class,
                () -> taskManager.getTaskByIdForEpic(1));

        Collection<SubTask> subTasks = taskManager.getAllSubTask();
        Collection<Epic> epics = taskManager.getAllEpic();
        assertEquals(0, subTasks.size());
        assertEquals(1, epics.size());
    }

    @Test
    void removeTaskById() {
        taskManager.removeTaskById(3);
        Collection<Task> tasks = taskManager.getAllTask();
        assertEquals(1, tasks.size());
    }

    @Test
    void removeSubtaskId() {
        taskManager.removeSubtaskId(6);
        Collection<SubTask> subTasks = taskManager.getAllSubTask();
        assertEquals(2, subTasks.size());
    }

    @Test
    void getTaskByIdForEpic() {
        taskManager.getAllEpic();
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);
        assertEquals("1, EPIC, epicName1, NEW, epicDis1, null, 0",//may be null at the end
                epic.toString());
    }

    @Test
    void getTaskByIdForTask() {
        assertEquals("3, TASK, taskName3, NEW, taskDis3, 2023.10.23 15:10, 0",//may be null at the end
                taskManager.getTaskByIdForTask(3).toString());
    }

    @Test
    void getTaskByIdForSubTask() {
        assertEquals("3, TASK, taskName3, NEW, taskDis3, 2023.10.23 15:10, 0",//may be null at the end
                taskManager.getTaskByIdForTask(3).toString());
    }

    @Test
    void addEpic() {
        Epic epic3 = new Epic("epicName3", "epicDis3",null,0);
        taskManager.addEpic(epic3);
        assertEquals(3, taskManager.getAllEpic().size());
    }

    @Test
    void addSubTask() {
        SubTask subTask8 = new SubTask("subTaskName8", "subTaskDis", 1, LocalDateTime.of(2023, 11, 23, 15, 14), 0);

        taskManager.addSubTask(subTask8);
        assertEquals(4, taskManager.getAllSubTask().size());
    }

    @Test
    void addTask() {
        Task task4 = new Task("taskName4", "taskDis4", LocalDateTime.of(2023, 10, 23, 16, 10), 0);

        taskManager.addTask(task4);
        assertEquals(3, taskManager.getAllTask().size());
    }

    @Test
    void updateEpicWithAllNewStatusSubTasksTest() {
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);
        taskManager.updateEpic(epic);
        assertEquals("NEW", epic.getStatus());
    }

    @Test
    void updateEpicWithDoneAndNewStatusSubTasksTest() {
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);

        SubTask sub8 = new SubTask("sub8", "subTaskDis", 1, LocalDateTime.of(2023, 10, 24, 15, 13), 0);
        sub8.setStatus("DONE");
        taskManager.addSubTask(sub8);

        SubTask sub9 = new SubTask("sub9", "subTaskDis", 1, LocalDateTime.of(2023, 10, 25, 15, 14), 0);
        sub9.setStatus("DONE");
        taskManager.addSubTask(sub9);

        taskManager.updateEpic(epic);
        assertEquals("IN_PROGRESS", epic.getStatus());
    }

    @Test
    void updateEpicWithAllDoneStatusSubTasksTest() {
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);

        taskManager.removeAllSubTask();

        SubTask newSub8StatusDone = new SubTask("sub8", "subTaskDis", 1, LocalDateTime.of(2023, 10, 24, 15, 13), 0);
        newSub8StatusDone.setStatus("DONE");
        taskManager.addSubTask(newSub8StatusDone);

        SubTask newSub9StatusDone = new SubTask("sub9", "subTaskDis", 1, LocalDateTime.of(2023, 10, 25, 15, 14), 0);
        newSub9StatusDone.setStatus("DONE");
        taskManager.addSubTask(newSub9StatusDone);

        taskManager.updateEpic(epic);
        assertEquals("DONE", epic.getStatus());
    }

    @Test
    void updateEpicWithAllInProgressStatusSubTasksTest() {
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);

        taskManager.removeAllSubTask();

        SubTask newSub8StatusInProgress = new SubTask("sub8", "subTaskDis", 1, LocalDateTime.of(2023, 10, 24, 15, 13), 0);
        newSub8StatusInProgress.setStatus("IN_PROGRESS");
        taskManager.addSubTask(newSub8StatusInProgress);

        SubTask newSub9StatusInProgress = new SubTask("sub9", "subTaskDis", 1, LocalDateTime.of(2023, 10, 25, 15, 14), 0);
        newSub9StatusInProgress.setStatus("IN_PROGRESS");
        taskManager.addSubTask(newSub9StatusInProgress);

        taskManager.updateEpic(epic);
        assertEquals("IN_PROGRESS", epic.getStatus());
    }

    @Test
    void updateEpicWithNoSubTasksTest() {
        Epic epic = (Epic) taskManager.getTaskByIdForEpic(1);

        taskManager.removeAllSubTask();

        taskManager.updateEpic(epic);
        assertEquals("NEW", epic.getStatus());
    }

    @Test
    void updateTask() {
        Task task3 = new Task("taskName3", "taskDis3", LocalDateTime.of(2024, 10, 23, 15, 10), 0);
        // Вызов метода updateTask
        taskManager.addTask(task3);
        taskManager.updateTask(task3);

        // Проверка ожидаемого результата
        Task updatedTask = taskManager.getTaskByIdForTask(8);
        assertEquals(task3, updatedTask);
    }

    @Test
    void updateSubTask() {
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        // Вызов метода updateTask
        taskManager.addSubTask(subTask5);
        taskManager.updateTask(subTask5);

        // Проверка ожидаемого результата
        Task updatedTask = taskManager.getTaskByIdForSubTask(8);
        assertEquals(subTask5, updatedTask);
    }

    @Test
    void CheckEpicForSubtask() {
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        // Вызов метода updateTask
        taskManager.addSubTask(subTask5);

        // Проверка ожидаемого результата
        SubTask updatedTask = (SubTask) taskManager.getTaskByIdForSubTask(8);
        assertEquals(1, updatedTask.getEpicId());
    }

    @Test
    void getPrioritizedTasks() {
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1, null, 0);
        // Вызов метода updateTask
        taskManager.addSubTask(subTask5);
        SubTask subTask9 = new SubTask("subTaskName555", "subTaskDis", 1, null, 0);
        // Вызов метода updateTask
        taskManager.addSubTask(subTask9);

        assertEquals(7, taskManager.getPrioritizedTasks().size());

    }

    @Test
    void getTasksForEpic() {
        taskManager.getTasksForEpic(1);
        assertEquals(3, taskManager.getTasksForEpic(1).size());
    }

    @Test
    void printHistory() {
        List<Task> history = taskManager.printHistory();
        assertEquals(0, history.size());
    }

}

/*"5, SUBTASK, subTaskName5, NEW, subTaskDis, 1, 2023.10.23 15:12, 0" + "\n" +
                   "6, SUBTASK, subTaskName6, NEW, subTaskDis, 1, 2023.10.23 15:13, 0" + "\n" +
                "7, SUBTASK, subTaskName7, NEW, subTaskDis, 1, 2023.10.23 15:14, 0"*/
