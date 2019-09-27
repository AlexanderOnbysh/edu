import java.util.Arrays;

public class Main {

    public static void main(String args[]) {

        Box<Integer> box = new Box<>();
        box.addAll(Arrays.asList(3, 1, 5, 4, 8, 12, 15, 0, -1));

        System.out.println("Before sorting:");
        box.display();

        System.out.println("Bubble sort:");
        box.sort(new BubbleSort<>());
        box.display();

        System.out.println("Add elements to list:");
        box.addAll(Arrays.asList(7, 13, 42));
        box.display();

        System.out.println("Insertion sort:");
        box.sort(new InsertionSort<>());
        box.display();

        System.out.println("\nString box");
        Box<String> string_box = new Box<>();
        string_box.addAll(Arrays.asList("one", "two", "three", "four", "five"));

        System.out.println("Before sorting:");
        string_box.display();

        System.out.println("After sorting");
        string_box.sort(new BubbleSort<>());
        string_box.display();
    }
}

