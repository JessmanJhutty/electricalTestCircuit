
package model;

// Represents a lightbulb, which has a resistance, power and a brightness level
public class Lightbulb extends ResistorComponent {

    private int brightness;
    private static int DIMPOWER = 60;
    private static int MAXACCEPTABLEPOWER = 100;

    /*
     * REQUIRES: resistance to be > 0
     * EFFECTS: Creates a lightbulb with the resistance given.
     */
    public Lightbulb(double resistance) {
        super.resistance = resistance;
    }
    
    /*
     * REQUIRES: Power must be positive
     * MODIFIES: this
     * EFFECTS: calculates and returns the brightness of the lightbulb connected to a circuit.
     */
    public int getBrightness(double power) {
        if (power == 0) {
            return this.brightness = 0;
        } else if (power < DIMPOWER) {
            return this.brightness = 1;
        } else if (power > DIMPOWER && power < MAXACCEPTABLEPOWER) {
            return this.brightness = 2;
        } else {
            return this.brightness = 3;
        }
    }

}