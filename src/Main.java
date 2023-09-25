import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Scanner scanner = new Scanner(System.in);
        TaskManager managers = Managers.getDefault();


        Epic epic1 = new Epic("epicName1", "epicDis1");
        managers.addEpic(epic1);


        Epic epic2 = new Epic("epicName2", "epicDis2");
        managers.addEpic(epic2);


        Task task3 = new Task("taskName3", "taskDis3");
        managers.addTask(task3);

        Task task4 = new Task("taskName4", "taskDis4");
        managers.addTask(task4);


        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1);
        managers.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName6", "subTaskDis", 1);
        managers.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("subTaskName7", "subTaskDis", 1);
        managers.addSubTask(subTask7);

        System.out.println(managers.getAllEpic());
        System.out.println(managers.getAllTask());
        System.out.println(managers.getAllSubTask());

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println(managers.getAllEpic());
                    System.out.println("Номер обращения");
                    int epicIds = scanner.nextInt();
                    managers.getTaskByIdForEpic(epicIds);
                    break;
                case 2:
                    System.out.println(managers.getAllTask());
                    System.out.println("Номер обращения");
                    int taskIds = scanner.nextInt();
                    managers.getTaskByIdForTask(taskIds);
                    break;
                case 3:
                    System.out.println(managers.getAllSubTask());
                    System.out.println("Номер обращения");
                    int subTaskIds = scanner.nextInt();
                    managers.getTaskByIdForSubTask(subTaskIds);
                    break;
                case 4:
                    managers.printEpicTask();
                    System.out.println("Номер удаления");
                    int epicRem = scanner.nextInt();
                    managers.removeEpicById(epicRem);
                    break;
                case 5:
                    System.out.println(managers.getAllTask());
                    System.out.println("Номер удаления");
                    int taskRem = scanner.nextInt();
                    managers.removeTaskById(taskRem);
                    break;
                case 6:
                    System.out.println(managers.getAllSubTask());
                    System.out.println("Номер удаления");
                    int subTaskRem = scanner.nextInt();
                    managers.removeSubtaskId(subTaskRem);
                    break;
                case 7:
                    System.out.println(managers.printHistory());
                    break;
                case 8:
                    return;
            }

        }
    }


        private static void printMenu () {

            System.out.println(
                            "1 - Обратиться к Эпику по id\n" +
                            "2 - Обратиться к Задаче по id\n" +
                            "3 - Обратиться к Подзадаче по id\n" +
                            "4 - Удалить Эпик по id\n" +
                            "5 - Удалить Задачу по id\n" +
                            "6 - Удалить Подзадачу по id\n" +
                            "7 - Показать историю,\n" +
                            "8 - Выход");
        }
    }
