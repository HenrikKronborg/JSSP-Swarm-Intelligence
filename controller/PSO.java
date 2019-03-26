package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;

public class PSO implements Algorithm {
    private int N = 100;  // Individuals
    private Chromosome globalBest;


    @Override
    public void run() {
        population[0] = new Chromosome(Main.n,Main.n);

        Gantt rnd = new Gantt();
        rnd.generatePhenoType(population[0]);
    }

    @Override
    public Gantt getBestSolution() {
        return null;
    }
}
