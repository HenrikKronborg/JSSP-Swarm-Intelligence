package model;

import controller.Main;

import java.util.ArrayList;

public class Gantt {
    TimeLine[] machins;

    public void generateFromChromosom(Chromosom c){
        machins = new TimeLine[Main.m];


        ArrayList<Node> canBePlaced = new ArrayList<>();
        ArrayList<Node> toBePlaced = new ArrayList<>();

        for(int i=0;i<Main.jobs.size();i++){
            for(int j=0;j<Main.jobs.get(i).getSteps().size();j++){
                Step step = Main.jobs.get(i).getSteps().get(j);
                Node n = new Node(step.getMachineNumber(),Main.jobs.get(i).getjobNumber(),step.getProcessingTime());
                if(j == 0){
                    canBePlaced.add(n);
                }else{
                    toBePlaced.add(n);
                }
            }
        }

        while (canBePlaced.size() + toBePlaced.size() != 0 ){

        }

    }
}
