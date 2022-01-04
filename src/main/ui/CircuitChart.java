package ui;

import model.Circuit;

import javax.swing.*;
import java.awt.*;

public class CircuitChart extends JComponent {
    private Circuit myCircuit;
    int[] slices;
    double total;

    CircuitChart(Circuit cir) {
        this.myCircuit = cir;
        slices = new int[] {myCircuit.getResistorCounter(), myCircuit.getAmpmeterCounter(),
                myCircuit.getVoltmeterCounter(), myCircuit.getLightbulbCounter()};
        total = (myCircuit.getResistorCounter() + myCircuit.getAmpmeterCounter()
                + myCircuit.getVoltmeterCounter() + myCircuit.getLightbulbCounter());
    }

    public void paint(Graphics g) {
        Rectangle area = new Rectangle(100,50,200,200);
        drawPieChart((Graphics2D) g, area);
    }

    // Code is adapted from http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawingaPieChart.htm
    // EFFECTS: Draws a pie chart using total components of the circuit and, each counter value
    private void drawPieChart(Graphics2D g, Rectangle area) {
        double curValue = 0;
        int startAngle = 0;
        for (int i = 0; i < slices.length; i++) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slices[i] * 360 / total);
            switch (i) {
                case 0:
                    g.setColor(Color.black);
                    break;
                case 1:
                    g.setColor(Color.green);
                    break;
                case 2:
                    g.setColor(Color.yellow);
                    break;
                case 3:
                    g.setColor(Color.red);
                    break;
            }
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slices[i];
        }
    }

}

