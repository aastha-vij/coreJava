package coreJava;

import java.util.Arrays;

/**
 * _006_SortArray - Various Array Sorting Algorithms and Techniques
 * 
 * This class demonstrates different sorting algorithms:
 * - Bubble Sort (original implementation)
 * - Selection Sort
 * - Insertion Sort
 * - Quick Sort (recursive)
 * - Merge Sort (recursive)
 * - Built-in Java sorting
 * - Performance comparison
 * 
 * Key Learning Points:
 * 1. Different algorithms have different time complexities
 * 2. Bubble Sort is O(n²) - not efficient for large arrays
 * 3. Quick Sort and Merge Sort are O(n log n) - much more efficient
 * 4. Built-in Java sorting uses optimized algorithms
 * 5. Choose algorithm based on data size and requirements
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _006_SortArray {

    /**
     * Original Bubble Sort implementation
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * 
     * @param arr Array to sort
     */
    public static void sortArray(int[] arr) {
        if (arr == null || arr.length <= 1) {
            System.out.println("Array is already sorted or empty");
            return;
        }
        
        System.out.println("=== Bubble Sort Implementation ===");
        System.out.println("Original array: " + Arrays.toString(arr));
        
        int temp = 0;
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                comparisons++;
                if (arr[i] > arr[j]) {
                    // Swap elements
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    swaps++;
                }
            }
        }
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println();
    }
    
    /**
     * Selection Sort implementation
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * Fewer swaps than Bubble Sort
     * 
     * @param arr Array to sort
     */
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        System.out.println("=== Selection Sort Implementation ===");
        System.out.println("Original array: " + Arrays.toString(arr));
        
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            
            // Find minimum element in unsorted portion
            for (int j = i + 1; j < arr.length; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap if minimum is not at current position
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
            }
        }
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println();
    }
    
    /**
     * Insertion Sort implementation
     * Time Complexity: O(n²) but better for small arrays
     * Space Complexity: O(1)
     * Good for nearly sorted arrays
     * 
     * @param arr Array to sort
     */
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        System.out.println("=== Insertion Sort Implementation ===");
        System.out.println("Original array: " + Arrays.toString(arr));
        
        int comparisons = 0;
        int shifts = 0;
        
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Move elements greater than key to one position ahead
            while (j >= 0 && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
                shifts++;
            }
            arr[j + 1] = key;
        }
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Shifts: " + shifts);
        System.out.println();
    }
    
    /**
     * Quick Sort implementation (recursive)
     * Time Complexity: O(n log n) average, O(n²) worst case
     * Space Complexity: O(log n) due to recursion
     * 
     * @param arr Array to sort
     * @param low Starting index
     * @param high Ending index
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    /**
     * Partition method for Quick Sort
     * 
     * @param arr Array to partition
     * @param low Starting index
     * @param high Ending index
     * @return Pivot index
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // Swap elements
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Place pivot in correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    /**
     * Merge Sort implementation (recursive)
     * Time Complexity: O(n log n) guaranteed
     * Space Complexity: O(n)
     * Stable sort algorithm
     * 
     * @param arr Array to sort
     * @param left Starting index
     * @param right Ending index
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    /**
     * Merge method for Merge Sort
     * 
     * @param arr Array to merge
     * @param left Starting index
     * @param mid Middle index
     * @param right Ending index
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
        
        // Merge the temporary arrays
        int i = 0, j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * Demonstrates built-in Java sorting
     * Uses optimized algorithms (Timsort - hybrid of merge sort and insertion sort)
     * 
     * @param arr Array to sort
     */
    public static void javaBuiltInSort(int[] arr) {
        System.out.println("=== Java Built-in Sort ===");
        System.out.println("Original array: " + Arrays.toString(arr));
        
        long startTime = System.nanoTime();
        Arrays.sort(arr);
        long endTime = System.nanoTime();
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Time taken: " + (endTime - startTime) + " nanoseconds");
        System.out.println();
    }
    
    /**
     * Performance comparison of different sorting algorithms
     * 
     * @param arr Array to test
     */
    public static void performanceComparison(int[] arr) {
        System.out.println("=== Performance Comparison ===");
        
        // Test Bubble Sort
        int[] arr1 = arr.clone();
        long startTime = System.nanoTime();
        sortArray(arr1);
        long bubbleTime = System.nanoTime() - startTime;
        
        // Test Selection Sort
        int[] arr2 = arr.clone();
        startTime = System.nanoTime();
        selectionSort(arr2);
        long selectionTime = System.nanoTime() - startTime;
        
        // Test Insertion Sort
        int[] arr3 = arr.clone();
        startTime = System.nanoTime();
        insertionSort(arr3);
        long insertionTime = System.nanoTime() - startTime;
        
        // Test Quick Sort
        int[] arr4 = arr.clone();
        startTime = System.nanoTime();
        quickSort(arr4, 0, arr4.length - 1);
        long quickTime = System.nanoTime() - startTime;
        
        // Test Merge Sort
        int[] arr5 = arr.clone();
        startTime = System.nanoTime();
        mergeSort(arr5, 0, arr5.length - 1);
        long mergeTime = System.nanoTime() - startTime;
        
        // Test Java Built-in Sort
        int[] arr6 = arr.clone();
        startTime = System.nanoTime();
        Arrays.sort(arr6);
        long javaTime = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (nanoseconds):");
        System.out.println("Bubble Sort: " + bubbleTime);
        System.out.println("Selection Sort: " + selectionTime);
        System.out.println("Insertion Sort: " + insertionTime);
        System.out.println("Quick Sort: " + quickTime);
        System.out.println("Merge Sort: " + mergeTime);
        System.out.println("Java Built-in: " + javaTime);
        System.out.println();
    }
    
    /**
     * Main method demonstrating all sorting algorithms
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== Array Sorting Algorithms ===\n");
        
        int[] array = {5, 7, 33, 6, 8, 1, -7};
        
        System.out.println("Testing with array: " + Arrays.toString(array));
        System.out.println();
        
        // Demonstrate different sorting algorithms
        sortArray(array.clone());
        selectionSort(array.clone());
        insertionSort(array.clone());
        
        // Test advanced algorithms
        int[] quickArray = array.clone();
        System.out.println("=== Quick Sort Implementation ===");
        System.out.println("Original array: " + Arrays.toString(quickArray));
        quickSort(quickArray, 0, quickArray.length - 1);
        System.out.println("Sorted array: " + Arrays.toString(quickArray));
        System.out.println();
        
        int[] mergeArray = array.clone();
        System.out.println("=== Merge Sort Implementation ===");
        System.out.println("Original array: " + Arrays.toString(mergeArray));
        mergeSort(mergeArray, 0, mergeArray.length - 1);
        System.out.println("Sorted array: " + Arrays.toString(mergeArray));
        System.out.println();
        
        // Test Java built-in sorting
        javaBuiltInSort(array.clone());
        
        // Performance comparison
        performanceComparison(array);
        
        System.out.println("=== Algorithm Summary ===");
        System.out.println("1. Bubble Sort: O(n²) - Simple but inefficient");
        System.out.println("2. Selection Sort: O(n²) - Fewer swaps than bubble");
        System.out.println("3. Insertion Sort: O(n²) - Good for small/nearly sorted arrays");
        System.out.println("4. Quick Sort: O(n log n) average - Good general-purpose sort");
        System.out.println("5. Merge Sort: O(n log n) guaranteed - Stable sort");
        System.out.println("6. Java Built-in: Optimized hybrid algorithm");
    }
}
