package model;

import java.util.ArrayList;

// This class represents a circuit with lists of any component it may have, a voltage and
// whether or not it is a series or a parallel circuit
public class Circuit {
    private int resistorCounter;
    private int voltmeterCounter;
    private int lightbulbCounter;
    private int ampmeterCounter;
    private double voltage;
    private double totalResistance;
    private boolean seriesOrParallel; // true for series and false for parallel
    private ArrayList<Resistor> resistors = new ArrayList<>();
    private ArrayList<Voltmeter> voltmeters = new ArrayList<>();
    private ArrayList<Lightbulb> lightbulbs = new ArrayList<>();
    private ArrayList<Ampmeter> ampmeters = new ArrayList<>();
    private int circuitId;
    private int nextCircuitId = 1;

    /*
     * REQUIRES: Voltage to be > 0
     * EFFECTS: Creates a circuit, with a parameter seriesOrParallel, to determine whether
     * its series or parallel and a voltage parameter determining its input voltqage source
     */
    public Circuit(boolean seriesOrParallel, double voltage) {
        circuitId = nextCircuitId++;
        this.seriesOrParallel = seriesOrParallel;
        this.voltage = voltage;
    }

    /*
     * REQUIRES:voltage >= 0
     * EFFECTS: constructs a circuit with id, type  and  voltage;
     * nextAccountId is the id of the next circuit to be constructed
     * NOTE: this constructor is to be used only when constructing
     * an account from data stored in file
     */
    public Circuit(int nextId, int id, boolean seriesOrParallel, double voltage) {
        nextCircuitId = nextId;
        this.circuitId = id;
        this.seriesOrParallel = seriesOrParallel;
        this.voltage = voltage;
    }


    /*
     * REQUIRES: resistance > 0
     * MODIFIES: this
     * EFFECTS: Adds a resistor to the circuit.
     * Adds one to the resistor counter
     */
    public void addResistor(double resistance) {
        Resistor r1 = new Resistor(resistance);
        resistors.add(r1);
        resistorCounter++;
    }

    /*
     * REQUIRES: resistance > 0
     * MODIFIES: this
     * EFFECTS: Adds a lightbulb to the circuit.
     * Adds one to the lightbulb counter
     */
    public void addLightbulb(double resistance) {
        Lightbulb l1 = new Lightbulb(resistance);
        lightbulbs.add(l1);
        lightbulbCounter++;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds a voltmeter to the circuit.
     * Adds one to the voltmeter counter
     */
    public void addVoltmeter() {
        Voltmeter v1 = new Voltmeter();
        voltmeters.add(v1);
        voltmeterCounter++;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds an ampmeter to the circuit.
     * Adds one to the ampmeter counter
     */
    public void addAmpmeter() {
        Ampmeter a1 = new Ampmeter();
        ampmeters.add(a1);
        ampmeterCounter++;
    }

    /* REQUIRES: index >= 1
     * MODIFIES: this
     * EFFECTS: removes a resistor from the circuit.
     * Removes one from the resistor counter
     */
    public void removeResistor(int index) {
        resistors.remove(index - 1);
        resistorCounter--;
    }

    /* REQUIRES: index >= 1
     * MODIFIES: this
     * EFFECTS: removes a lightbulb from the circuit, form index.
     * Removes one from the lightbulb counter
     */
    public void removeLightbulb(int index) {
        lightbulbs.remove(index - 1);
        lightbulbCounter--;
    }

    /* REQUIRES: index >= 1
     * MODIFIES: this
     * EFFECTS: removes a voltmeter from the circuit, form index.
     * Removes one from the voltmeter counter
     */
    public void removeVoltmeter(int index) {
        voltmeters.remove(index - 1);
        voltmeterCounter--;
    }

    /* REQUIRES: index >= 1
     * MODIFIES: this
     * EFFECTS: removes an ampmeter from the circuit, from index.
     * Removes one from the ampmeter counter
     */
    public void removeAmpmeter(int index) {
        ampmeters.remove(index - 1);
        ampmeterCounter--;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Returns the total resistance of the circuit, counting lightbulbs
     *  as well as resistors. Value also depends on the type of circuit, whether its
     * in series or if its in parallel
     */
    public double getTotalResistance() {
        int i;
        totalResistance = 0;
        if (seriesOrParallel) {
            for (i = 0; i <  resistorCounter; i++) {
                totalResistance += resistors.get(i).getResistance();
            }
            for (i = 0; i < lightbulbCounter; i++) {
                totalResistance += lightbulbs.get(i).getResistance();
            }
            return totalResistance;
        } else {
            for (i = 0; i <  resistorCounter; i++) {
                totalResistance += 1 / (resistors.get(i).getResistance());
            }
            for (i = 0; i < lightbulbCounter; i++) {
                totalResistance += 1 / (lightbulbs.get(i).getResistance());
            }
            return 1 / totalResistance;
        }
    }

    /* REQUIRES: position >= 1, resistance > 0
     * MODIFIES: this
     * EFFECTS: changes the resistance of a lightbulb at a given position.
     */
    public void changeResistanceLightbulb(int position, double resistance) {
        lightbulbs.get(position - 1).setResistance(resistance);
    }

    /* REQUIRES: position >= 1, resistance > 0
     * MODIFIES: this
     * EFFECTS: changes the resistance of a resistor at a given position.
     */
    public void changeResistance(int position, double resistance) {
        resistors.get(position - 1).setResistance(resistance);
    }

    /*
     * EFFECTS: Returns the total amount of resistor components.
     */
    public int getResistanceComponentCounter() {
        return resistorCounter + lightbulbCounter;
    }

    public double getVoltage() {
        return voltage;
    }

    public boolean getSeriesOrParallel() {
        return seriesOrParallel;
    }

    public Resistor getResistor(int index) {
        return resistors.get(index);
    }

    public Lightbulb getLightbulb(int index) {
        return lightbulbs.get(index);
    }

    public Ampmeter getAmpmeter(int index) {
        return ampmeters.get(index);
    }

    public Voltmeter getVoltmeter(int index) {
        return voltmeters.get(index);
    }

    public int getAmpmeterCounter() {
        return ampmeterCounter;
    }

    public void setAmpmeterCounter(int ampmeterCounter) {
        this.ampmeterCounter = ampmeterCounter;
    }

    public int getVoltmeterCounter() {
        return voltmeterCounter;
    }

    public void setVoltmeterCounter(int voltmeterCounter) {
        this.voltmeterCounter = voltmeterCounter;
    }

    public int getResistorCounter() {
        return resistorCounter;
    }

    public void setResistorCounter(int resistorCounter) {
        this.resistorCounter = resistorCounter;
    }

    public int getLightbulbCounter() {
        return lightbulbCounter;
    }

    public void setLightbulbCounter(int lighbulbCounter) {
        this.lightbulbCounter = lighbulbCounter;
    }

    public int getCircuitId() {
        return circuitId;
    }

    public int getNextCircuitId() {
        return nextCircuitId;
    }

}

