package model.utils;

import controller.Main;
import model.Chromosome;
import model.Gantt;

public class Particle {
    private Chromosome x; // Current chromosome
    private Chromosome p; // Personal best chromosome

    private double[][] v = new double[Main.n][Main.m];; // Velocity

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
        double[][] personal;
    }

    public void updatePosition() {
        double[][] newChromosome = new double[x.getWeights().length][x.getWeights()[0].length];
    }

    public static double[][] sumMatrix(double[][] a, double[][] b){
        if(a.length == b.length && a[0].length == b[0].length){
            double[][] c =  new double[a.length][a[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length;j++) {
                 c[i][j] = a[i][j] +b[i][j];
                }
            }
            return c;
        }

        System.out.println("Matrix error");
        return null;

    }

    public static double[][] subtractionMatrix(double[][] a, double[][] b){
        if(a.length == b.length && a[0].length == b[0].length){
            double[][] c =  new double[a.length][a[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length;j++) {
                    c[i][j] = a[i][j] - b[i][j];
                }
            }
            return c;
        }

        System.out.println("Matrix error");
        return null;

    }

    public static double[][] multiplicationMatrix(double[][] a, double[][] b){
        if(a.length == b.length && a[0].length == b[0].length){
            double[][] c =  new double[a.length][a[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length;j++) {
                    c[i][j] = a[i][j] * b[i][j];
                }
            }
            return c;
        }

        System.out.println("Matrix error");
        return null;

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
