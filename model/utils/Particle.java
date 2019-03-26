package model.utils;

import model.Chromosome;

public class Particle {
    Chromosome x;   // Current particle
    Chromosome p;   // Personal best

    double[][] v;     // Velocity

    int currentFitness;
    int bestFitness;


    public Particle() {

    }

    public void updatePosition(){

    }

    public void updateVelocity(){

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
