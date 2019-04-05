package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;
import model.utils.Particle;

import java.util.Arrays;
import java.util.Comparator;

public class PSO implements Algorithm {

    private int swarmSize = 25; // Should be between 10-50
    private int neighbourhoodSize = 25; // Should be either 3 or 5
    private double importanceOfPersonalBest = 2;      // C1
    private double importanceOfNeighbourhoodBest = 2; // C2
    public static final double maxVelocity = 0.05;

    private Particle[] population = new Particle[swarmSize];
    private Chromosome globalBest;
    private int globalBestValue = Integer.MAX_VALUE;

    @Override
    public void run() {
        // Create population and update global fitness, local fitness done inside Particle class.
        for(int i = 0; i < swarmSize; i++) {
            population[i] = new Particle();

            updateGlobal(population[i]);
        }

        for(int gen = 0; gen < generations; gen++){

            // Update position and velocity
            for(Particle p : population) {
                //Chromosome neighbourhoodBest = getNeighbourhoodBest(p);
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

    private Chromosome getNeighbourhoodBest(Particle indv){
        double[][] particleDist = new double[population.length][2];

        for(int i=0;i < population.length;i++){
            Particle p = population[i];
            if(p.equals(indv)){
                particleDist[i][0] = 0;
            }else{
                particleDist[i][0] = euclideanDist(indv.getX(), population[i].getX());
            }
            particleDist[i][1] = i;
        }

        Arrays.sort(particleDist, Comparator.comparing((double[] arr) -> arr[0]));

        Particle neighbourhoodBest = population[(int)particleDist[0][1]];
        // Includes itself.
        for(int i = 1; i <= neighbourhoodSize && i < particleDist.length; i++){
            if(neighbourhoodBest.getBestFitness() < population[(int)particleDist[i][1]].getBestFitness()){
                neighbourhoodBest = population[(int)particleDist[i][1]];
            }
        }

        return neighbourhoodBest.getP();
    }


    private double euclideanDist(Chromosome c, Chromosome other){
        double[][] w1 =  c.getWeights();
        double[][] w2 =  other.getWeights();

        double sum = 0.0;
        for (int i = 0; i < w1.length; i++){
            for (int j= 0; j < w1[0].length; j++){
                sum += Math.pow(w1[i][j]-w2[i][j],2);
            }
        }

        return Math.sqrt(sum);
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
