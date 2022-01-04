package persistence;

import model.Circuit;

import java.util.List;

public class ParseCircuit {

    public  static Circuit parseCircuit(List<String> components) {
        int nextId = Integer.parseInt(components.get(0));
        int id = Integer.parseInt(components.get(1));
        boolean seriesOrParallel = Boolean.parseBoolean(components.get(2));
        double voltage = Double.parseDouble(components.get(3));
        Circuit myCircuit = new Circuit(nextId, id, seriesOrParallel, voltage);
        myCircuit.setAmpmeterCounter(Integer.parseInt(components.get(7))); // ampmeter
        myCircuit.setVoltmeterCounter(Integer.parseInt(components.get(6))); // voltmeter counter
        myCircuit.setResistorCounter(Integer.parseInt(components.get(4)));
        // Integer.parseInt(components.get(4)); is my Resistor Counter
        myCircuit.setLightbulbCounter(Integer.parseInt(components.get(5))); // Lightbulb counter
        for (int i = 0; i < Integer.parseInt(components.get(4)); i++) {
            myCircuit.addResistor(Double.parseDouble(components.get(i + 8)));
        }
        for (int i = 0; i < Integer.parseInt(components.get(5)); i++) {
            myCircuit.addLightbulb(Double.parseDouble(components.get(i + 8 + Integer.parseInt(components.get(4)))));
        }
        for (int i = 0; i < Integer.parseInt(components.get(6)); i++) {
            myCircuit.addVoltmeter();
        }
        for (int i = 0; i < Integer.parseInt(components.get(7)); i++) {
            myCircuit.addAmpmeter();
        }
        setAllCounters(myCircuit, components);
        return myCircuit;
    }

    // EFFECT: sets all the counter values to their original value retrieved from the file
    public static void setAllCounters(Circuit circuit, List<String> components) {
        circuit.setAmpmeterCounter(Integer.parseInt(components.get(7)));
        circuit.setVoltmeterCounter(Integer.parseInt(components.get(6)));
        circuit.setResistorCounter(Integer.parseInt(components.get(4)));
        circuit.setLightbulbCounter(Integer.parseInt(components.get(5)));
    }
}
