import java.io.IOException;
import java.time.LocalDateTime;

import KVServer.*;
import manager.HttpTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Task;

public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();

        TaskManager manager = Managers.getDefault();
        Task task3 = new Task("taskName3", "taskDis3", LocalDateTime.of(2023, 10, 23, 15, 10), 0);

        Epic epic1 = new Epic("epicName1", "epicDis1",null,0);

        Epic epic2 = new Epic("epicName2", "epicDis2",null,0);

        Epic epic3 = new Epic("epicName3", "epicDis3", null, 0);

        manager.addTask(task3);
//        kvTaskClient.put("key2", "value2");
//        kvTaskClient.put("key3", "value3");
//
//        String value1 = kvTaskClient.load("key1");
//        String value2 = kvTaskClient.load("key2");
//        String value3 = kvTaskClient.load("key3");
//
//        System.out.println("Value 1: " + value1);
//        System.out.println("Value 2: " + value2);
//        System.out.println("Value 3: " + value3);
    }
}