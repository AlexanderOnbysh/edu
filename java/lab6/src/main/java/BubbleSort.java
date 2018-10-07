import java.util.List;

public class BubbleSort<T extends Comparable<T>> implements SortingStrategy<T> {

    @Override
    public void sort(List<T> container) {
        int n = container.size();
        T temp = null;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (container.get(j - 1).compareTo(container.get(j)) < 0) {
                    temp = container.get(j - 1);
                    container.set(j - 1, container.get(j));
                    container.set(j, temp);
                }
            }
        }
    }
}
