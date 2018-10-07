import java.util.LinkedList;
import java.util.List;


public class Box<T extends Comparable<T>> {
    private List<T> list = new LinkedList<>();

    Box(){};

    void add(T value) {
        list.add(value);
    }

    void addAll(List<T> values) {
        list.addAll(values);
    }

    void display() {
        for (T aList : list) {
            System.out.print(aList.toString() + " ");
        }
        System.out.println();
    }

    void sort(SortingStrategy<T> strategy) {
        strategy.sort(list);
    }
}
