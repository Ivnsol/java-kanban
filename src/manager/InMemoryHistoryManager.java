package manager;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedLists history = new CustomLinkedLists();


    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (history.nodeHistory.containsKey(id)) {
            history.removeNode(history.nodeHistory.get(id));
            history.nodeHistory.remove(id);
        } else {
            System.out.println("Такой задачи нет в списке"); //проверка на ошибку
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }


    protected static class CustomLinkedLists {
        protected Node last;
        protected Node first;
        protected HashMap<Integer, Node> nodeHistory = new HashMap<>();

        protected CustomLinkedLists() {
            last = null;
            first = null;
        }

        protected void removeNode(final Node node) {
            Node prev = node.getPrev();
            Node next = node.getNext();

            if (prev == null) {
                first = next;
            } else {
                prev.setNext(next);
            }

            if (next == null) {
                last = prev;
            } else {
                next.setPrev(prev);
            }
        }

        protected void linkLast(Task task) {
            if (nodeHistory.containsKey(task.getId())) {
                removeNode(nodeHistory.get(task.getId()));
            }
            Node l = last;
            final Node newNode = new Node(l, task, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.setNext(newNode);
                newNode.setPrev(l);
            }
            nodeHistory.put(task.getId(), newNode);

        }

        protected List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            Node node = first;
            while (node != null) {
                tasks.add(node.getItem());
                node = node.getNext();
            }
            return tasks;
        }

        public static class Node {
            private Task item;
            private Node next;
            private Node prev;

            public Node(Node prev, Task element, Node next) {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }

            public Task getItem() {
                return item;
            }

            public void setItem(Task item) {
                this.item = item;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

            public Node getPrev() {
                return prev;
            }

            public void setPrev(Node prev) {
                this.prev = prev;
            }
        }
    }
}
