package persistence;


import model.Circuit;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class FileReaderTest {
    @Test
    void testParseCircuit() {
        try {
            List<Circuit> circuits = FileReader.readCircuits(new File("./data/testCircuit.txt"));
            Circuit circuit1 = circuits.get(0);
            assertEquals(1, circuit1.getCircuitId());
            assertEquals(true, circuit1.getSeriesOrParallel());
            assertEquals(20.0, circuit1.getVoltage());
            assertEquals(34.9, circuit1.getResistor(0).getResistance());
            assertEquals(10.9, circuit1.getLightbulb(0).getResistance());

            Circuit circuit2 = circuits.get(1);
            assertEquals(false, circuit2.getSeriesOrParallel());
            assertEquals(10.0, circuit2.getVoltage());
            assertEquals(1,circuit2.getAmpmeterCounter());
            assertEquals(1,circuit2.getVoltmeterCounter());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }


    @Test
    void testIOException() {
        try {
            FileReader.readCircuits(new File("./path/does/not/exist/testCircuit.txt"));
        } catch (IOException e) {
            // expected
        }
    }

}
