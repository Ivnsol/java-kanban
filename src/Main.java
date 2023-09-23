
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

                    break;
                case 2:
                    System.out.println("Введите название задачи");
                    String taskName = scanner.next();
                    String taskDis = "scanner.next()";
                    Task task = new Task(taskName,taskDis);
                    managers.addTask(task);

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

                    break;
                case 4:
                    System.out.println(managers.getAllEpic());
                    System.out.println("Номер обращения");
                    int epicIds = scanner.nextInt();
                    managers.getTaskByIdForEpic(epicIds);
                    break;
                case 5:
                    System.out.println(managers.getAllTask());
                    System.out.println("Номер обращения");
                    int taskIds = scanner.nextInt();
                    managers.getTaskByIdForTask(taskIds);
                    break;
                case 6:
                    System.out.println(managers.getAllSubTask());
                    System.out.println("Номер обращения");
                    int subTaskIds = scanner.nextInt();
                    managers.getTaskByIdForTask(subTaskIds);
                    break;
                case 7:
                    managers.printEpicTask();
                    System.out.println("Номер удаления");
                    int epicRem = scanner.nextInt();
                    managers.removeEpicById(epicRem);
                    break;
                case 8:
                    System.out.println(managers.getAllTask());
                    System.out.println("Номер удаления");
                    int taskRem = scanner.nextInt();
                    managers.removeTaskById(taskRem);
                    break;
                case 9:
                    System.out.println(managers.getAllSubTask());
                    System.out.println("Номер удаления");
                    int subTaskRem = scanner.nextInt();
                    managers.removeSubtaskId(subTaskRem);
                    break;
                case 10:
                    System.out.println(managers.printHistory());
                    break;
                case 11:
                    return;

            }
        }
    }

    private static void printMenu() {

        System.out.println(
                "1 - Ввести название Эпика,\n" +
                "2 - Ввести название Обычная задача,\n" +
                "3 - Ввести название Подзадача,\n" +
                "4 - Обратиться к Эпику по id\n" +
                "5 - Обратиться к Задаче по id\n" +
                "6 - Обратиться к Подзадаче по id\n" +
                "7 - Удалить Эпик по id\n" +
                "8 - Удалить Задачу по id\n" +
                "9 - Удалить Подзадачу по id\n" +
                "10 - Показать историю,\n" +
                "11 - Выход");
    }
}