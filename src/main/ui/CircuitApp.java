package ui;

import model.Circuit;
import persistence.FileReader;
import persistence.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Circuit application
public class CircuitApp {
    private Scanner input;
    private Circuit myCircuit;
    public List<Circuit> circuitList = new ArrayList<>();
    private static final String FILE_NAME = "./data/circuits.txt";

    // EFFECTS" runs the circuit application
    public CircuitApp() {
        FileReader fileReader = new FileReader();
        runCircuit();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCircuit() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        loadCircuits();

        // The following code is from the TellerApp
        // Using scanner for user input
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    // Skeleton from Teller App
    // EFFECTS: saves state of chequing and savings accounts to ACCOUNTS_FILE
    public void saveCircuits() {
        try {
            FileWriter fileWriter = new FileWriter(new File(FILE_NAME));
            fileWriter.write(circuitList);
            fileWriter.close();
            System.out.println("Circuits saved to file " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save circuits to " + FILE_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // Skeleton from Teller App
    // MODIFIES: this
    // EFFECTS: loads accounts from FILE_NAME, if that file exists;
    // otherwise carries on.
    public void loadCircuits() {
        try {
            circuitList = FileReader.readCircuits(new File(FILE_NAME));
        } catch (IOException e) {
            System.out.println("IOException caught, circuitList is empty");
        }
    }

    // EFFECTS: Displays the menu for the user to select
    public void displayMenu() {
        System.out.println("Hello, welcome to the circuit maker!");
        System.out.println("Would you like to make a series or parallel circuit?");
        System.out.println("s for series");
        System.out.println("p for parallel");
        System.out.println("q to quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the menu option
    public void processCommand(String command) {
        double voltage;
        if (command.equals("s")) {
            System.out.println("You have chosen a series circuit!");
            System.out.println("What voltage would you like to have for your circuit?");
            voltage = input.nextDouble();
            init(command, voltage);
            next();
        } else if (command.equals("p")) {
            System.out.println("You have chosen a parallel circuit");
            System.out.println("What voltage would you like to have for your circuit?");
            voltage = input.nextDouble();
            init(command, voltage);
            next();
        } else {
            System.out.println("Not a valid command");
            System.out.println();
        }
    }

    // EFFECTS: display options menu and
    // processes user command for the options menu
    public void next() {
        options();
        String option = input.next();
        optionsMenu(option);
    }

    // EFFECTS: Shows the options menu
    public void options() {
        System.out.println("What would you like to do next?");
        System.out.println("Press a to add a component.");
        System.out.println("Press r to remove a component.");
        System.out.println("Press c to change a lightbulb or resistors resistance.");
        System.out.println("Press v to view your circuit.");
        System.out.println("Press s to save your circuit.");
        System.out.println("Press d to delete a previously saved circuit.");
        System.out.println("Press l to load another circuit.");
        System.out.println("Press b to go start a new circuit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the menu option
    public void optionsMenu(String option) {
        if (option.equals("a")) {
            askToAdd();
        } else if (option.equals("r")) {
            askToRemove();
        } else if (option.equals("v")) {
            viewCircuit();
        } else if (option.equals("c")) {
            changeResistorComponent();
        } else if (option.equals("b")) {
            runCircuit();
        } else if (option.equals("s")) {
            circuitList.add(myCircuit);
            saveCircuits();
            next();
        } else if (option.equals("d")) {
            circuitRemover();
        } else if (option.equals("l")) {
            circuitLoader();
        }

    }

    // REQUIRES: index >= 1
    // MODIFIES: this
    // EFFECTS: removes a circuit from the list of saved circuits
    public void circuitRemover() {
        if (circuitList.size() == 0) {
            System.out.println("You don't have any circuits to remove");
            System.out.println("Try saving some circuits first");
        } else {
            System.out.println("You have " + circuitList.size() + " Circuits.");
            System.out.println("Enter which circuit number you would like to remove");
            int index = input.nextInt();
            circuitList.remove(index - 1);
        }
        next();
    }

    // REQUIRES: index >= 1
    // MODIFIES: this
    // EFFECTS:  loads a circuit from the list of saved circuits
    public void circuitLoader() {
        if (circuitList.size() == 0) {
            System.out.println("You don't have any circuits to load");
            System.out.println("Try saving some circuits first");
        } else {
            System.out.println("You have " + circuitList.size() + " Circuits.");
            System.out.println("Enter which circuit number you would like to load");
            int index = input.nextInt();
            myCircuit = circuitList.get(index - 1);
        }
        next();
    }

    // EFFECTS: displays the circuit specifications on the console
    // including the measurements on the voltmeters and the ampmeters
    private void viewCircuit() {
        System.out.println("You have " + myCircuit.getVoltmeterCounter() + " voltmeter(s)");
        System.out.println("You have " + myCircuit.getAmpmeterCounter() + " ampmeter(s)");
        System.out.println("You have " + myCircuit.getResistorCounter() + " resistor(s)");
        System.out.println("You have " + myCircuit.getLightbulbCounter() + " lightbulb(s)");
        System.out.println();
        viewResistors();
        viewLightbulbs();
        viewVoltmeters();
        viewAmpmeters();
        next();
    }

    // EFFECTS: Displays all resistors, their values and their powers
    public void viewResistors() {
        int count = 1;
        for (int i = 0; i < myCircuit.getResistorCounter(); i++) {
            System.out.println("Resistor " + count  + " has a resistance of "
                    + myCircuit.getResistor(i).getResistance());
            System.out.println("and has a power of " + myCircuit.getResistor(i).powerCalculation(myCircuit)
                    + " Watts");
            System.out.println();
            count++;
        }
    }

    // EFFECTS: Displays all lightbulbs, their values, their powers and their brightness level
    // with a description
    public void viewLightbulbs() {
        int count = 1;
        for (int i = 0; i < myCircuit.getLightbulbCounter(); i++) {

            System.out.println("Lightbulb " + count + " has a resistance of "
                    + myCircuit.getLightbulb(i).getResistance());

            System.out.println("has a power of " + myCircuit.getLightbulb(i).powerCalculation(myCircuit)
                    + " Watts");
            if (myCircuit.getLightbulb(i).getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 0) {
                System.out.println("And is also off");
            } else if (myCircuit.getLightbulb(i)
                    .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 1) {
                System.out.println("And is also dim");
            } else if (myCircuit.getLightbulb(i)
                    .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 2) {
                System.out.println("And is also bright");
            } else if (myCircuit.getLightbulb(i)
                    .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 3) {
                System.out.println("And is also on fire!");
            }
            System.out.println();
            count++;
        }
    }

    // EFFECTS: Displays all ampmeters, and their corresponding current value
    public void viewAmpmeters() {
        int count = 1;
        for (int i = 0; i < myCircuit.getAmpmeterCounter(); i++) {
            System.out.println("Ampmeter " + count + " reads a value of "
                     + myCircuit.getAmpmeter(i).meterCalculation(myCircuit, i) + " Amps");
            System.out.println();
            count++;
        }

    }

    // EFFECTS: Displays all voltmeters, and their corresponding voltage value
    public void viewVoltmeters() {
        int count = 1;
        for (int i = 0; i < myCircuit.getVoltmeterCounter(); i++) {
            System.out.println("Voltmeter " + count + " reads a value of "
                    + myCircuit.getVoltmeter(i).meterCalculation(myCircuit, i) + " Volts");
            System.out.println();
            count++;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user which component to add to the circuit,
    // processes this command
    public void askToAdd() {
        displayAddActions();
        String action = input.next();
        processAddAction(action);
    }

    // MODIFIES: this
    // EFFECTS: prompts the user which component to remove from the circuit,
    // processes this command
    public void askToRemove() {
        displayRemoveActions();
        String action = input.next();
        processRemoveAction(action);
    }

    // REQUIRES: Index >= 1
    // MODIFIES: this
    // EFFECTS: prompts the user to change the value of a resistor or a lightbulb
    // and also processes this commmand.
    public void changeResistorComponent() {
        System.out.println("Change a lightbulb or a resistor?");
        String command = input.next();
        if (myCircuit.getResistanceComponentCounter() == 0) {
            System.out.println("Add some resistors or lightbulbs first!");
        } else {
            if (command.equals("l")) {
                System.out.println("Which lightbulb would you like to change?");
                int index = input.nextInt();
                System.out.println("What resistance would you like?");
                double resistance = input.nextDouble();
                myCircuit.getLightbulb(index - 1).setResistance(resistance);
                next();
            } else if (command.equals("r")) {
                System.out.println("Which resistor would you like to change?");
                int index = input.nextInt();
                System.out.println("What resistance would you like?");
                double resistance = input.nextDouble();
                myCircuit.getResistor(index - 1).setResistance(resistance);
                next();
            }
        }

    }

    // EFFECTS: displays all the actions the user can add to the circuit
    public void displayAddActions() {
        System.out.println("Press r to add a resistor to your circuit.");
        System.out.println("Press l to add a lightbulb to your circuit.");
        System.out.println("Press v to add a voltmeter to your circuit.");
        System.out.println("Press a to add an ampmeter to your circuit.");
        System.out.println("Press b to go back.");
    }

    // EFFECTS: displays all the actions the user can remove from the circuit
    public void displayRemoveActions() {
        System.out.println("Press r to remove a resistor to your circuit.");
        System.out.println("Press l to remove a lightbulb to your circuit.");
        System.out.println("Press v to remove a voltmeter to your circuit.");
        System.out.println("Press a to remove an ampmeter to your circuit.");
        System.out.println("Press b to go back.");
    }

    // REQUIRES: voltage > 0
    // MODIFIES: this
    // EFFECTS: initializes the circuit with the specifications the user has given
    public void init(String command, double voltage) {
        if (command.equals("s")) {
            myCircuit = new Circuit(true, voltage);
        } else if (command.equals("p")) {
            myCircuit = new Circuit(false, voltage);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes any add action that the user gives
    public void processAddAction(String action) {
        if (action.equals("r")) {
            doAddResistor();
        } else if (action.equals("l")) {
            doAddLightbulb();
        } else if (action.equals("v")) {
            doAddVoltmeter();
        } else if (action.equals("a")) {
            doAddAmpmeter();
        } else if (action.equals("b")) {
            next();
        } else {
            System.out.println("Selection not valid...");
            next();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an ampmeter to the circuit
    private void doAddAmpmeter() {
        if (myCircuit.getResistanceComponentCounter() == 0) {
            System.out.println("Add some resistors or lightbulbs first!");
            askToAdd();
        }
        myCircuit.addAmpmeter();
        next();
    }

    // MODIFIES: this
    // EFFECTS: adds a voltmeter to the circuit
    private void doAddVoltmeter() {
        if (myCircuit.getResistanceComponentCounter() == 0) {
            System.out.println("Add some resistors or lightbulbs first!");
            askToAdd();
        }
        myCircuit.addVoltmeter();
        next();
    }

    // MODIFIES: this
    // EFFECTS: adds a lightbulb to the circuit
    private void doAddLightbulb() {
        System.out.println("What resistance would you like?");
        double resistance = input.nextDouble();

        while (resistance <= 0) {
            System.out.println("That is not a valid resistance");
            System.out.println("What resistance would you like?");
            resistance = input.nextDouble();
        }
        myCircuit.addLightbulb(resistance);
        next();
    }

    // MODIFIES: this
    // EFFECTS: adds a resistor to the circuit
    private void doAddResistor() {
        System.out.println("What resistance would you like?");
        double resistance = input.nextDouble();

        while (resistance <= 0) {
            System.out.println("That is not a valid resistance");
            System.out.println("What resistance would you like?");
            resistance = input.nextDouble();
        }
        myCircuit.addResistor(resistance);
        next();
    }

    // MODIFIES: this
    // EFFECTS: processes any remove action that the user gives
    public void processRemoveAction(String action) {
        if (action.equals("r")) {
            doRemoveResistor();
        } else if (action.equals("l")) {
            doRemoveLightbulb();
        } else if (action.equals("v")) {
            doRemoveVoltmeter();
        } else if (action.equals("a")) {
            doRemoveAmpmeter();
        } else if (action.equals("b")) {
            next();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes an ampmeter from the circuit
    private void doRemoveAmpmeter() {
        if (myCircuit.getAmpmeterCounter() == 0) {
            System.out.println("No ampmeters to remove");
            next();
        }
        System.out.println("Which ampmeter would you like to remove (give number)");
        int index = input.nextInt();
        myCircuit.removeAmpmeter(index);
        next();
    }

    // MODIFIES: this
    // EFFECTS: Removes a voltmeter from the circuit
    private void doRemoveVoltmeter() {
        if (myCircuit.getVoltmeterCounter() == 0) {
            System.out.println("No voltmeters to remove");
            next();
        }
        System.out.println("Which voltmeter would you like to remove (give number)");
        int index = input.nextInt();
        myCircuit.removeVoltmeter(index);
        next();
    }

    // MODIFIES: this
    // EFFECTS: Removes a lightbulb from the circuit
    private void doRemoveLightbulb() {
        if (myCircuit.getLightbulbCounter() == 0) {
            System.out.println("No lightbulbs to remove");
            next();
        }
        System.out.println("Which lightbulb would you like to remove (give number)");
        int index = input.nextInt();
        myCircuit.removeLightbulb(index);
        next();
    }

    // MODIFIES: this
    // EFFECTS: Removes a resistor from the circuit
    private void doRemoveResistor() {
        if (myCircuit.getResistorCounter() == 0) {
            System.out.println("No resistors to remove");
            next();
        }
        System.out.println("Which resistor would you like to remove (give number)");
        int index = input.nextInt();
        myCircuit.removeResistor(index);
        next();
    }

}
