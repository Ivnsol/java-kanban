package test;

import KVServer.KVServer;
import manager.HttpTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.time.LocalDateTime;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
KVServer kvServer;
    {
        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @BeforeEach
    public void init() {
        kvServer.start();
        taskManager = (HttpTaskManager) Managers.getDefault();
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
    public void stop() {
        kvServer.stop();
    }
}