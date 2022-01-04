package ui;

import model.Circuit;
import persistence.FileReader;
import persistence.FileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gui implements ActionListener  {
    private Circuit myCircuit;
    public List<Circuit> circuitList = new ArrayList<>();
    private static final String FILE_NAME = "./data/circuits.txt";
    private double voltage;
    private int index;
    private double resistance;

    private ArrayList<JButton> buttonList = new ArrayList<>();
    private JTextField seriesVoltageTextField;
    private JTextField parallelVoltageTextField;
    private JTextField circuitLoadIndexTextField;
    private JTextField circuitDeleteIndexTextField;
    private JTextField resistanceTextField;
    private JTextField resistorTextField;
    private JTextField lightbulbTextField;
    private JTextField ampmeterTextField;
    private JTextField voltmeterTextField;
    private JFrame frame = new JFrame();
    private CardLayout cardlayout = new CardLayout();
    final Container contentPane = frame.getContentPane();

    //final JFrame frame = new JFrame();
    private HashMap<String, JPanel> panelMap = new HashMap<>();

    public Gui() {
        setup();
    }


    /* REQUIRES:
       MODIFIES:
       EFFECTS: Sets up new JFrame and adds main panels to the hashmap
    */
    public void setup() {
        // Setup for frame
        loadCircuits();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(cardlayout);
        //frame.setSize(500,500);
        contentPane.setPreferredSize(new Dimension(600, 400));
        mainPanelSetup();
        setupSeries();
        setupParallel();
        load();
        deleteCircuit();
        saveCircuits();
        nextAction();
        add();
        remove();
        change();
        contentPane.add(panelMap.get("main"), BorderLayout.CENTER);
        frame.pack();
        frame.setTitle("Electrical Circuit Builder");
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }


    /* REQUIRES:
     MODIFIES: this
     EFFECTS: Method for setting up main Panel
  */
    public void mainPanelSetup()  {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));

        JLabel welcomeLabel = new JLabel("Welcome to Electrical Circuit Maker: Select your type of circuit");
        JButton seriesButton = new JButton("Series");
        JButton parallelButton = new JButton("Parallel");

        mainPanel.add(welcomeLabel);
        mainPanel.add(seriesButton);
        mainPanel.add(parallelButton);

        seriesButton.setActionCommand("series");
        seriesButton.addActionListener(this);
        parallelButton.setActionCommand("parallel");
        parallelButton.addActionListener(this);

        panelMap.put("main", mainPanel);

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


    // MODIFIES: this
    // EFFECTS: Sets up a panel for the series circuit
    public void setupSeries() {
        JPanel seriesPanel = new JPanel();
        seriesPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel seriesText = new JLabel("You have selected a series Circuit \n");
        JLabel voltageText = new JLabel("Select your voltage \n");
        seriesVoltageTextField = new JTextField(20);
        JButton voltageSetter = new JButton("Set Voltage");
        voltageSetter.setBounds(40,40,50,50);
        seriesPanel.add(seriesText);
        seriesPanel.add(voltageText);
        seriesPanel.add(voltageSetter);
        seriesPanel.add(seriesVoltageTextField);
        voltageSetter.setActionCommand("series voltage");
        voltageSetter.addActionListener(this);

        panelMap.put("series", seriesPanel);

    }


    // MODIFIES: this
    // EFFECTS: Sets up a panel for the parallel circuit
    public void setupParallel() {
        JPanel parallelPanel = new JPanel();
        parallelPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel parallelText = new JLabel("You have selected a parallel Circuit \n");
        JLabel voltageText = new JLabel("Select your voltage \n");
        parallelVoltageTextField = new JTextField(20);
        JButton voltageSetter = new JButton("Set Voltage");
        parallelPanel.add(parallelText);
        parallelPanel.add(voltageText);
        parallelPanel.add(parallelVoltageTextField);
        parallelPanel.add(voltageSetter);
        voltageSetter.setActionCommand("parallel voltage");
        voltageSetter.addActionListener(this);

        panelMap.put("parallel", parallelPanel);


    }

    public void initSeries(double voltage) {
        myCircuit = new Circuit(true, voltage);
    }

    public void initParallel(double voltage) {
        myCircuit = new Circuit(false, voltage);
    }


    // MODIFIES: this
    // EFFECTS: Activates a panel, meaning that the panel which is called from
    // the hashmap appears on the frame
    public void activatePanel(String activatePanel) {
        for (Map.Entry mapElement : panelMap.entrySet()) {
            panelMap.get(mapElement.getKey()).setVisible(false);
            contentPane.remove(panelMap.get(mapElement.getKey()));
        }
        panelMap.get(activatePanel).setVisible(true);
        contentPane.add(panelMap.get(activatePanel), BorderLayout.CENTER);
    }


    // MODIFIES: this
    // EFFECTS: Refreshes a panel given its key
    public void update(String key) {
        panelMap.remove(key);
        switch (key) {
            case "delete":
                deleteCircuit();
            case "load":
                load();
        }
    }




    // EFFECTS: Reacts to button being pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("series")) {
            activatePanel("series");
        } else if (e.getActionCommand().equals("parallel")) {
            activatePanel("parallel");
        } else if (e.getActionCommand().equals("series voltage")) {
            seriesInit();
        } else if (e.getActionCommand().equals("parallel voltage")) {
            parallelInit();
        }
        processNextAction(e);
        processActionButton(e);
    }

    // MODIFIES: this
    // EFFECTS: sets the voltage to what the user inputted to
    public void setVoltage(JTextField textField) {
        double value;
        try {
            value = Double.parseDouble(textField.getText());
            this.voltage = value;
        } catch (NumberFormatException n) {
            System.out.println("Invalid entry");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the index to what the user inputted to
    public void setIndex(JTextField textField) {
        int index;
        try {
            index = Integer.parseInt(textField.getText());
            this.index =  index;
        } catch (NumberFormatException n) {
            System.out.println("Invalid entry");
        } catch (NullPointerException e) {
            System.out.println("Invalid entry");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the series circuit with user voltage
    public void seriesInit() {
        setVoltage(seriesVoltageTextField);
        initSeries(voltage);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: initializes the parallel circuit with user voltage
    public void parallelInit() {
        setVoltage(parallelVoltageTextField);
        initParallel(voltage);
        activatePanel("button");
    }


    // EFFECTS: Responds to menu buttons
    public void processNextAction(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            activatePanel("add");
        } else if (e.getActionCommand().equals("remove")) {
            activatePanel("remove");
        } else if (e.getActionCommand().equals("change")) {
            activatePanel("change");
        } else if (e.getActionCommand().equals("view")) {
            view();
            activatePanel("view");
        }  else if  (e.getActionCommand().equals("save")) {
            circuitList.add(myCircuit);
            saveCircuits();
            activatePanel("save");
        } else if (e.getActionCommand().equals("delete")) {
            activatePanel("delete");
        } else if (e.getActionCommand().equals("load")) {
            activatePanel("load");
        } else if (e.getActionCommand().equals("new")) {
            activatePanel("main");
        }
    }

    // EFFECTS: Responds to action buttons
    public void processActionButton(ActionEvent e) {
        if (e.getActionCommand().equals("load circuit")) {
            actionLoad();
        } else if (e.getActionCommand().equals("close")) {
            activatePanel("button");
        } else if (e.getActionCommand().equals("delete circuit")) {
            actionDelete();
        } else if (e.getActionCommand().equals("view chart")) {
            viewChart();
        } else if (e.getActionCommand().equals("view stats")) {
            stats();
            activatePanel("stats");
        }
        addActionButton(e);
        removeActionButton(e);
        changeActionButton(e);
    }

    // EFFECTS: Responds to add buttons
    public void addActionButton(ActionEvent e) {
        if (e.getActionCommand().equals("add resistor")) {
            askForResistorResistance();
            activatePanel("ask resistor resistance");
        } else if (e.getActionCommand().equals("set resistor resistance")) {
            addResistor();
        } else if (e.getActionCommand().equals("add lightbulb")) {
            askForLightbulbResistance();
            activatePanel("ask lightbulb resistance");
        } else if (e.getActionCommand().equals("set lightbulb resistance")) {
            addLightbulb();
        } else if (e.getActionCommand().equals("add ampmeter")) {
            myCircuit.addAmpmeter();
            activatePanel("button");
        } else if (e.getActionCommand().equals(("add voltmeter"))) {
            myCircuit.addVoltmeter();
            activatePanel("button");
        }

    }

    // EFFECTS: Responds to remove buttons
    public void removeActionButton(ActionEvent e) {
        if (e.getActionCommand().equals("remove resistor")) {
            askResistor();
            activatePanel("ask resistor");
        } else if (e.getActionCommand().equals("set resistor")) {
            removeResistor();
        } else if (e.getActionCommand().equals("remove lightbulb")) {
            askLightbulb();
            activatePanel("ask lightbulb");
        } else if (e.getActionCommand().equals("set lightbulb")) {
            removeLightbulb();
        } else if (e.getActionCommand().equals("remove ampmeter")) {
            askAmpmeter();
            activatePanel("ask ampmeter");
        } else if (e.getActionCommand().equals(("set ampmeter"))) {
            removeAmpmeter();
        } else if (e.getActionCommand().equals("remove voltmeter")) {
            askVoltmeter();
            activatePanel("ask voltmeter");
        } else if (e.getActionCommand().equals("set voltmeter")) {
            removeVoltmeter();
        }

    }

    // EFFECTS: Responds to change buttons
    public void changeActionButton(ActionEvent e) {
        if (e.getActionCommand().equals("change resistor")) {
            changeResistor();
            activatePanel("ask resistor change");
        } else if (e.getActionCommand().equals("set resistor change")) {
            resistorChange();
        } else if (e.getActionCommand().equals("change lightbulb")) {
            changeLightbulb();
            activatePanel("ask lightbulb change");
        } else if (e.getActionCommand().equals("set lightbulb change")) {
            lightbulbChange();
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets up the load panel
    public void load() {
        JPanel loadPanel = new JPanel();
        loadPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel noCircuits = new JLabel("You don't have any circuits to load \n Try saving some circuits first");
        JLabel circuitLabel = new JLabel("You have " + circuitList.size()
                + " Circuits. \n" + " Enter which circuit number you would like to load");
        circuitLoadIndexTextField = new JTextField(10);
        JButton loadButton = new JButton("Load");
        JButton closeButton = new JButton("Close");
        if (circuitList.size() == 0) {
            loadPanel.add(noCircuits);
            closeButton.addActionListener(this);
            closeButton.setActionCommand("close");
            loadPanel.add(closeButton);
        } else {
            loadPanel.add(circuitLabel);
            loadPanel.add(circuitLoadIndexTextField);
            loadPanel.add(loadButton);
            loadButton.setActionCommand("load circuit");
            loadButton.addActionListener(this);
        }
        panelMap.put("load", loadPanel);
    }


    // MODIFIES: this
    // EFFECT: declares myCircuit to which ever circuit the user chose
    public void actionLoad() {
        setIndex(circuitLoadIndexTextField);
        if (index > circuitList.size()) {
            JLabel failed = new JLabel("Need to have sufficient amount of circuits");
            panelMap.get("load").add(failed);
        } else {
            myCircuit = circuitList.get(index - 1);
        }
        activatePanel("button");
        update("load");

    }


    // MODDIFIES: this
    // EFFECTS: sets up the delete panel
    private void deleteCircuit() {
        JPanel deletePanel = new JPanel();
        deletePanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel noCircuits = new JLabel("You don't have any circuits to remove \n Try saving some circuits first");
        JLabel circuitLabel = new JLabel("You have " + circuitList.size()
                + " Circuits. \n" + " Enter which circuit number you would like to remove");
        circuitDeleteIndexTextField = new JTextField(10);
        JButton deleteButton = new JButton("Delete");
        if (circuitList.size() == 0) {
            deletePanel.add(noCircuits);
        } else {
            deletePanel.add(circuitLabel);
            deletePanel.add(circuitDeleteIndexTextField);
            deletePanel.add(deleteButton);
            deleteButton.setActionCommand("delete circuit");
            deleteButton.addActionListener(this);
        }
        panelMap.put("delete", deletePanel);
    }

    // MODIFIES: this
    // EFFECT: removes the circuit whichever the user chose
    public void actionDelete() {
        setIndex(circuitDeleteIndexTextField);
        if (index > circuitList.size()) {
            JLabel failed = new JLabel("Need to have sufficient amount of circuits");
            panelMap.get("delete").add(failed);
        } else {
            circuitList.remove(index - 1);
        }
        activatePanel("button");
        update("delete");
    }

    // MODIFIES: this
    // EFFECTS: Sets up the view panel
    private void view() {
        JPanel viewPanel = new JPanel();
        viewPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel resistorLabel = new JLabel("You have " + myCircuit.getResistorCounter() + " resistor(s) "
                + " Chart colour: Black");
        JLabel lightbulbLabel = new JLabel("You have " + myCircuit.getLightbulbCounter() + " lightbulb(s) "
                + "Chart colour: Red");
        JLabel ampmeterLabel = new JLabel("You have " + myCircuit.getAmpmeterCounter() + " ampmeter(s) "
                + " Chart colour: Green");
        JLabel voltmeterLabel = new JLabel("You have " + myCircuit.getVoltmeterCounter() + " voltmeter(s) "
                + " Chart colour: Yellow");
        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("close");
        closeButton.addActionListener(this);
        viewPanel.add(resistorLabel);
        viewPanel.add(lightbulbLabel);
        viewPanel.add(ampmeterLabel);
        viewPanel.add(voltmeterLabel);
        viewPanel.add(closeButton);
        viewStats(viewPanel);
        panelMap.put("view", viewPanel);
    }


    // EFFECTS: Creates a new frame with a pie chart indicating how many components comprise the circuit
    private void viewChart() {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new CircuitChart(myCircuit));
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates Buttons on the viewPanel to view the chart and the statistics
    private void viewStats(JPanel viewPanel) {
        JButton viewChartButton = new JButton("View Chart");
        viewChartButton.setActionCommand("view chart");
        viewChartButton.addActionListener(this);
        JButton viewStatsButton = new JButton("View Statistics");
        viewStatsButton.setActionCommand("view stats");
        viewStatsButton.addActionListener(this);
        viewPanel.add(viewStatsButton);
        viewPanel.add(viewChartButton);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the statistics panel
    private void stats() {
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        viewResistors(statsPanel);
        viewLightbulbs(statsPanel);
        viewVoltmeters(statsPanel);
        viewAmpmeters(statsPanel);
        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("close");
        closeButton.addActionListener(this);
        statsPanel.add(closeButton);
        panelMap.put("stats", statsPanel);
    }

    // MODIFIES: this
    // EFFECTS: Adds labels to each resistor indicating its power and its resistance
    public void viewResistors(JPanel statsPanel) {
        int count = 1;
        for (int i = 0; i < myCircuit.getResistorCounter(); i++) {
            JLabel resistorLabel = new JLabel("Resistor " + count  + " has a resistance of "
                    + myCircuit.getResistor(i).getResistance() + " and has a power of "
                    + myCircuit.getResistor(i).powerCalculation(myCircuit)
                    + " Watts.");
            statsPanel.add(resistorLabel);
            count++;
        }
    }

    // EFFECTS: Displays all lightbulbs, their values, their powers and their brightness level
    // with a description
    public void viewLightbulbs(JPanel statsPanel) {
        int count = 1;
        for (int i = 0; i < myCircuit.getLightbulbCounter(); i++) {
            JLabel lightbulbLabel = new JLabel("Lightbulb " + count + " has a resistance of "
                    + myCircuit.getLightbulb(i).getResistance()
                    + " has a power of " + myCircuit.getLightbulb(i).powerCalculation(myCircuit) + " Watts,");
            statsPanel.add(lightbulbLabel);
            lightbulbLabel(statsPanel, i);
            count++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds description of Lightbulb status
    public void lightbulbLabel(JPanel statsPanel, int i) {
        if (myCircuit.getLightbulb(i).getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 0) {
            JLabel offLabel = new JLabel("and is also off.");
            statsPanel.add(offLabel);
        } else if (myCircuit.getLightbulb(i)
                .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 1) {
            JLabel dimLabel = new JLabel("and is also dim.");
            statsPanel.add(dimLabel);
        } else if (myCircuit.getLightbulb(i)
                .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 2) {
            JLabel brightLabel = new JLabel("and is also bright.");
            statsPanel.add(brightLabel);
        } else if (myCircuit.getLightbulb(i)
                .getBrightness(myCircuit.getLightbulb(i).powerCalculation(myCircuit)) == 3) {
            JLabel fireLabel = new JLabel("and is also on fire!");
            statsPanel.add(fireLabel);
        }
    }

    // EFFECTS: Displays all ampmeters, and their corresponding current value
    public void viewAmpmeters(JPanel statsPanel) {
        int count = 1;
        for (int i = 0; i < myCircuit.getAmpmeterCounter(); i++) {
            JLabel ampmeterLabel = new JLabel("Ampmeter " + count + " reads a value of "
                    + myCircuit.getAmpmeter(i).meterCalculation(myCircuit, i) + " Amps.");
            statsPanel.add(ampmeterLabel);
            count++;
        }

    }

    // EFFECTS: Displays all voltmeters, and their corresponding voltage value
    public void viewVoltmeters(JPanel statsPanel) {
        int count = 1;
        for (int i = 0; i < myCircuit.getVoltmeterCounter(); i++) {
            JLabel voltmeterLabel = new JLabel("Voltmeter " + count + " reads a value of "
                    + myCircuit.getVoltmeter(i).meterCalculation(myCircuit, i) + " Volts.");
            statsPanel.add(voltmeterLabel);
            count++;
        }
    }


    // MODIFIES: this
    // EFFECTS: sets up change panel
    private void change() {
        JPanel changePanel = new JPanel();
        changePanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel changeComponent = new JLabel("Select which component you want to change");
        JButton resistorButton = new JButton("Resistor");
        JButton lightbulbButton = new JButton("Lightbulb");
        resistorButton.setActionCommand("change resistor");
        resistorButton.addActionListener(this);

        lightbulbButton.setActionCommand("change lightbulb");
        lightbulbButton.addActionListener(this);

        changePanel.add(changeComponent);
        changePanel.add(resistorButton);
        changePanel.add(lightbulbButton);

        panelMap.put("change", changePanel);
    }

    // MODIFIES: this
    // EFFECTS: Changes resistance of chosen resistor
    private void resistorChange() {
        setIndex(resistorTextField);
        setResistance(resistanceTextField);
        myCircuit.changeResistance(index, resistance);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: Changes resistance of chosen lightbulb
    private void lightbulbChange() {
        setIndex(lightbulbTextField);
        setResistance(resistanceTextField);
        myCircuit.changeResistanceLightbulb(index, resistance);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: sets up change resistor panel to input index and resistance of new resistor
    private void changeResistor() {
        JPanel askResistorPanel = new JPanel();
        JLabel askResistor = new JLabel("You have " + myCircuit.getResistorCounter()
                + " resistor(s). Choose one to change, and then choose the corresponding Resistance");
        resistorTextField = new JTextField(10);
        resistanceTextField = new JTextField(20);
        JButton selectResistor = new JButton("Select Resistor");
        selectResistor.setActionCommand("set resistor change");
        selectResistor.addActionListener(this);

        askResistorPanel.add(askResistor);
        askResistorPanel.add(resistorTextField);
        askResistorPanel.add(resistanceTextField);
        askResistorPanel.add(selectResistor);

        panelMap.put("ask resistor change", askResistorPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up change lightbulb panel to input index and resistance of new lightbulb
    private void changeLightbulb() {
        JPanel askLightbulbPanel = new JPanel();
        JLabel askLightbulb = new JLabel("You have " + myCircuit.getLightbulbCounter()
                + " lightbulb(s). Choose one to change, and then choose the corresponding Resistance");
        lightbulbTextField = new JTextField(10);
        resistanceTextField = new JTextField(20);
        JButton selectLightbulb = new JButton("Select Lighbulb");
        selectLightbulb.setActionCommand("set lightbulb change");
        selectLightbulb.addActionListener(this);

        askLightbulbPanel.add(askLightbulb);
        askLightbulbPanel.add(lightbulbTextField);
        askLightbulbPanel.add(resistanceTextField);
        askLightbulbPanel.add(selectLightbulb);

        panelMap.put("ask lightbulb change", askLightbulbPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up the remove panel
    private void remove() {
        JPanel removePanel = new JPanel();
        removePanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel removeComponent = new JLabel("Select which component you want to remove");
        JButton resistorButton = new JButton("Resistor");
        JButton lightbulbButton = new JButton("Lightbulb");
        JButton ampmeterButton = new JButton("Ampmeter");
        JButton voltmeterButton = new JButton("voltmeter");
        resistorButton.setActionCommand("remove resistor");
        resistorButton.addActionListener(this);

        lightbulbButton.setActionCommand("remove lightbulb");
        lightbulbButton.addActionListener(this);

        ampmeterButton.setActionCommand("remove ampmeter");
        ampmeterButton.addActionListener(this);

        voltmeterButton.setActionCommand("remove voltmeter");
        voltmeterButton.addActionListener(this);

        removePanel.add(removeComponent);
        removePanel.add(resistorButton);
        removePanel.add(lightbulbButton);
        removePanel.add(ampmeterButton);
        removePanel.add(voltmeterButton);

        panelMap.put("remove", removePanel);
    }

    // MODIFIES: this
    // EFFECTS: removes a resistor from the circuit chosen by the user
    private void removeResistor() {
        setIndex(resistorTextField);
        myCircuit.removeResistor(index);
        activatePanel("button");

    }

    // MODIFIES: this
    // EFFECTS: removes a lightbulb from the circuit chosen by the user
    private void removeLightbulb() {
        setIndex(lightbulbTextField);
        myCircuit.removeLightbulb(index);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: removes am ampmeter from the circuit chosen by the user
    private void removeAmpmeter() {
        setIndex(ampmeterTextField);
        myCircuit.removeAmpmeter(index);
        activatePanel("button");

    }

    // MODIFIES: this
    // EFFECTS: removes a voltmeter from the circuit chosen by the user
    private void removeVoltmeter() {
        setIndex(voltmeterTextField);
        myCircuit.removeVoltmeter(index);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to remove a resistor from the circuit chosen by the user
    private void askResistor() {
        JPanel askResistorPanel = new JPanel();
        JLabel askResistor = new JLabel("You have " + myCircuit.getResistorCounter()
                + " resistors. Choose one to remove");
        resistorTextField = new JTextField(10);
        JButton selectResistor = new JButton("Select Resistor");
        selectResistor.setActionCommand("set resistor");
        selectResistor.addActionListener(this);

        askResistorPanel.add(askResistor);
        askResistorPanel.add(resistorTextField);
        askResistorPanel.add(selectResistor);

        panelMap.put("ask resistor", askResistorPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to ask the user which lightbulb to remove
    private void askLightbulb() {
        JPanel askLightbulbPanel = new JPanel();
        JLabel askLighbulb = new JLabel("You have " + myCircuit.getLightbulbCounter()
                + " lightbulbs. Choose one to remove");
        lightbulbTextField = new JTextField(10);
        JButton selectLighbulb = new JButton("Select Lightbulb");
        selectLighbulb.setActionCommand("set lightbulb");
        selectLighbulb.addActionListener(this);

        askLightbulbPanel.add(askLighbulb);
        askLightbulbPanel.add(lightbulbTextField);
        askLightbulbPanel.add(selectLighbulb);

        panelMap.put("ask lightbulb", askLightbulbPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to ask the user which voltmeter to remove
    private void askVoltmeter() {
        JPanel askVoltmeterPanel = new JPanel();
        JLabel askVoltmeter = new JLabel("You have " + myCircuit.getVoltmeterCounter()
                + " voltmeters. Choose one to remove");
        voltmeterTextField = new JTextField(10);
        JButton selectVoltmeter = new JButton("Select Voltmeter");
        selectVoltmeter.setActionCommand("set voltmeter");
        selectVoltmeter.addActionListener(this);

        askVoltmeterPanel.add(askVoltmeter);
        askVoltmeterPanel.add(voltmeterTextField);
        askVoltmeterPanel.add(selectVoltmeter);

        panelMap.put("ask voltmeter", askVoltmeterPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to ask the user which ampmeter to remove
    private void askAmpmeter() {
        JPanel askAmpmeterPanel = new JPanel();
        JLabel askAmpmeter = new JLabel("You have " + myCircuit.getAmpmeterCounter()
                + " ampmeter. Choose one to remove");
        ampmeterTextField = new JTextField(10);
        JButton selectAmpmeter = new JButton("Select Ampmeter");
        selectAmpmeter.setActionCommand("set ampmeter");
        selectAmpmeter.addActionListener(this);

        askAmpmeterPanel.add(askAmpmeter);
        askAmpmeterPanel.add(ampmeterTextField);
        askAmpmeterPanel.add(selectAmpmeter);

        panelMap.put("ask ampmeter", askAmpmeterPanel);
    }


    // MODIFIES: this
    // EFFECTS: sets up add panel and adds its corresponding buttons to add components
    private void add() {
        JPanel addPanel = new JPanel();
        addPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        JLabel addComponent = new JLabel("Select which component you want to add");
        JButton resistorButton = new JButton("Resistor");
        JButton lightbulbButton = new JButton("Lightbulb");
        JButton ampmeterButton = new JButton("Ampmeter");
        JButton voltmeterButton = new JButton("Voltmeter");
        resistorButton.setActionCommand("add resistor");
        resistorButton.addActionListener(this);

        lightbulbButton.setActionCommand("add lightbulb");
        lightbulbButton.addActionListener(this);

        ampmeterButton.setActionCommand("add ampmeter");
        ampmeterButton.addActionListener(this);

        voltmeterButton.setActionCommand("add voltmeter");
        voltmeterButton.addActionListener(this);

        addPanel.add(addComponent);
        addPanel.add(resistorButton);
        addPanel.add(lightbulbButton);
        addPanel.add(ampmeterButton);
        addPanel.add(voltmeterButton);

        panelMap.put("add", addPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to add a resistor from the circuit chosen by the user
    public void addResistor() {
        setResistance(resistanceTextField);
        myCircuit.addResistor(resistance);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to add a lightbulb from the circuit chosen by the user
    public void addLightbulb() {
        setResistance(resistanceTextField);
        myCircuit.addLightbulb(resistance);
        activatePanel("button");
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to ask for resistance for a resistor
    public void askForResistorResistance() {
        JPanel addMenuPanel = new JPanel();
        JLabel resistanceQuestion = new JLabel("What Resistance would you like?");
        resistanceTextField = new JTextField(20);
        JButton setButton = new JButton("Set Resistance");
        setButton.setActionCommand("set resistor resistance");
        setButton.addActionListener(this);

        addMenuPanel.add(resistanceQuestion);
        addMenuPanel.add(resistanceTextField);
        addMenuPanel.add(setButton);

        panelMap.put("ask resistor resistance", addMenuPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel to ask for resistance for a lightbulb
    public void askForLightbulbResistance() {
        JPanel addMenuPanelLightbulb = new JPanel();
        JLabel resistanceQuestion = new JLabel("What Resistance would you like?");
        resistanceTextField = new JTextField(20);
        JButton setButton = new JButton("Set Resistance");
        setButton.setActionCommand("set lightbulb resistance");
        setButton.addActionListener(this);

        addMenuPanelLightbulb.add(resistanceQuestion);
        addMenuPanelLightbulb.add(resistanceTextField);
        addMenuPanelLightbulb.add(setButton);

        panelMap.put("ask lightbulb resistance", addMenuPanelLightbulb);
    }

    // MODIFIES: this
    // EFFECTS: sets the resistance by taking in a textfield input from the user
    public void setResistance(JTextField textField) {
        double value;
        try {
            value = Double.parseDouble(textField.getText());
            this.resistance = value;
        } catch (NumberFormatException n) {
            System.out.println("Invalid entry");
        }
    }

    // MODIFIES: this
    // EFFECTS: writes the data of the circuit list to a file.
    public void saveCircuits() {
        JPanel savePanel = new JPanel();
        savePanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        try {
            FileWriter fileWriter = new FileWriter(new File(FILE_NAME));
            fileWriter.write(circuitList);
            fileWriter.close();
            JLabel savedText = new JLabel("Circuits saved to file " + FILE_NAME);
            JButton closeSaveFrame = new JButton("Close");
            closeSaveFrame.setActionCommand("close");
            closeSaveFrame.addActionListener(this);
            savePanel.add(savedText);
            savePanel.add(closeSaveFrame);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save circuits to " + FILE_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
        panelMap.put("save", savePanel);
        update("load");
        update("delete");
    }


    // MODIFIES: this
    // EFFECTS: Sets up panel for next action including, change, reomve add, save and load, and view
    public void nextAction() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        setButtonList();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonPanel.add(buttonList.get(i));
            buttonList.get(i).addActionListener(this);
        }
        buttonList.get(0).setActionCommand("add");
        buttonList.get(1).setActionCommand("remove");
        buttonList.get(2).setActionCommand("change");
        buttonList.get(3).setActionCommand("view");
        buttonList.get(4).setActionCommand("save");
        buttonList.get(5).setActionCommand("delete");
        buttonList.get(6).setActionCommand("load");
        buttonList.get(7).setActionCommand("new");

        panelMap.put("button", buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up all the buttons to a list
    public void setButtonList() {
        JButton addButton = new JButton("Add a component");
        JButton removeButton = new JButton("Remove a component");
        JButton changeButton = new JButton("Change a component");
        JButton viewButton = new JButton("View a circuit");
        JButton saveButton = new JButton("Save your circuits");
        JButton deleteButton = new JButton("Delete a circuit");
        JButton loadButton = new JButton("load a circuit");
        JButton backButton = new JButton("New Circuit");
        buttonList.add(addButton);
        buttonList.add(removeButton);
        buttonList.add(changeButton);
        buttonList.add(viewButton);
        buttonList.add(saveButton);
        buttonList.add(deleteButton);
        buttonList.add(loadButton);
        buttonList.add(backButton);
    }

}
