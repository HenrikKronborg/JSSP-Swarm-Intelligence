package model;

import controller.Main;

// The genotype
public class Chromosome implements Comparable<Chromosome>{
    // [Jobs][Step]
    private double[][] weights;
    private int fitness;

    public Chromosome() {
        // There's the same amount of steps as machines
        weights = new double[Main.n][Main.m];
    }

    public Chromosome(double[][] chrom) {
        weights = new double[Main.n][Main.m];

        for (int i = 0; i < chrom.length; i++) {
            for (int j = 0; j < chrom[i].length; j++) {
                weights[i][j] = chrom[i][j];
            }
        }
    }
    
    public void generateChromosome() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length;j++) {
                weights[i][j] = Math.random();
            }
        }
    }

    public double getWeight(int jobNumber, int stepNumber) {
        return weights[jobNumber][stepNumber];
    }

    public double[][] getWeights() { return weights; }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void generateFromSite(Chromosome site, double patchSize) {
        for (int i = 0; i < site.weights.length; i++) {
            for (int j = 0; j< site.weights[i].length;j++) {
                this.weights[i][j] = Math.abs(site.weights[i][j]+(Math.random()*patchSize*2 - patchSize) % 1);
            }
        }
    }

    @Override
    public int compareTo(Chromosome o) {
        double sum = this.fitness - o.fitness;
        if(sum > 0) {
            return 1;
        } else if(sum == 0) {
            return 0;
        }
        return -1;
    }
}
