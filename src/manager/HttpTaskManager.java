package manager;

import KVServer.KVTaskClient;
import com.google.gson.Gson;

import java.io.IOException;


public class HttpTaskManager extends FileBackedTasksManager {
    Gson gson = new Gson();
    private final KVTaskClient kvTaskClient;

    public HttpTaskManager(String serverUrl) throws IOException {
        super(serverUrl);
        this.kvTaskClient = new KVTaskClient(serverUrl);
    }

    public void save() throws ManagerSaveException {
        try {
            kvTaskClient.put("task", gson.toJson(tasks));
            kvTaskClient.put("epic", gson.toJson(epics));
            kvTaskClient.put("subTask", gson.toJson(subTasks));
            kvTaskClient.put("history", gson.toJson(historyToString(historyManager)));
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка сохранения данных на сервере");
        }
    }

    public void fileReader() {
        try {
            kvTaskClient.load("task");
            kvTaskClient.load("subTask");
            kvTaskClient.load("epic");
            kvTaskClient.load("history");
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка загрузки данных с сервера");
        }
    }
}