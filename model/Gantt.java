package model;

import controller.Main;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Gantt {
    private ArrayList<ArrayList<Node>> machinTimeline;
    private int fitness;

    public void generateFromChromosome(Chromosome c){
        machinTimeline = new ArrayList<>(Main.m);
        ArrayList<Integer> jobTimeLine = new ArrayList<>(Main.n);

        for(int i=0; i< Main.m; i++){
            machinTimeline.add(new ArrayList<>());
        }
        for(int i=0; i< Main.n; i++){
            jobTimeLine.add(0);
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
            int mintime = 0;
            if(jobTimeLine.size() != 0)
                mintime = jobTimeLine.get(n.getJobNumber());
            if(machinTimeline.get(n.getMachineNumber()).size() != 0){
                if(machinTimeline.get(n.getMachineNumber()).get(machinTimeline.get(n.getMachineNumber()).size()-1).getEndTime() > mintime){
                    mintime = machinTimeline.get(n.getMachineNumber()).get(machinTimeline.get(n.getMachineNumber()).size()-1).getEndTime();
                }
            }
            n.setStartTime(mintime); // Does set both start and end time.
            machinTimeline.get(n.getMachineNumber()).add(n);
            jobTimeLine.set(n.getJobNumber(),n.getEndTime());

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
        for (ArrayList<Node> nodes : machinTimeline){
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

    public ArrayList<ArrayList<Node>> getMachinTimeline() {
        return machinTimeline;
    }
}
