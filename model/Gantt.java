package model;

import controller.Main;

import java.util.ArrayList;
import java.util.PriorityQueue;

// The phenotype
public class Gantt {
    private ArrayList<ArrayList<Node>> machineTimeline;
    private int fitness;
    private final double PARALLEL_LIMIT = 0.95;

    public void generatePhenoType(Chromosome c) {
        machineTimeline = new ArrayList<>(Main.m);
        int[] jobTimeLine = new int[Main.n];

        for(int i = 0; i < Main.m; i++) {
            machineTimeline.add(new ArrayList<>());
        }

        PriorityQueue<Node> place = new PriorityQueue<>();
        ArrayList<Node> toBePlaced = new ArrayList<>();

        for(int i = 0; i < Main.jobs.size(); i++) {
            for(int j = 0; j < Main.jobs.get(i).getSteps().size(); j++){
                Step step = Main.jobs.get(i).getSteps().get(j);
                Node n = new Node(step.getMachineNumber(), Main.jobs.get(i).getjobNumber(), step.getProcessingTime(), j);
                n.setWeight(c.getW(n.getJobNumber(), j));

                if(j == 0) {
                    place.add(n);
                } else {
                    toBePlaced.add(n);
                }
            }
        }

        while(place.size() + toBePlaced.size() != 0) {
            Node node = place.peek();
            int minTime = Integer.MAX_VALUE;

            if(node.getWeight() < PARALLEL_LIMIT){
                for(Node n : place) {
                    int time = jobTimeLine[n.getJobNumber()];

                    if(machineTimeline.get(n.getMachineNumber()).size() != 0) {
                        int machineNodeTime = machineTimeline.get(n.getMachineNumber()).get(machineTimeline.get(n.getMachineNumber()).size()-1).getEndTime();
                        if(machineNodeTime > time) {
                            time = machineNodeTime;
                        }
                    }
                    if(time < minTime) {
                        minTime = time;
                        node = n;
                    }
                }
            }else{
                minTime = jobTimeLine[node.getJobNumber()];

                if(machineTimeline.get(node.getMachineNumber()).size() != 0) {
                    int machineNodeTime = machineTimeline.get(node.getMachineNumber()).get(machineTimeline.get(node.getMachineNumber()).size()-1).getEndTime();
                    if(machineNodeTime > minTime) {
                        minTime = machineNodeTime;
                    }
                }
            }

            place.remove(node);
            node.setStartTime(minTime); // Sets both start and end time.
            machineTimeline.get(node.getMachineNumber()).add(node);
            jobTimeLine[node.getJobNumber()] = node.getEndTime();

            // Find the next steps and add them in the place queue
            for(int i = 0; i < toBePlaced.size(); i++) {
                Node other = toBePlaced.get(i);
                if(other.getJobNumber() == node.getJobNumber()) {
                    if(other.getStepNr() - 1 == node.getStepNr()) {
                        place.add(other);
                        toBePlaced.remove(i);
                        break;
                    }
                }
            }
        }

        // Calculate fitness.
        int maxTime = 0;
        for(ArrayList<Node> nodes : machineTimeline) {
            for(Node node : nodes) {
                if(maxTime < node.getEndTime()) {
                    maxTime = node.getEndTime();
                }
            }
        }
        fitness = maxTime;
    }

    public int getFitness() {
        return fitness;
    }

    public ArrayList<ArrayList<Node>> getMachineTimeline() {
        return machineTimeline;
    }
}
