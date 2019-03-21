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
    private static ArrayList<Job> jobs = new ArrayList<>(); // List of all jobs with steps


    public Chromosome[] population = new Chromosome[1];

    @Override
    public void run() {
        population[0] = new Chromosome(Main.n,Main.n);

        Gantt rnd = new Gantt();
        rnd.generateFromChromosome(population[0]);


    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
}
