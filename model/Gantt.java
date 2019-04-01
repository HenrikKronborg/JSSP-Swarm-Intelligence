package model;

import controller.Main;

import java.util.ArrayList;
import java.util.PriorityQueue;

// The phenotype
public class Gantt {
    private ArrayList<ArrayList<Node>> machineTimeline;
    private int fitness;
    private final double PARALLEL_LIMIT = 0.05;

    public void generatePhenoType(Chromosome c) {
        machineTimeline = new ArrayList<>(Main.m);
        int[] jobTimeLine = new int[Main.n];

        for(int i = 0; i < Main.m; i++) {
            machineTimeline.add(new ArrayList<>());
        }

        PriorityQueue<Node> place = new PriorityQueue<>();
        ArrayList<Node> toBePlaced = new ArrayList<>();

        // Create the nodes
        for(int i = 0; i < Main.jobs.size(); i++) {
            for(int j = 0; j < Main.jobs.get(i).getSteps().size(); j++){
                Step step = Main.jobs.get(i).getSteps().get(j);
                Node n = new Node(step.getMachineNumber(), Main.jobs.get(i).getjobNumber(), step.getProcessingTime(), j);
                n.setWeight(c.getWeight(n.getJobNumber(), j));

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

            if(node.getWeight() > PARALLEL_LIMIT) {
                for(Node n : place) {
                    int time = jobTimeLine[n.getJobNumber()];

                    if(machineTimeline.get(n.getMachineNumber()).size() != 0) {
                        int endTimeOfLastNodeInMachine = machineTimeline.get(n.getMachineNumber()).get(machineTimeline.get(n.getMachineNumber()).size()-1).getEndTime();
                        if(endTimeOfLastNodeInMachine > time) {
                            time = endTimeOfLastNodeInMachine;
                        }
                    }
                    if(time < minTime) {
                        minTime = time;
                        node = n;
                    }

                    //System.out.println((node.getStepNumber()+1) + "/" + (node.getJobNumber()+1));
                }
                //System.out.println("---------------");
            } else {
                minTime = jobTimeLine[node.getJobNumber()];

                if(machineTimeline.get(node.getMachineNumber()).size() != 0) {
                    int endTimeOfLastNodeInMachine = machineTimeline.get(node.getMachineNumber()).get(machineTimeline.get(node.getMachineNumber()).size()-1).getEndTime();
                    if(endTimeOfLastNodeInMachine > minTime) {
                        minTime = endTimeOfLastNodeInMachine;
                    }
                }
            }

            place.remove(node);
            node.setStartTime(minTime); // Sets both start and end time.
            machineTimeline.get(node.getMachineNumber()).add(node);
            jobTimeLine[node.getJobNumber()] = node.getEndTime();

            // Find the next steps in the job sequence and add them in the queue to be placed
            for(int i = 0; i < toBePlaced.size(); i++) {
                Node other = toBePlaced.get(i);
                if(other.getJobNumber() == node.getJobNumber()) {
                    if(other.getStepNumber() - 1 == node.getStepNumber()) {
                        place.add(other);
                        toBePlaced.remove(i);
                        break;
                    }
                }
            }
        }

        // Calculate fitness.
        int minTime = 0;
        for(ArrayList<Node> nodes : machineTimeline) {
            for(Node node : nodes) {
                if(minTime < node.getEndTime()) {
                    minTime = node.getEndTime();
                }
            }
        }
        fitness = minTime;
    }

    public int getFitness() {
        return fitness;
    }

    public ArrayList<ArrayList<Node>> getMachineTimeline() {
        return machineTimeline;
    }
}
