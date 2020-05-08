import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
   TO DO:
    0) Make current in different color ++
    1) Add Different sorting algorithms and make it work with different algos
    1.5) Add something like chooser and work the chooser in other method. not in the Constructor
    2) Add a Start to Menu ++
    3) Make a Menu to Choose algo
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
        algorithms.setBounds(10,40,100,50);
        setOpaque(true);
        mainFrame.setBackground(Color.GRAY);
        ///////////////////////
        // Add algorithms //
        algorithms.addItem("Bubble Sort");
        algorithms.addItem("Quicksort");
        algorithms.addItem("Heapsort");
        algorithms.addItem("Counting Sort");
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
        mainFrame.add(this);
        mainFrame.setVisible(true);
       // bubbleSort(data);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.drawLine(1250, HEIGHT, 1250, HEIGHT - 500);
        for (int i = 0; i < data.length; i++) {
            if (cur == i)
                g2.setColor(Color.ORANGE);
            else
                g2.setColor(Color.GRAY);
            if(count == 0){
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
    private void startSorting(int alabilir,String olabilir){}

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
                delay(10000);
            }
        }
        System.out.println("heyy");
        mainFrame.add(restart);
        restart.setVisible(true);
    }

    private void randomize(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 720);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Start") && count == 0){
            restart.setVisible(false);
            setOpaque(false);
            starter.setVisible(false);// i dont want to see after i click
            algorithms.setVisible(false);
            count++;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    bubbleSort(data);
                }
            }.start();

        }
        if(command.equals("Restart")){
            starter.setVisible(true);
            algorithms.setVisible(true);
            restart.setVisible(false);
            randomize(data);
            repaint();
        }
    }
}
