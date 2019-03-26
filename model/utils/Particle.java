package model.utils;

import controller.Main;
import model.Chromosome;
import model.Gantt;

public class Particle {
    private Chromosome x; // Current chromosome
    private Chromosome p; // Personal best chromosome

    private double[][] v; // Velocity

    private int currentFitness;
    private int bestFitness;

    public Particle() {
        // Randomly initiate the current chromosome
        x = new Chromosome();
        x.generateChromosome();

        Gantt gantt = new Gantt();
        gantt.generatePhenoType(x);

        x.setFitness(gantt.getFitness());

        // Calculate current chromosomes fitness
        currentFitness = x.getFitness();
        bestFitness = currentFitness;
    }

    public void updateVelocity(Chromosome globalBest) {
        if(v == null){
            v = new double[Main.m][Main.n];
        }
    }

    public void updatePosition(Chromosome globalBest) {
        for (int i = 0; i < x.getWeights().length; i++) {
            for (int j = 0; j < x.getWeights()[i].length;j++) {

            }
        }
    }

    public Chromosome getX() {
        return x;
    }

    public void setX(Chromosome x) {
        this.x = x;
    }

    public Chromosome getP() {
        return p;
    }

    public void setP(Chromosome p) {
        this.p = p;
    }

    public double[][] getV() {
        return v;
    }

    public void setV(double[][] v) {
        this.v = v;
    }

    public int getCurrentFitness() {
        return currentFitness;
    }

    public void setCurrentFitness(int currentFitness) {
        this.currentFitness = currentFitness;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }
}
