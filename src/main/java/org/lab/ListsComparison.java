/**
 * ListsComparison class compares the performance of ArrayList and LinkedList
 * for three basic operations: add, get, and remove.
 */
package org.lab;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The ListsComparison class provides methods to compare the performance of
 * ArrayList and LinkedList for add, get, and remove operations.
 */
public class ListsComparison {

    /**
     * Starts the performance testing for the specified number of operations.
     *
     * @param numOperations The number of operations to be performed during testing.
     */
    public static void start(int numOperations) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Testing for ArrayList
        Thread arrayListThread = new Thread(() -> {
            synchronized (System.out) {
                Long addDuration = measureTime(() -> testAdd(arrayList, numOperations));
                Long getDuration = measureTime(() -> testGet(arrayList, numOperations));
                Long removeDuration = measureTime(() -> testRemove(arrayList, numOperations));
                System.out.println("ArrayList:");
                System.out.println("\t\tIterations \tDuration (ms)");
                printRow("add", numOperations, addDuration);
                printRow("get", numOperations, getDuration);
                printRow("remove", numOperations, removeDuration);
                System.out.println();
            }
        });

        // Testing for LinkedList
        Thread linkedListThread = new Thread(() -> {
            synchronized (System.out) {
                Long addDuration = measureTime(() -> testAdd(linkedList, numOperations));
                Long getDuration = measureTime(() -> testGet(linkedList, numOperations));
                Long removeDuration = measureTime(() -> testRemove(linkedList, numOperations));
                System.out.println("LinkedList:");
                System.out.println("\t\tIterations \tDuration (ms)");
                printRow("add", numOperations, addDuration);
                printRow("get", numOperations, getDuration);
                printRow("remove", numOperations, removeDuration);
                System.out.println();
            }
        });

        // Start threads
        arrayListThread.start();
        linkedListThread.start();

        // Wait for threads to finish
        try {
            arrayListThread.join();
            linkedListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a row with operation, iterations, and duration information.
     *
     * @param operation    The name of the operation.
     * @param iterations   The number of iterations.
     * @param duration     The duration of the operation.
     */
    private static void printRow(String operation, int iterations, long duration) {
        System.out.printf("%-10s%-15s%-10s%n", operation + ":", iterations, duration);
    }

    /**
     * Performs add operations on the given list for the specified number of iterations.
     *
     * @param list          The list to perform add operations on.
     * @param numOperations The number of add operations to perform.
     */
    private static void testAdd(List<Integer> list, int numOperations) {
        for (int i = 0; i < numOperations; i++) {
            list.add(i);
        }
    }

    /**
     * Performs remove operations on the given list for the specified number of iterations.
     *
     * @param list          The list to perform remove operations on.
     * @param numOperations The number of remove operations to perform.
     */
    private static void testRemove(List<Integer> list, int numOperations) {
        for (int i = numOperations - 1; i >= 0; i--) {
            list.remove(i);
        }
    }

    /**
     * Performs get operations on the given list for the specified number of iterations.
     *
     * @param list          The list to perform get operations on.
     * @param numOperations The number of get operations to perform.
     */
    private static void testGet(List<Integer> list, int numOperations) {
        for (int i = 0; i < numOperations; i++) {
            list.get(i);
        }
    }

    /**
     * Measures the time taken to execute the specified task.
     *
     * @param task The task to be measured.
     * @return The duration of the task in milliseconds.
     */
    private static Long measureTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        return duration;
    }
}
