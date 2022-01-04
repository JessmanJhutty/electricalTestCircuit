package model;

// Represents an ampmeter which sole purpose is to return the current
public class Ampmeter implements MeterComponent {
    private double current;

    /*
     * EFFECTS: Creates a new ampmeter
     */
    public Ampmeter() {

    }

    /*
     * REQUIRES: index to be >= 0
     * EFFECTS: calculates the current of the circuit if in series, and current going through resistor
     * in ascending order if in parallel.
     */
    // Note ampmeters only work for resistors
    @Override
    public double meterCalculation(Circuit myCircuit, int index) {
        if (myCircuit.getSeriesOrParallel()) {
            this.current = myCircuit.getVoltage() / myCircuit.getTotalResistance();
        } else {
            this.current = myCircuit.getVoltage() / myCircuit.getResistor(index).getResistance();
        }
        return this.current;
    }
}
