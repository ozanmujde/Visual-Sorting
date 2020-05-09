import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/*
   TO DO:
    0) Make current in different color ++
    1) Add Different sorting algorithms and make it work with different algos ++
    1.5) Add something like chooser and work the chooser in other method. not in the Constructor++
    2) Add a Start to Menu ++
    3) Make a Menu to Choose algo++
    4) Add speed chooser to menu to 0 to 1
    5) Make this Menu preface like login screen ++
    6) Add restart After Sorting is finish++
 */
public class Printer extends JComponent implements ActionListener {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private int count = 0;
    private int[] data;
    private int cur;
    private JFrame mainFrame;
    private JButton starter;
    private JButton restart;
    private JPanel background;
    private JComboBox<String> algorithms;

    public Printer() {
        //Set the items//////
        algorithms = new JComboBox<>();
        mainFrame = new JFrame();
        background = new JPanel();
        starter = new JButton("Start");
        restart = new JButton("Restart");
        restart.addActionListener(this);
        starter.addActionListener(this);
        starter.setBounds(10, 100, 100, 50);
        algorithms.setBounds(10, 40, 100, 50);
        restart.setBounds(540, 300, 100, 60);

        ///////////////////////
        // Add algorithms //
        algorithms.addItem("Bubble Sort");
        algorithms.addItem("Quicksort");
        algorithms.addItem("Heapsort");
        algorithms.addItem("Insertion Sort");
        algorithms.addItem("Merge Sort");
        algorithms.addItem("Radix Sort");
        algorithms.addItem("Cocktail Sort");
        ////////////////
        data = new int[WIDTH];
        randomize(data);
        /////////Add items to Main Frame ////////
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setTitle("Visual Sorting");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(starter);
        mainFrame.add(algorithms);
        mainFrame.add(restart);
        restart.setVisible(false);
        mainFrame.add(this);
        mainFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        for (int i = 0; i < data.length; i++) {
            if (cur == i)
                g2.setColor(Color.ORANGE);
            else
                g2.setColor(Color.GRAY);
            if (count == 0) {
                g2.setColor(Color.RED);// starter screen will be look different
            }
            g2.drawLine(i, HEIGHT, i, HEIGHT - data[i]);
        }
    }

    private void delay(long nanoseconds) {// dont use thread due to speed Thread's are too slow
        long timeElapsed;
        final long startTime = System.nanoTime();
        do {
            timeElapsed = System.nanoTime() - startTime;
        } while (timeElapsed < nanoseconds);
    }

    private void startSorting(String sortingAlgorithm) {
        switch (sortingAlgorithm) {
            case "Bubble Sort":
                bubbleSort(data);
                break;
            case "Quicksort":
                quickSort(data, 0, data.length - 1);
                break;
            case "Heapsort":
                heapSort(data);
                break;
            case "Insertion Sort":
                insertionSort(data);
                break;
            case "Merge Sort":
                mergeSort(data, 0, data.length - 1);
                break;
            case "Radix Sort":
                radixSort(data, data.length);
                break;
            case "Cocktail Sort":
                cocktailSort(data);
                break;

        }
        cur = -1;//delete the current pointer to dont point orange
        System.out.println(count);
        repaint();
        restart.setVisible(true);
    }

    /////////////////////BubbleSort//////////////
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j+1] and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                cur = j;
                repaint();
                delay(10_000);
            }
        }
    }

    ///////////Cocktail Sort
    private void cocktailSort(int[] a) {
        boolean swapped = true;
        int start = 0;
        int end = a.length;

        while (swapped) {
            // reset the swapped flag on entering the 
            // loop, because it might be true from a 
            // previous iteration. 
            swapped = false;

            // loop from bottom to top same as 
            // the bubble sort 
            for (int i = start; i < end - 1; ++i) {
                swapped = isSwapped(a, swapped, i);
            }

            // if nothing moved, then array is sorted. 
            if (!swapped)
                break;

            // otherwise, reset the swapped flag so that it 
            // can be used in the next stage 
            swapped = false;

            // move the end point back by one, because 
            // item at the end is in its rightful spot 
            end = end - 1;

            // from top to bottom, doing the 
            // same comparison as in the previous stage 
            for (int i = end - 1; i >= start; i--) {
                swapped = isSwapped(a, swapped, i);
            }

            // increase the starting point, because 
            // the last stage would have moved the next 
            // smallest number to its rightful spot. 
            start = start + 1;
        }
    }

    ////////////////////////
    private boolean isSwapped(int[] a, boolean swapped, int i) {
        if (a[i] > a[i + 1]) {
            int temp = a[i];
            a[i] = a[i + 1];
            a[i + 1] = temp;
            cur = i;
            repaint();
            delay(10_000);
            swapped = true;
        }
        return swapped;
    }

    //////////////////////////
    /////////////Merge Sort////
    private void merge(int[] arr, int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int[] L = new int[n1];
        int[] R = new int[n2];

        /*Copy data to temp arrays*/
        if (n1 >= 0) System.arraycopy(arr, l, L, 0, n1);
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                cur = k;
                repaint();
                delay(1_500_000);
                i++;
            } else {
                cur = k;
                repaint();
                delay(1_500_000);
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            cur = k;
            repaint();
            delay(1_500_000);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            cur = m;
            repaint();
            delay(3_000_000);
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    //////////////////////////
    ////Insertion Sort/////////
    private void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                cur = j;
                repaint();
                delay(10_000);
            }
            arr[j + 1] = key;

        }
    }

    ///////////////////////
    ///////////Quick Sort////
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                cur = j;
                repaint();
                delay(1_000_000);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        cur = i;
        repaint();
        delay(1_000_000);
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            cur = pi;
            repaint();
            delay(10_000_000);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    //////////////////Heap Sort///////////
    public void heapSort(int[] arr) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            cur = i;
            repaint();
            delay(1_000_000);
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            cur = i;
            repaint();
            delay(1_000_000);
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    /////////////////////////////////////
    /////////////Radix Sort/////////////
    private int getMax(int[] arr, int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    private void countSort(int[] arr, int n, int exp) {
        int[] output = new int[n]; // output array
        int i;
        int[] countarr = new int[10];
        Arrays.fill(countarr, 0);

        // Store count of occurrences in countarr[]
        for (i = 0; i < n; i++)
            countarr[(arr[i] / exp) % 10]++;

        // Change countarr[i] so that countarr[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            countarr[i] += countarr[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[countarr[(arr[i] / exp) % 10] - 1] = arr[i];
            countarr[(arr[i] / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to curent digit
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
            cur = i;
            repaint();
            delay(2_000_000);
        }
    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    private void radixSort(int[] arr, int n) {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }
    ////////////////////////////////////

    //////////////randomize array elements
    private void randomize(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 720);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Start") && count == 0) {
            restart.setVisible(false);
            starter.setVisible(false);// i dont want to see after i click
            algorithms.setVisible(false);
            count++;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    startSorting(String.valueOf(algorithms.getSelectedItem()));
                }
            }.start();

        }
        if (command.equals("Restart")) {
            restart.setVisible(false);
            starter.setVisible(true);
            algorithms.setVisible(true);
            count = 0;
            randomize(data);
            repaint();
        }
    }
}
