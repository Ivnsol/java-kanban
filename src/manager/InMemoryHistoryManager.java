package manager;

import model.Task;
import Node.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedLists his = new CustomLinkedLists();

    //private static final int HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        his.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (his.nodeHistory.containsKey(id)) {
            his.removeNode(his.nodeHistory.get(id));
            his.nodeHistory.remove(id);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return his.getTasks();
    }


}

  class CustomLinkedLists {
      protected Node last;
      protected Node first;

    protected CustomLinkedLists() {
        last = null;
        first = null;
    }
      HashMap<Integer, Node> nodeHistory = new HashMap<>();
      protected void removeNode(final Node node) {//(предыдущий элемент, task, след элемент)
            Node prev = node.prev; // предыдущий элемент = прев.
            Node next = node.next; // след элеменет = след.

          if (prev == null) {
              first = next;
          } else {
              prev.next = next;
          }

          if (next == null) {
              last = prev;
          } else {
              next.prev = prev;
          }
      }

      protected void linkLast(Task task) {
          Node l = last;
          final Node newNode = new Node(l, task, null);
          if (nodeHistory.containsKey(task.getId())) {
              removeNode(nodeHistory.get(task.getId()));
          }
              last = newNode;
              if (l == null) {
                  first = newNode;
              } else {
                  l.next = newNode;
                  newNode.prev = l;
              }
              nodeHistory.put(task.getId(), newNode);
      }



      protected ArrayList<Task> getTasks() {
          ArrayList<Task> tasks = new ArrayList<>();
          Node node = first;
          while (node != null) {
              tasks.add(node.item);
              node = node.next;
          }
          return tasks;
   }

  }