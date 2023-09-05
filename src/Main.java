import manager.InMemoryTaskManager;


public class Main {


    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        inMemoryTaskManager.createEpic("1");
        inMemoryTaskManager.addTaskToEpic(, "1/1");
    }
}