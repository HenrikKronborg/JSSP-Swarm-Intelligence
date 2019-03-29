package model;

public class Node implements Comparable<Node>{
    private int machineNumber;
    private int jobNumber;
    private int processingTime;
    private int stepNumber;

    private int startTime;
    private int endTime;

    private double weight;


    public Node(int machineNumber, int jobNumber, int processingTime, int stepNumber) {
        this.machineNumber = machineNumber;
        this.jobNumber = jobNumber;
        this.processingTime = processingTime;
        this.stepNumber = stepNumber;
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
        this.endTime = startTime + processingTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    @Override
    public int compareTo(Node o) {
        double sum = this.weight - o.weight;
        if(sum > 0){
            return 1;
        }else if(sum == 0){
            return 0;
        }
        return -1;
    }
}
