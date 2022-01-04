package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResistorTest {
    private double resistance = 40;
    private double voltage = 20;
    private Circuit myCircuit1;
    private Circuit myCircuit2;

    @BeforeEach
    public void setup() {
        myCircuit1 = new Circuit(true, voltage);
        myCircuit2 = new Circuit(false, voltage);
    }

    @Test
    public void powerCalculationTest() {

        myCircuit1.addResistor(resistance);
        myCircuit1.addResistor(resistance);
        myCircuit2.addResistor(resistance);
        myCircuit2.addResistor(resistance);
        assertEquals(myCircuit1.getResistor(0).powerCalculation(myCircuit1), 2.5);
        assertEquals(myCircuit2.getResistor(0).powerCalculation(myCircuit2), 10);
    }

}
