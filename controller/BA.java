package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;

import java.util.PriorityQueue;

public class BA implements Algorithm {
    private int numberOfScouts = 100;
    private int numberOfBestSites = 10;
    private int numberOfEliteSites = 2;

    private int recruitedEliteSites = 40;
    private int recruitedRemainingBestSites = 20;
    private double neighbourhoodSize = 0.5;
    private int siteAbandonment = 5;


    private PriorityQueue<Chromosome> population = new PriorityQueue<>();
    private Gantt bestSolution;

    @Override
    public void run() {
        for(int i = 0; i < numberOfScouts; i++) {
            Chromosome c =  new Chromosome(Main.n, Main.n);
            c.generateChromosome();
            Gantt gantt = new Gantt();
            gantt.generatePhenoType(c);

            c.setFitness(gantt.getFitness());
            if(bestSolution == null){
                bestSolution = gantt;
            } else if( gantt.getFitness() < bestSolution.getFitness()){
                bestSolution = gantt;
            }
        }

    }

    public Gantt getBestSolution(){
        return bestSolution;
    }
}
