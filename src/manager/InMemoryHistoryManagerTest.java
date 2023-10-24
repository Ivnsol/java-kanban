package manager;

import model.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest{

    private InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @BeforeEach
    void init(){
        Epic epic1 = new Epic("epicName1", "epicDis1",null,0);
        epic1.setId(1);
        manager.add(epic1);
        Epic epic2 = new Epic("epicName2", "epicDis2",null,0);
        epic2.setId(2);
        manager.add(epic2);

        Epic epic3 = new Epic("epicName3", "epicDis3", null, 0);
        epic3.setId(3);
        manager.add(epic3);

    }

    @Test
    void addSecondOneTest() {
        Epic epic4 = new Epic("epicName4", "epicDis4", null, 0);
        epic4.setId(4);
        manager.add(epic4);
        manager.add(epic4);

        Epic epic5 = new Epic("epicName5", "epicDis5", null, 0);
        epic5.setId(5);
        manager.add(epic5);
        manager.add(epic5);

        assertEquals(5, manager.getHistory().size());
    }

    @Test
    void removeFromStartMiddleEndTest() {
        Epic epic4 = new Epic("epicName4", "epicDis4", null, 0);
        epic4.setId(4);
        manager.add(epic4);


        Epic epic5 = new Epic("epicName5", "epicDis5", null, 0);
        epic5.setId(5);
        manager.add(epic5);

        manager.remove(5);
        manager.remove(3);
        manager.remove(1);

        assertEquals(2, manager.getHistory().size());
    }

    @Test
    void zeroHistoryTest() {
        manager.remove(1);
        manager.remove(2);
        manager.remove(3);

        assertEquals(0, manager.getHistory().size());
    }
}