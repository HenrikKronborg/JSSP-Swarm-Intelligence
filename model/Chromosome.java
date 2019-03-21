package model;

import java.util.ArrayList;

public class Chromosome {
    double[][] weights;

    public Chromosome(int jobs, int steps){
        weights = new double[jobs][steps];
    }

    public void generateRnd(){
        for (int i = 0; i < weights.length; i++){
            for (int j=0; j<weights.length;j++){
                weights[i][j] = Math.random();
            }
        }
    }


}
