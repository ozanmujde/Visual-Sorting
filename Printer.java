import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
   TO DO:
    0) Make current in different color ++
    1) Add Different sorting algorithms and make it work with different algos
    1.5) Add something like chooser and work the chooser in other method. not in the Constructor++
    2) Add a Start to Menu ++
    3) Make a Menu to Choose algo++
    4) Add speed chooser to menu to 0 to 1
    5) Make this Menu preface like login screen ++
    6) Add restart After Sorting is finish
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
    private JComboBox<String> algorithms;

    public Printer() {
        //Set the items//////
        algorithms = new JComboBox<>();
        mainFrame = new JFrame();
        starter = new JButton("Start");
        restart = new JButton("Restart");
        restart.addActionListener(this);
        starter.addActionListener(this);
        starter.setBounds(10, 100, 100, 50);
        algorithms.setBounds(10, 40, 100, 50);
        restart.setBounds(540,300,100,60);
        setOpaque(true);
        mainFrame.setBackground(Color.GRAY);
        ///////////////////////
        // Add algorithms //
        algorithms.addItem("Bubble Sort");
        algorithms.addItem("Quicksort");
        algorithms.addItem("Heapsort");
        algorithms.addItem("Insertion Sort");
        algorithms.addItem("Merge Sort");
        algorithms.addItem("Radix Sort");
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
        // bubbleSort(data);
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

                g2.setColor(Color.WHITE);// starter screen will be look different
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
                quickSort(data,0,data.length-1);
                break;
            case "Heapsort":
                break;
            case "Insertion Sort":
                insertionSort(data);
                break;
            case "Merge Sort":
                ;
                break;
            case "Radix Sort":
                ;
                ;
                break;
        }
        cur = -1;//delete the current pointer
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
    private int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (arr[j] < pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void quickSort(int [] arr, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            cur = pi;
            repaint();
            delay(10_000_000);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }
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
            setOpaque(false);
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
            mainFrame.remove(restart);
            count = 0;
            randomize(data);
            repaint();
        }
    }
}
