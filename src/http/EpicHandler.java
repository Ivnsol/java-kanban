package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static http.HttpTaskServer.*;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class EpicHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
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
                    gson.toJson(taskManager.getTaskByIdForEpic(id)),
                    200);
        } else {
            writeResponse(exchange,
                    gson.toJson(taskManager.getAllEpic()),
                    200);
        }
    }

    private void deleteTasks(HttpExchange exchange) throws IOException {
        Optional<String> path = Optional.ofNullable(exchange.getRequestURI().getQuery());//разбиваем URI
        if (path.isPresent()) {
            int id = Integer.parseInt(path.get().split("=")[1]);
            taskManager.removeEpicById(id);
            writeResponse(exchange, "Удаление по id прошло успешно",200);
        } else {
            taskManager.removeAllTasksForEpic();
            writeResponse(exchange, "Удаление всех задач прошло успешно",200);
        }
    }

    private void readResponse(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(body, Epic.class);
        boolean coincidence = taskManager.getAllEpic().stream()
                .map(Epic::getId)
                .anyMatch(n -> n == epic.getId());
        if (coincidence) {
            taskManager.updateEpic(epic);
            writeResponse(exchange, "Обновление успешно",200);
        } else {
            taskManager.addEpic(epic);
            writeResponse(exchange, "Добавление успешно",200);
        }
    }
}
