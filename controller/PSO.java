package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;

public class PSO implements Algorithm {

    private int swarmSize = 10; // Should be between 10-50
    private double neighbourhoodSize = 3; // Should be either 3 or 5
    private double importanceOfPersonalBest = 2;        // C1
    private double importanceOfNeighbourhoodBest = 2;   //C2
    public static final double maxVelocity = 0.05;

    private Chromosome[] population = new Chromosome[swarmSize];
    private Chromosome globalBest;

    @Override
    public void run() {
        for(int i = 0; i < swarmSize; i++) {
            Chromosome c = new Chromosome(Main.n, Main.n);
            c.generateChromosome();
            Gantt gantt = new Gantt();
            gantt.generatePhenoType(c);

            c.setFitness(gantt.getFitness());
            population[i] = c;
        }
    }

    @Override
    public Gantt getBestSolution() {
        return null;
    }
}
