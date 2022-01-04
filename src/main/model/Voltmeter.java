package model;

// Represents a voltmeter which sole purpose is to return the voltage
public class Voltmeter implements MeterComponent {
    private double voltage;
    private double current;

    /*
     * EFFECTS: Creates a new voltmeter
     */
    public Voltmeter() {

    }

    /*
     * REQUIRES: index to be >= 0
     * EFFECTS: calculates the voltage drop of a resistor in ascending order if in series,
     * and voltage drops of all resistors if in parallel
     */
    // NOTE: the voltmeter works for resistors only
    @Override
    public double meterCalculation(Circuit myCircuit, int index) {
        if (myCircuit.getSeriesOrParallel()) {
            current = myCircuit.getVoltage() / myCircuit.getTotalResistance();
            voltage = current * myCircuit.getResistor(index).getResistance();
            return voltage;
        } else {
            return myCircuit.getVoltage();
        }
    }
}
