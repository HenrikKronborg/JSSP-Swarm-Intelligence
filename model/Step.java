package model;

public class Step {
    private int machineNumber;
    private int processingTime;

    public Step(int machineNumber, int processingTime) {
        this.machineNumber = machineNumber;
        this.processingTime = processingTime;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
}
