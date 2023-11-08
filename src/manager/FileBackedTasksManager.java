package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Types;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    protected final String FIRST_STRING = "id,type,name,status,description,epic\n";
    private final File file;

    public FileBackedTasksManager(String file) {
        super();
        this.file = new File(file);
    }


    public static String historyToString(HistoryManager manager) {
        StringJoiner sj = new StringJoiner(", ");
        for (Task task : manager.getHistory()) {
            sj.add(String.valueOf(task.getId()));
        }
        return sj.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new LinkedList<>();
        String[] s = value.split(", ");
        for (String s1 : s) {
            history.add(Integer.parseInt(s1));
        }
        return history;
    }


//    public void fromString(String string) {
//        String[] split = string.split(", ");
//        switch (Types.valueOf(split[1])) {
//            case SUBTASK:
//                SubTask subTask = new SubTask(split[2], split[4], Integer.parseInt(split[5]));
//                subTask.setType(Types.valueOf(split[1]));
//                subTask.setStatus(split[3]);
//                subTask.setId(Integer.parseInt(split[0]));
//                subTasks.put(subTask.getId(), subTask);
//                break;
//            case EPIC:
//                Epic epic = new Epic(split[2], split[4]);
//                epic.setType(Types.valueOf(split[1]));
//                epic.setStatus(split[3]);
//                epic.setId(Integer.parseInt(split[0]));
//                epics.put(epic.getId(), epic);
//                break;
//            case TASK:
//                Task task = new Task(split[2], split[4]);
//                task.setType(Types.valueOf(split[1]));
//                task.setStatus(split[3]);
//                task.setId(Integer.parseInt(split[0]));
//                tasks.put(task.getId(), task);
//                break;
//        }
//    }

    //---------------------------------------------------------------------------------


    public static FileBackedTasksManager loadFromFile(String file) {
        FileBackedTasksManager read = new FileBackedTasksManager(file);
        read.fileReader();
        return read;
    }

    public Task fromString(String string) {
        String[] split = string.split(", ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm");

        Task task;

        if (Types.valueOf(split[1]).equals(Types.SUBTASK)) {
            task = new SubTask(split[2],
                    split[4],
                    Integer.parseInt(split[5]),
                    LocalDateTime.parse(split[6], formatter),
                    Integer.parseInt(split[7]));
        } else if (Types.valueOf(split[1]).equals(Types.TASK)) {
            task = new Task(split[2],
                    split[4],
                    (split[5].equals("null") ? null : (LocalDateTime.parse(split[5], formatter))),
                    Integer.parseInt(split[6]));
        } else {
            task = new Epic(split[2],
                    split[4],
                    (split[5].equals("null") ? null : (LocalDateTime.parse(split[5], formatter))),
                    Integer.parseInt(split[6]));
        }
        task.setType(Types.valueOf(split[1]));
        task.setStatus(split[3]);
        task.setId(Integer.parseInt(split[0]));
        return task;
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(FIRST_STRING);
            saveTasks(writer, epics);
            saveTasks(writer, tasks);
            saveTasks(writer, subTasks);
            writer.write("\n" + historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }
    }

    protected void saveTasks(BufferedWriter writer, Map<Integer, ? extends Task> taskMap) throws IOException {
        for (Task task : taskMap.values()) {
            writer.write(task.toString() + "\n");
        }
    }

    //TODO нужна ли такая логика в ридере? или подумать над save
    public void fileReader() throws RuntimeException {
        LinkedList<String> tasksFromFile = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String lines = reader.readLine();
                tasksFromFile.add(lines);
            }
            if (tasksFromFile.size() != 2) {
                tasksFromFile.removeFirst();
                if (tasksFromFile.size() > 2) {
                    for (int i = 0; i < tasksFromFile.size() - 2; i++) {
                        Task task = fromString(tasksFromFile.get(i));
                        tasksToMap(task);
                    }
                }
                if (!tasksFromFile.getLast().equals("")) {
                    List<Integer> history = historyFromString(tasksFromFile.getLast());//возврат истории просмотров
                    for (Integer historyId : history) {
                        historyBack(historyId);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }

    }

    private void tasksToMap(Task task) {
        switch (task.getType()) {
            case SUBTASK:
                subTasks.put(task.getId(), (SubTask) task);
                break;
            case EPIC:
                epics.put(task.getId(), (Epic) task);
                break;
            case TASK:
                tasks.put(task.getId(), task);
                break;
        }
    }

    private void historyBack(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
        } else if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
        } else if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
        }
    }

    @Override
    // Метод удаления всех задач для эпика
    public void removeAllTasksForEpic() {
        super.removeAllTasksForEpic();
        save();
    }

    @Override
    // Метод удаления всех задач для обычных задач
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    // Метод удаления всех задач для подзадач
    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    @Override
    public void removeEpicById(int epicIds) {
        super.removeEpicById(epicIds);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeSubtaskId(int subTaskId) {
        super.removeSubtaskId(subTaskId);
        save();
    }

    @Override
    // Метод создания эпика
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    // Метод добавления subTask
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    // Метод добавления задачи
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    // Метод обновления эпика по идентификатору
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    // Метод обновления обычной задачи по идентификатору
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    // Метод обновления подзадачи по идентификатору
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    // Метод получения задачи по идентификатору для эпика
    public Task getTaskByIdForEpic(int epicIds) {
        Epic epic = epics.get(epicIds);
        historyManager.add(epic);
        save();
        return epic;
    }

    @Override
    // Метод получения задачи по идентификатору для обычных задач
    public Task getTaskByIdForTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        save();
        return task;
    }

    @Override
    // Метод получения задачи по идентификатору для подзадач
    public Task getTaskByIdForSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        historyManager.add(subTask);
        save();
        return subTask;
    }
}

