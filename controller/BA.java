package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;
import model.Job;

import java.util.ArrayList;

public class BA implements Algorithm {
    private int numberOfSouts;
    private int numberOfEliteSites;
    private int numberOfBestSites;
    private int recruitedEliteSites;
    private int recruitedRemainingBestSites;
    private int neighbourhoodSize;
    private double siteAbandonement;

    private Chromosome[] population = new Chromosome[1];
    private Gantt bestSolution;
    @Override
    public void run() {
        population[0] = new Chromosome(Main.n,Main.n);

        population[0].generateChromosome();

        Gantt rnd = new Gantt();
        rnd.generateFromChromosome(population[0]);
        bestSolution = rnd;
    }

    public void setJobs(ArrayList<Job> jobs) {
        Main.jobs = jobs;
    }

    public Gantt getBestSolution(){
        return bestSolution;
    }
}
