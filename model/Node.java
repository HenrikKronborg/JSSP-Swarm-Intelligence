package model;

public class Node {
    private int machineNumber;
    private int jobNumber;
    private int processingTime;
    private boolean visited;

    private int startTime;
    private int endTime;

    private double weight;


    public Node(int machineNumber, int jobNumber, int processingTime) {
        this.machineNumber = machineNumber;
        this.jobNumber = jobNumber;
        this.processingTime = processingTime;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
