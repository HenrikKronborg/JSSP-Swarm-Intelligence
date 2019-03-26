package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;
import model.utils.Particle;

public class PSO implements Algorithm {

    private int swarmSize = 10; // Should be between 10-50
    private double neighbourhoodSize = 3; // Should be either 3 or 5
    private double importanceOfPersonalBest = 2;        // C1
    private double importanceOfNeighbourhoodBest = 2;   //C2
    public static final double maxVelocity = 0.05;

    private Particle[] population = new Particle[swarmSize];
    private Chromosome globalBest;
    private int globalBestValue = Integer.MAX_VALUE;

    @Override
    public void run() {
        //Create population and update global fitness, local fittnes done inside Particle class.
        for(int i = 0; i < swarmSize; i++) {
            population[i] = new Particle();

            updateGlobal(population[i]);
        }

        for(int gen = 0; gen < generations; gen++){

            //update position and velocity
            for(Particle p : population) {
                p.updateVelocity(globalBest,importanceOfPersonalBest, importanceOfNeighbourhoodBest, maxVelocity);
                p.updatePosition();
            }

            // Update Global if necessary
            for(Particle p : population) {
                updateGlobal(p);
            }

        }
    }

    private void updateGlobal(Particle indv){
        if(indv.getBestFitness() < globalBestValue){
            globalBest = indv.getP();
            globalBestValue = indv.getBestFitness();
        }
    }

    @Override
    public Gantt getBestSolution() {
        if(globalBest == null){
            return null;
        }

        Gantt best = new Gantt();
        best.generatePhenoType(globalBest);

        return best;
    }
}
