import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.File;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = Managers.getDefault();


        Epic epic1 = new Epic("epicName1", "epicDis1");
        manager.addEpic(epic1);


        Epic epic2 = new Epic("epicName2", "epicDis2");
        manager.addEpic(epic2);


        Task task3 = new Task("taskName3", "taskDis3");
        manager.addTask(task3);

        Task task4 = new Task("taskName4", "taskDis4");
        manager.addTask(task4);


        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDis", 1);
        manager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName6", "subTaskDis", 1);
        manager.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("subTaskName7", "subTaskDis", 1);
        manager.addSubTask(subTask7);



//            manager.getTaskByIdForEpic(epic1.getId());
//            manager.getTaskByIdForEpic(epic2.getId());
//            manager.getTaskByIdForTask(task3.getId());
//            manager.getTaskByIdForTask(task4.getId());
//            manager.getTaskByIdForSubTask(subTask5.getId());
//            manager.getTaskByIdForSubTask(subTask6.getId());
//            manager.getTaskByIdForSubTask(subTask7.getId());
//
//            System.out.println(manager.printHistory());
//            System.out.println("Должен вывести историю id 1-7");
//            manager.removeAllSubTask();
//            manager.removeAllTask();
//            manager.removeAllTasksForEpic();


//----------------------------------------------------------------
//        manager.getTaskByIdForEpic(epic1.getId());
//        manager.getTaskByIdForEpic(epic2.getId());
//        manager.getTaskByIdForTask(task3.getId());
//        manager.getTaskByIdForTask(task4.getId());
//        manager.getTaskByIdForSubTask(subTask5.getId());
//        manager.getTaskByIdForSubTask(subTask6.getId());
//        manager.getTaskByIdForSubTask(subTask7.getId());
//        manager.removeEpicById(epic1.getId());
//        manager.removeEpicById(epic2.getId());
//
//        System.out.println(manager.printHistory());
//        System.out.println("Должен вывести историю id 3,4");
//        manager.removeAllSubTask();
//        manager.removeAllTask();
//        manager.removeAllTasksForEpic();
//------------------------------------------------------------------------------
//        manager.getTaskByIdForEpic(epic1.getId());
//        manager.getTaskByIdForEpic(epic2.getId());
//        manager.getTaskByIdForTask(task3.getId());
//        manager.getTaskByIdForTask(task4.getId());
//        manager.getTaskByIdForSubTask(subTask5.getId());
//        manager.getTaskByIdForSubTask(subTask6.getId());
//        manager.getTaskByIdForSubTask(subTask7.getId());
//        manager.removeTaskById(task3.getId());
//        manager.removeTaskById(task4.getId());
//
//        System.out.println(manager.printHistory());
//        System.out.println("Должен вывести историю без id 3,4");
//        manager.removeAllSubTask();
//        manager.removeAllTask();
//        manager.removeAllTasksForEpic();
////----------------------------------------------------------------
        manager.getTaskByIdForEpic(epic1.getId());
        manager.getTaskByIdForEpic(epic2.getId());
        manager.getTaskByIdForTask(task3.getId());
        manager.getTaskByIdForTask(task4.getId());
        manager.getTaskByIdForSubTask(subTask5.getId());
        manager.getTaskByIdForSubTask(subTask6.getId());
        manager.getTaskByIdForSubTask(subTask7.getId());
        System.out.println(manager.printHistory());
        manager.getTaskByIdForSubTask(subTask7.getId());
        manager.getTaskByIdForSubTask(subTask6.getId());
        manager.getTaskByIdForSubTask(subTask5.getId());
        manager.getTaskByIdForTask(task4.getId());
        manager.getTaskByIdForTask(task3.getId());
        manager.getTaskByIdForEpic(epic2.getId());
        manager.getTaskByIdForEpic(epic1.getId());

        System.out.println(manager.printHistory());
        System.out.println("Должен вывести историю id 7-1");
        manager.removeAllSubTask();
        manager.removeAllTask();
        manager.removeAllTasksForEpic();
    }
}


