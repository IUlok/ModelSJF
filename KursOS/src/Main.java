import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setTitle("Модель планирования процессов по принципу SJF");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new FramePanel());
        frame.setVisible(true);
    }
}