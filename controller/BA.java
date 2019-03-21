package controller;

import model.Algorithm;
import model.Chromosom;
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


    public Chromosom[] population = new Chromosom[1];

    @Override
    public void run() {
        population[0] = new Chromosom(Main.n,Main.n);

        Gantt rnd = new Gantt();
        rnd.generateFromChromosom(population[0]);


    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
}
