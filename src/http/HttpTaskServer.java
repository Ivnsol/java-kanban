package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Optional;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {
    static Gson gson = new Gson();
    protected static TaskManager taskManager = Managers.getDefault();
    private static final int PORT = 8080;

    protected static void writeResponse(HttpExchange exchange,
                                        String responseString,
                                        int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }


    public static void main(String[] args) throws IOException {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server.createContext("/tasks/task", new TasksHandler());
            server.createContext("/tasks/subtask", new SubTaskHandler());
            server.createContext("/tasks/epic", new EpicHandler());
            server.createContext("/tasks/subtask/epic", new EpicSubtasks());
            server.createContext("/tasks/history", new HistoryTasksHandler());
            server.createContext("/tasks", new PriorityTasksHandler());

            server.start();

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Сервер не запускается");
        }
    }

    static class EpicSubtasks implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getQuery();//разбиваем URI
            int id = Integer.parseInt(path.split("=")[1]);
            writeResponse(exchange,gson.toJson(taskManager.getTasksForEpic(id)), 200);
        }
    }
    static class HistoryTasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            writeResponse(exchange,gson.toJson(taskManager.printHistory()), 200);
        }
    }
    static class PriorityTasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            writeResponse(exchange,gson.toJson(taskManager.getPrioritizedTasks()), 200);
        }
    }
}