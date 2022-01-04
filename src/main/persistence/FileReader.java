package persistence;

import model.Circuit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {
    public static final String DELIMITER = ",";

    // NOTE: Most of the String, split String and Reader methods are adapted
    // from the teller application. The big exception being the parseCircuit
    // EFFECTS: returns a list of circuits parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Circuit> readCircuits(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of circuits parsed from list of strings
    // where each string contains data for one account
    private static List<Circuit> parseContent(List<String> fileContent) {
        List<Circuit> accounts = new ArrayList<>();
        ParseCircuit myParseCircuit = new ParseCircuit();
        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            accounts.add(myParseCircuit.parseCircuit(lineComponents));
        }

        return accounts;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

}
