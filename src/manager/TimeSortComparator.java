package manager;

import model.Task;

import java.time.LocalDateTime;
import java.util.Comparator;


public class TimeSortComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        LocalDateTime dateTime1 = o1.getStartTime();
        LocalDateTime dateTime2 = o2.getStartTime();

        if (dateTime1 == null && dateTime2 == null) {
            // Если оба элемента равны по dateTime, сравниваем по title
            return o1.getTitle().compareTo(o2.getTitle());
        } else if (dateTime1 == null) {
            // Элемент с null идет в конце
            return 1;
        } else if (dateTime2 == null) {
            // Элемент с null идет в конце
            return -1;
        } else {
            // Сравниваем по умолчанию по dateTime
            int dateTimeComparison = dateTime1.compareTo(dateTime2);
            if (dateTimeComparison == 0) {
                // Если dateTime одинаковы, сравниваем по title
                return o1.getTitle().compareTo(o2.getTitle());
            }
            return dateTimeComparison;
        }
    }
}
