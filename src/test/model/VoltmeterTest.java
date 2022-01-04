package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VoltmeterTest {
    private Circuit myCircuit1;
    private Circuit myCircuit2;
    private double voltage = 20;
    private double resistance = 20;

    @BeforeEach
    public void setup() {
        myCircuit1 = new Circuit(true, voltage);
        myCircuit2 = new Circuit(false, voltage);
    }

    @Test
    public void meterCalculationTest() {
        myCircuit1.addResistor(resistance);
        myCircuit1.addResistor(resistance);
        myCircuit1.addResistor(resistance);
        myCircuit1.addResistor(resistance);
        myCircuit1.addVoltmeter();

        myCircuit2.addResistor(resistance);
        myCircuit2.addResistor(resistance);
        myCircuit2.addResistor(resistance);
        myCircuit2.addResistor(resistance);
        myCircuit2.addVoltmeter();
        assertEquals(myCircuit1.getVoltmeter(0).meterCalculation(myCircuit1, 1), 5);
        assertEquals(myCircuit2.getVoltmeter(0).meterCalculation(myCircuit2, 1), 20);
    }


}
