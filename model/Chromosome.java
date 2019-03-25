package model;

public class Chromosome implements Comparable<Chromosome>{
    private double[][] weights;
    private int fitness;

    public Chromosome(int jobs, int steps){
        weights = new double[jobs][steps];
    }

    public void generateChromosome() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j<weights.length;j++) {
                weights[i][j] = Math.random();
            }
        }
    }

    public double getW(int jobNumber, int stepNumber) {
        return weights[jobNumber][stepNumber];
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Chromosome o) {
        double sum = this.fitness - o.fitness;
        if(sum > 0){
            return 1;
        }else if(sum == 0){
            return 0;
        }
        return -1;
    }
}
