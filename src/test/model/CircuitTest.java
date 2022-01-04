package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircuitTest {
    private Circuit myCircuit1;
    private double voltage = 50;

    @BeforeEach
    public void setup() {
        myCircuit1 = new Circuit(true, voltage);
    }

    @Test
    public void testConstructor() {
        assertTrue(myCircuit1.getSeriesOrParallel());
        assertEquals(myCircuit1.getVoltage(), 50 );
    }

    @Test
    public void testConstructorParallel() {
        myCircuit1 = new Circuit(false, voltage);
        assertFalse(myCircuit1.getSeriesOrParallel());
        assertEquals(myCircuit1.getVoltage(), 50 );
    }

    @Test
    public void addResistorTest() {
        double resistance = 50;
        myCircuit1.addResistor(resistance);
        assertEquals(myCircuit1.getResistorCounter(), 1);
    }

    @Test
    public void addLightbulbTest() {
        double resistance = 50;
        myCircuit1.addLightbulb(resistance);
        assertEquals(myCircuit1.getLightbulbCounter(), 1);
    }

    @Test
    public void addAmpmeterTest() {
        myCircuit1.addAmpmeter();
        assertEquals(myCircuit1.getAmpmeterCounter(), 1);
    }

    @Test
    public void addVoltmeterTest() {
        myCircuit1.addVoltmeter();
        assertEquals(myCircuit1.getVoltmeterCounter(), 1);

    }

    @Test
    public void removeResistorTest() {
        double resistance = 50;
        myCircuit1.addResistor(resistance);
        resistance = 40;
        myCircuit1.addResistor(resistance);
        resistance = 30;
        myCircuit1.addResistor(resistance);

        myCircuit1.removeResistor(1);
        assertEquals(myCircuit1.getResistorCounter(), 2);
        assertEquals(myCircuit1.getResistor(0).getResistance(), 40);
        assertEquals(myCircuit1.getResistor(1).getResistance(), 30);
    }

    @Test
    public void removeLightbulbTest() {
        double resistance = 50;
        myCircuit1.addLightbulb(resistance);
        resistance = 40;
        myCircuit1.addLightbulb(resistance);
        resistance = 30;
        myCircuit1.addLightbulb(resistance);

        myCircuit1.removeLightbulb(1);
        assertEquals(myCircuit1.getLightbulbCounter(), 2);
        assertEquals(myCircuit1.getLightbulb(0).getResistance(), 40);
        assertEquals(myCircuit1.getLightbulb(1).getResistance(), 30);
    }

    @Test
    public void removeAmpmeterTest() {
        myCircuit1.addAmpmeter();
        myCircuit1.addAmpmeter();
        myCircuit1.addAmpmeter();
        myCircuit1.removeAmpmeter(3);
        myCircuit1.removeAmpmeter(1);
        assertEquals(myCircuit1.getAmpmeterCounter(), 1);
    }

    @Test
    public void removeVoltmeterTest() {
        myCircuit1.addVoltmeter();
        myCircuit1.addVoltmeter();
        myCircuit1.addVoltmeter();
        myCircuit1.removeVoltmeter(3);
        myCircuit1.removeVoltmeter(1);
        assertEquals(myCircuit1.getVoltmeterCounter(), 1);
    }

    @Test
    public void getTotalResistanceTest() {
        double resistance = 50;
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);
        resistance = 30;
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);

        assertEquals(myCircuit1.getTotalResistance(), 160);

        myCircuit1 = new Circuit(false, voltage);
        resistance = 50;
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);
        resistance = 30;
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);

        assertEquals(myCircuit1.getTotalResistance(), 9.375);
    }

    @Test
    public void changeResistanceLightbulbTest() {
        double resistance = 50;
        myCircuit1.addLightbulb(resistance);
        myCircuit1.addLightbulb(resistance);
        resistance = 25;
        myCircuit1.changeResistanceLightbulb(2, resistance);
        assertEquals(myCircuit1.getLightbulb(0).getResistance(), 50);
        assertEquals(myCircuit1.getLightbulb(1).getResistance(), 25);
    }

    @Test
    public void changeResistanceTest() {
        double resistance = 50;
        myCircuit1.addResistor(resistance);
        myCircuit1.addResistor(resistance);
        resistance = 25;
        myCircuit1.changeResistance(2, resistance);
        assertEquals(myCircuit1.getResistor(0).getResistance(), 50);
        assertEquals(myCircuit1.getResistor(1).getResistance(), 25);
    }

    @Test
    public void getResistorComponentTest() {
        double resistance = 20;
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);
        myCircuit1.addResistor(resistance);
        myCircuit1.addLightbulb(resistance);
        assertEquals(myCircuit1.getResistanceComponentCounter(), 4);
    }


}
