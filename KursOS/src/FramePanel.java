import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FramePanel extends JPanel {
    private java.util.List<Process> processes = new ArrayList<>();
    private Timer timer;
    private Process currentProcess = null;

    public FramePanel() {
        setLayout(new BorderLayout());
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProcess != null && currentProcess.leftTimeProcess > 0) {
                    currentProcess.leftTimeProcess--;
                    if (currentProcess.leftTimeProcess == 0) {
                        currentProcess = selectNextProcess();
                    }
                } else {
                    currentProcess = selectNextProcess();
                }
                repaint();
            }
        });
        timer.start();

        // Панель для ввода данных
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField nameField = new JTextField(10);
        JTextField burstTimeField = new JTextField(5);
        JButton addButton = new JButton("Добавить процесс");
        JButton removeButton = new JButton("Сброс");

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int burstTime = Integer.parseInt(burstTimeField.getText());
                addProcess(name, burstTime, Color.getHSBColor((float) Math.random(), 1, 1));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Некорректный ввод данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            processes.clear();
            repaint();
        });

        inputPanel.add(new JLabel("Название:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Длительность:"));
        inputPanel.add(burstTimeField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private Process selectNextProcess() {
        if (processes.isEmpty()) return null;
        Collections.sort(processes, Comparator.comparingInt(p -> p.leftTimeProcess));
        Process nextProcess = processes.get(0);
        return nextProcess;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int x = 30;
        int y = 30;

        for (Process process : processes) {
            if (process.leftTimeProcess > 0) {
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(process.color);
                int processWidth = Math.max(0, 500-((500/process.generalTimeProcess) * process.leftTimeProcess));
                g.fillRect(getWidth()/2-250, y, processWidth, 10);
                g.setColor(Color.BLACK);
                g.drawRect(getWidth()/2-250, y, processWidth, 10);
                g.drawRect(getWidth()/2-250, y, 500, 30);

                g.setColor(Color.BLACK);
                String msgProc =  "Процесс: " + process.name + ". Длительность: " + process.leftTimeProcess + " секунд";
                g.drawString(msgProc, getWidth()/2-msgProc.length()*5, y + 25);
                y += 50;
            }
        }

        processes.removeIf(p -> p.leftTimeProcess <= 0);
    }

    public void addProcess(String name, int burstTime, Color color) {
        if(processes.size() > 4) {
            JOptionPane.showMessageDialog(this, "Нельзя выводить более 5-ти процессов", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Process process = new Process(name, burstTime, color);
            processes.add(process);
            Collections.sort(processes, Comparator.comparingInt(p -> p.generalTimeProcess));
            if (currentProcess == null){
                currentProcess = selectNextProcess();
            }
            repaint();
        }
    }
}
