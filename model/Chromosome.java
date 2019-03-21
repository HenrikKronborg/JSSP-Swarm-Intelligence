package model;

public class Chromosome {
    double[][] weights;

    public Chromosome(int jobs, int steps){
        weights = new double[jobs][steps];
    }

    public void generateChromosome() {
        for (int i = 0; i < weights.length; i++){
            for (int j=0; j<weights.length;j++){
                weights[i][j] = Math.random();
            }
        }
    }


    public double getW(int jobNumber, int stepNumber) {
        return weights[jobNumber][stepNumber];
    }
}
