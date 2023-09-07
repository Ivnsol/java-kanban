import manager.InMemoryTaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        System.out.println("Поехали!");
        Scanner scanner = new Scanner(System.in);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Введите название эпика");
                    String epicName = scanner.next();
                    System.out.println("Введите описание эпика");
                    String epicDis = scanner.next();
                    Epic epic = new Epic(epicName,epicDis);
                    inMemoryTaskManager.addEpic(epic);
                    inMemoryTaskManager.getTaskByIdForEpic(epic.getId());
                    break;

                case 2:
                    System.out.println("Введите название задачи");
                    String taskName = scanner.next();
                    System.out.println("Введите описание задачи");
                    String taskDis = scanner.next();
                    Task task = new Task(taskName,taskDis);
                    inMemoryTaskManager.addTask(task);
                    inMemoryTaskManager.getTaskByIdForTask(task.getId());
                    break;

                case 3:
                    System.out.println("Введите номер эпика для добавления подзадачи");
                    inMemoryTaskManager.printEpicTask();
                    int epicId = scanner.nextInt();
                    System.out.println("Введите название подзадачи");
                    String subTaskName = scanner.next();
                    System.out.println("Введите описание подзадачи");
                    String subTaskDis = scanner.next();
                    SubTask subTask = new SubTask(subTaskName,subTaskDis,epicId);
                    inMemoryTaskManager.addSubTask(subTask);
                    inMemoryTaskManager.getTaskByIdForSubTask(subTask.getId());
                    break;

                case 4:
                    inMemoryTaskManager.getHistory();
                case 5:
                    return;
            }

        }


    }

    private static void printMenu() {
        System.out.println("Ввести название: " +
                "1 - Эпика, " +
                "2 - Обычная задача, " +
                "3 - Подзадача, " +
                "4 - Показать историю, " +
                "5 - Выход");
    }
}