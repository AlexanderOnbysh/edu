import java.util.List;

public class InsertionSort<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(List<T> container) {
        int j, n = container.size();
        T key = null;
        for (int i = 1; i < n; i++) {
            key = container.get(i);
            j = i - 1;

            while (j >= 0 && container.get(j).compareTo(key) < 0) {
                container.set(j + 1, container.get(j));
                j = j - 1;
            }
            container.set(j + 1, key);
        }
    }
}
