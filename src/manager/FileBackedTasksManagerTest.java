package manager;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    public FileBackedTasksManagerTest() {
        super();
        taskManager = new FileBackedTasksManager(new File("example.txt")); // Инициализация объекта taskManager
    }

    @Test
    void saveStandartWorkTest() {
        taskManager.getTaskByIdForTask(3);
        taskManager.getTaskByIdForTask(4);
        taskManager.save();
        taskManager.fileReader();

        assertEquals(2, taskManager.getAllTask().size());
        assertEquals(3, taskManager.getAllSubTask().size());
        assertEquals(2, taskManager.getAllEpic().size());
        assertEquals(2, taskManager.printHistory().size());
    }

    @Test
    void saveWithoutTasksTest() throws IOException {
        taskManager.removeAllTask();
        taskManager.removeAllSubTask();
        taskManager.removeAllTasksForEpic();
        taskManager.historyManager.getHistory().clear();

        taskManager.save();
        taskManager.fileReader();
        LinkedList<String> tasksFromFile = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader("example.txt"));
        while (reader.ready()) {
            String lines = reader.readLine();
            tasksFromFile.add(lines);
        }
        assertEquals(2, tasksFromFile.size());
    }

    @Test
    void saveWithoutSubtasksTest() {
        taskManager.removeAllSubTask();
        taskManager.save();
        taskManager.fileReader();

        assertEquals(2, taskManager.getAllTask().size());
        assertEquals(0, taskManager.getAllSubTask().size());
        assertEquals(2, taskManager.getAllEpic().size());
        assertEquals(2, taskManager.printHistory().size());
    }
}