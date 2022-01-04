package model;

public abstract class ResistorComponent {
    public double resistance;
    public double current;
    public  double power;


    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }

    public double powerCalculation(Circuit myCircuit) {
        if (myCircuit.getSeriesOrParallel()) {
            current = myCircuit.getVoltage() / myCircuit.getTotalResistance();
        } else {
            current = myCircuit.getVoltage() / resistance;
        }
        this.power = current * current * resistance;
        return power;
    }

}
