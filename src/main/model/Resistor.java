
package model;

// Represents a resistor, which has a resistance and power
public class  Resistor extends ResistorComponent {

    /*
     * REQUIRES: resistance to be > 0
     * EFFECTS: Creates a resistor with the resistance given.
     */
    public Resistor(double resistance) {
        super.resistance = resistance;
    }


}