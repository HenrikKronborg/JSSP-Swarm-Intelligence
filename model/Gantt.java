package model;

import controller.Main;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Gantt {
    private ArrayList<ArrayList<Node>> machineTimeline;
    private int fitness;

    public void generateFromChromosome(Chromosome c){
        machineTimeline = new ArrayList<>(Main.m);
        int[] jobTimeLine = new int[Main.n];

        for(int i=0; i< Main.m; i++){
            machineTimeline.add(new ArrayList<>());
        }

        PriorityQueue<Node> place = new PriorityQueue<>();
        ArrayList<Node> toBePlaced = new ArrayList<>();

        for(int i=0;i<Main.jobs.size();i++){
            for(int j=0;j<Main.jobs.get(i).getSteps().size();j++){
                Step step = Main.jobs.get(i).getSteps().get(j);
                Node n = new Node(step.getMachineNumber(),Main.jobs.get(i).getjobNumber(),step.getProcessingTime(),j);
                n.setWeight(c.getW(n.getJobNumber(),j));

                if(j == 0){
                    place.add(n);
                }else{
                    toBePlaced.add(n);
                }
            }
        }

        while (place.size() + toBePlaced.size() != 0 ){
            Node n = place.poll();
            int mintime = jobTimeLine[n.getJobNumber()];

            if(machineTimeline.get(n.getMachineNumber()).size() != 0){
                if(machineTimeline.get(n.getMachineNumber()).get(machineTimeline.get(n.getMachineNumber()).size()-1).getEndTime() > mintime){
                    mintime = machineTimeline.get(n.getMachineNumber()).get(machineTimeline.get(n.getMachineNumber()).size()-1).getEndTime();
                }
            }
            n.setStartTime(mintime); // Does set both start and end time.
            machineTimeline.get(n.getMachineNumber()).add(n);
            jobTimeLine[n.getJobNumber()] = n.getEndTime();

            for(int i =0; i < toBePlaced.size(); i++){
                Node other =  toBePlaced.get(i);
                if(other.getJobNumber() == n.getJobNumber()){
                    if(other.getStepNr() - 1 == n.getStepNr()){
                        place.add(other);
                        toBePlaced.remove(i);
                        break;
                    }
                }
            }
        }

        // Calculate fitness.
        int maxTime = 0;
        for (ArrayList<Node> nodes : machineTimeline){
            for (Node node : nodes){
                if(maxTime < node.getEndTime()){
                    maxTime = node.getEndTime();
                }
            }
        }
        fitness = maxTime;
        System.out.println("Gantt created: "+maxTime);
    }

    public int getFitness() {
        return fitness;
    }

    public ArrayList<ArrayList<Node>> getMachineTimeline() {
        return machineTimeline;
    }
}
