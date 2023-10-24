package manager;

import java.io.File;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        super();
        taskManager = new InMemoryTaskManager(); // Инициализация объекта taskManager
    }
}

