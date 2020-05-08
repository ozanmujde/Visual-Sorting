import javax.swing.*;
import java.awt.*;

/*
   TO DO:
    0) Make current in diffrent color ++
    1) Add Diffrent sorting algroithms and make it work with diffrent algos
    2) Add a Start to Menu
    3) Make a Menu to Choose algo
    4) Add speed chooser to menu to 0 to 1
    5) Make this Menu preface like login screen
 */
public class Printer extends JComponent {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private int[] data;
    private int cur;
    JFrame mainFrame;
    JButton button;

    public Printer() {
        mainFrame = new JFrame();
        button = new JButton("Bas");
        button.setBounds(10, 100, 100, 100);
        data = new int[WIDTH];
        randomize(data);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setTitle("Visual Sorting");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(button);
        mainFrame.add(this);
        mainFrame.setVisible(true);
        bubbleSort(data);
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
    }

    private void randomize(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 720);
        }
    }
}
