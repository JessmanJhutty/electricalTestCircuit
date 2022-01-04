package persistence;

import model.Circuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class FileWriterTest {

    private static final String TEST_FILE = "./data/testCircuit.txt";
    private FileWriter testWriter;
    private List<Circuit> circuitList = new ArrayList<>();
    private Circuit circuit1;
    private Circuit circuit2;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new FileWriter(new File(TEST_FILE));
        circuit1 = new  Circuit(true, 20);
        circuit2 = new Circuit(false , 10);
        circuit1.addResistor(34.9);
        circuit1.addLightbulb(10.9);
        circuit2.addVoltmeter();
        circuit2.addAmpmeter();
    }

    @Test
    void testWriteCircuits() {
        // add circuits to circuit list and write it to a file
        circuitList.add(circuit1);
        circuitList.add(circuit2);
        testWriter.write(circuitList);
        testWriter.close();

        // now read them back in and verify that the circuits have the expected values
        try {
            List<Circuit> circuits = FileReader.readCircuits(new File(TEST_FILE));
            Circuit circuit1 = circuits.get(0);
            assertEquals(1, circuit1.getCircuitId());
            assertEquals(true, circuit1.getSeriesOrParallel());
            assertEquals(20.0, circuit1.getVoltage());
            assertEquals(34.9, circuit1.getResistor(0).getResistance());
            assertEquals(10.9, circuit1.getLightbulb(0).getResistance());

            Circuit circuit2 = circuits.get(1);
            assertEquals(false, circuit2.getSeriesOrParallel());
            assertEquals(10.0, circuit2.getVoltage());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
