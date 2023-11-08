package http;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static http.HttpTaskServer.*;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

 class TasksHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();//находим метод обращения
        switch (method) {
            case "POST":
                readResponse(exchange);
                break;
            case "GET":
                getTasks(exchange);
                break;
            case "DELETE":
                deleteTasks(exchange);
                break;
            default:
                break;
        }
    }

    private void getTasks(HttpExchange exchange) throws IOException {
        Optional<String> path = Optional.ofNullable(exchange.getRequestURI().getQuery());//разбиваем URI
        if (path.isPresent()) {
            int id = Integer.parseInt(path.get().split("=")[1]);
            writeResponse(exchange,
                    gson.toJson(taskManager.getTaskByIdForTask(id)),
                    200);
        } else {
            writeResponse(exchange,
                    gson.toJson(taskManager.getAllTask()),
                    200);
        }
    }

    private void deleteTasks(HttpExchange exchange) throws IOException {
        Optional<String> path = Optional.ofNullable(exchange.getRequestURI().getQuery());//разбиваем URI
        if (path.isPresent()) {
            int id = Integer.parseInt(path.get().split("=")[1]);
            taskManager.removeTaskById(id);
            writeResponse(exchange, "Удаление по id прошло успешно",200);
        } else {
            taskManager.removeAllTask();
            writeResponse(exchange, "Удаление всех задач прошло успешно",200);
        }
    }

    private void readResponse(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(body, Task.class);
        boolean coincidence = taskManager.getAllTask().stream()
                .map(Task::getId)
                .anyMatch(n ->n == task.getId());
        if (coincidence) {
            taskManager.updateTask(task);
            writeResponse(exchange, "Обновление успешно",200);
        } else {
            taskManager.addTask(task);
            writeResponse(exchange, "Добавление успешно",200);
        }
    }
}
