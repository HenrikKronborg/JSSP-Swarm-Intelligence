package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;

public class PSO implements Algorithm {
    public Chromosome[] population = new Chromosome[1];

    @Override
    public void run() {
        population[0] = new Chromosome(Main.n,Main.n);

        Gantt rnd = new Gantt();
        rnd.generateFromChromosome(population[0]);
    }

    @Override
    public Gantt getBestSolution() {
        return null;
    }
}
