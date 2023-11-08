package test;

import KVServer.KVServer;
import manager.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class HttpTaskManagerTest extends FileBackedTasksManagerTest {

    private KVServer kvServer; // Добавьте поле для хранения экземпляра сервера

    @BeforeEach
    public void setUp() throws IOException {
        kvServer = new KVServer(); // Создайте экземпляр сервера
        kvServer.start(); // Запустите сервер
        Managers.getDefault(); // Инициализируйте менеджеры
    }

    @AfterEach
    public void stop() throws IOException {
        if (kvServer != null) {
            kvServer.stop(); // Остановите сервер, если он был создан
        }
    }
}
