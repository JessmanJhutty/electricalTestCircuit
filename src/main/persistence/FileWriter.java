package persistence;

import model.Circuit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    private PrintWriter printWriter;


    // EFFECTS: constructs a writer that will write data to file
    public FileWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes to a file
    public void write(List<Circuit> circuitList) {

        for (Circuit circuit : circuitList) {
            String circuitFileLine = circuit.getNextCircuitId() + FileReader.DELIMITER
                    + circuit.getCircuitId() + FileReader.DELIMITER
                    + circuit.getSeriesOrParallel() + FileReader.DELIMITER
                    + circuit.getVoltage()  + FileReader.DELIMITER
                    + circuit.getResistorCounter()  + FileReader.DELIMITER
                    + circuit.getLightbulbCounter()  + FileReader.DELIMITER
                    + circuit.getVoltmeterCounter()  + FileReader.DELIMITER
                    + circuit.getAmpmeterCounter()   + FileReader.DELIMITER;
            for (int i = 0; i < circuit.getResistorCounter(); i++) {
                circuitFileLine += circuit.getResistor(i).getResistance()  + FileReader.DELIMITER;
            }
            for (int i = 0; i < circuit.getLightbulbCounter(); i++) {
                circuitFileLine += circuit.getLightbulb(i).getResistance()  + FileReader.DELIMITER;
            }
            circuitFileLine += "x\n";
            printWriter.write(circuitFileLine);
        }
        printWriter.flush();
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }

}
