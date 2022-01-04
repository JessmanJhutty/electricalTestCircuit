package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LightbulbTest {
    private double resistance = 40;
    private double voltage = 20;
    private Circuit myCircuit1;
    private Circuit myCircuit2;
    private Circuit myCircuit3;

    @BeforeEach
    public void setup() {
        myCircuit1 = new Circuit(true, voltage);
        myCircuit2 = new Circuit(false, voltage);
        myCircuit3 = new Circuit(false, 0);
    }

    @Test
    public void powerCalculationTest() {

        myCircuit1.addLightbulb(resistance);
        myCircuit1.addLightbulb(resistance);
        myCircuit2.addLightbulb(resistance);
        myCircuit2.addLightbulb(resistance);
        assertEquals(myCircuit1.getLightbulb(0).powerCalculation(myCircuit1), 2.5);
        assertEquals(myCircuit2.getLightbulb(0).powerCalculation(myCircuit2), 10);
    }

    @Test
    public void getBrightnessTest() {
        myCircuit3.addLightbulb(resistance);
        myCircuit1.addLightbulb(resistance);
        resistance = 5;
        myCircuit2.addLightbulb(resistance);

        Circuit myCircuit4 = new Circuit(true, 50);
        myCircuit4.addLightbulb(resistance);

        assertEquals(myCircuit3.getLightbulb(0).getBrightness(myCircuit3.getLightbulb(0).powerCalculation(myCircuit3)), 0);
        assertEquals(myCircuit1.getLightbulb(0).getBrightness(myCircuit1.getLightbulb(0).powerCalculation(myCircuit1)), 1);
        assertEquals(myCircuit2.getLightbulb(0).getBrightness(myCircuit2.getLightbulb(0).powerCalculation(myCircuit2)), 2);
        assertEquals(myCircuit4.getLightbulb(0).getBrightness(myCircuit4.getLightbulb(0).powerCalculation(myCircuit4)), 3);
    }
}
