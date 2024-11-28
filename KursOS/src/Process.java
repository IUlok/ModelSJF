import java.awt.*;

public class Process {
    String name;
    int generalTimeProcess;
    int leftTimeProcess;
    Color color;

    public Process(String name, int burstTime, Color color) {
        this.name = name;
        this.generalTimeProcess = burstTime;
        this.leftTimeProcess = burstTime;
        this.color = color;
    }
}