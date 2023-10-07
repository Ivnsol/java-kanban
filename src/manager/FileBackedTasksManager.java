package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Types;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static manager.Managers.loadFromFile;


public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    final String FIRST_STRING = "id,type,name,status,description,epic\n";
    private final File file;

    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }


    public static String historyToString(HistoryManager manager) { //подумать где вызвать метод!
        StringJoiner sj = new StringJoiner(", ");
        for (Task task : manager.getHistory()) {
            sj.add(String.valueOf(task.getId()));
        }
        return sj.toString();
    }

    public static List<Integer> historyFromString(String value) { //подумать где вызвать метод!
        List<Integer> history = new LinkedList<>();
        String[] s = value.split(", ");
        for (String s1 : s) {
            history.add(Integer.parseInt(s1));
        }
        return history;
    }


    public void fromString(String string) {
        String[] split = string.split(", ");
        switch (Types.valueOf(split[1])) {
            case SUBTASK:
                SubTask subTask = new SubTask(split[2], split[4], Integer.parseInt(split[5]));
                subTask.setType(Types.valueOf(split[1]));
                subTask.setStatus(split[3]);
                subTask.setId(Integer.parseInt(split[0]));
                subTasks.put(subTask.getId(), subTask);
                break;
            case EPIC:
                Epic epic = new Epic(split[2], split[4]);
                epic.setType(Types.valueOf(split[1]));
                epic.setStatus(split[3]);
                epic.setId(Integer.parseInt(split[0]));
                epics.put(epic.getId(), epic);
                break;
            case TASK:
                Task task = new Task(split[2], split[4]);
                task.setType(Types.valueOf(split[1]));
                task.setStatus(split[3]);
                task.setId(Integer.parseInt(split[0]));
                tasks.put(task.getId(), task);
                break;
        }
    }
//        if (split.length == 6) {
//            SubTask subTask = new SubTask(split[2], split[4], Integer.parseInt(split[5]));
//            subTask.setType(Types.valueOf(split[1]));
//            subTask.setStatus(split[3]);
//            subTask.setId(Integer.parseInt(split[0]));
//            return subTask;
//        } else if{
//            Task task = new Task(split[2], split[4]);
//            task.setType(Types.valueOf(split[1]));
//            task.setStatus(split[3]);
//            task.setId(Integer.parseInt(split[0]));
//            return task;
//        }


    public void save() throws ManagerSaveException {//метод записи в файл

        try (BufferedWriter writter = new BufferedWriter(new FileWriter(file))) {

            writter.write(FIRST_STRING);

            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }
            for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }

            writter.write("\n" + historyToString(historyManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }
    }

    public void fileReader() throws RuntimeException {
        LinkedList<String> tasksFromFile = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String lines = reader.readLine();
                tasksFromFile.add(lines);
            }
            tasksFromFile.removeFirst();
            for (int i = 0; i < tasksFromFile.size() - 2; i++) {
                fromString(tasksFromFile.get(i));//возврат тасков
//                if (task.getType().equals(TASK)){
//                    tasks.put(task.getId(), task);
//                }else if (task.getType().equals(SUBTASK)) {
//                    tasks.put(task.getId(), task);
//                }else if (task.getType().equals(EPIC)) {
//                    tasks.put(task.getId(), task);
//                }

            }
            List<Integer> hfs = historyFromString(tasksFromFile.getLast());//возврат истории просмотров
            for (Integer hf : hfs) {
                if (tasks.containsKey(hf)) {
                    historyManager.add(tasks.get(hf));
                } else if (subTasks.containsKey(hf)) {
                    historyManager.add(subTasks.get(hf));
                } else if (epics.containsKey(hf)) {
                    historyManager.add(epics.get(hf));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
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
        super.removeTaskById(subTaskId);
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

    //---------------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("Поехали!");

        File file = new File("example.txt");
        System.out.println(loadFromFile(file).getAllSubTask());
        TaskManager manager = Managers.getDefault();

        System.out.println(manager.printHistory());
    }
}

//        Epic epic1 = new Epic("epicName1", "epicDis1");
//        manager.addEpic(epic1);
//
//
//        Epic epic2 = new Epic("epicName2", "epicDis2");
//        manager.addEpic(epic2);
//
//
//        Task task3 = new Task("taskName3", "taskDis3");
//        manager.addTask(task3);
//
//        Task task4 = new Task("taskName4", "taskDis4");
//        manager.addTask(task4);
//
//
//        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1);
//        manager.addSubTask(subTask5);
//        SubTask subTask6 = new SubTask("subTaskName6", "subTaskDis", 1);
//        manager.addSubTask(subTask6);
//        SubTask subTask7 = new SubTask("subTaskName7", "subTaskDis", 1);
//        manager.addSubTask(subTask7);
//
//        manager.getTaskByIdForEpic(epic1.getId());
//        manager.getTaskByIdForEpic(epic2.getId());
//        manager.getTaskByIdForTask(task3.getId());
//        manager.getTaskByIdForTask(task4.getId());
//        manager.getTaskByIdForSubTask(subTask5.getId());
//        manager.getTaskByIdForSubTask(subTask6.getId());
//        manager.getTaskByIdForSubTask(subTask7.getId());

