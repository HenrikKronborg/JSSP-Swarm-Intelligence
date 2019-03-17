package model;

import java.util.ArrayList;

public class Node {
    private int machineNumber;
    private int jobNumber;
    private int processingTime;
    private Node neighbors;
    private ArrayList<Double> pheromones;
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

    public Node getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Node neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<Double> getPheromones() {
        return pheromones;
    }

    public void addPheromone(double pheromone) {
        this.pheromones.add(pheromone);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
