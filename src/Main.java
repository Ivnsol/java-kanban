
import manager.*;
import model.Epic;

import model.SubTask;

import model.Task;



import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Scanner scanner = new Scanner(System.in);
        TaskManager managers = Managers.getDefault();


        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Введите название эпика");
                    String epicName = scanner.next();
                    String epicDis = "scanner.next()";
                    Epic epic = new Epic(epicName,epicDis);
                    managers.addEpic(epic);
                    managers.getTaskByIdForEpic(epic.getId());
                    break;
                case 2:
                    System.out.println("Введите название задачи");
                    String taskName = scanner.next();
                    String taskDis = "scanner.next()";
                    Task task = new Task(taskName,taskDis);
                    managers.addTask(task);
                    managers.getTaskByIdForTask(task.getId());
                    break;
                case 3:
                    System.out.println("Введите номер эпика для добавления подзадачи");
                    managers.printEpicTask();
                    int epicId = scanner.nextInt();
                    System.out.println("Введите название подзадачи");
                    String subTaskName = scanner.next();
                    String subTaskDis = "scanner.next()";
                    SubTask subTask = new SubTask(subTaskName,subTaskDis,epicId);
                    managers.addSubTask(subTask);
                    managers.getTaskByIdForSubTask(subTask.getId());
                    break;
                case 4:
                    System.out.println(managers.printHistory());
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