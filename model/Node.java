package model;

public class Node {
    private int machineNumber;
    private int jobNumber;
    private int processingTime;
    private boolean visited;

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
}
