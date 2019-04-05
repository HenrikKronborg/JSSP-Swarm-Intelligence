package model.utils;

import controller.Main;
import model.Chromosome;
import model.Gantt;

public class Particle {
    private Chromosome x; // Current chromosome
    private Chromosome p; // Personal best chromosome

    private double[][] v = new double[Main.n][Main.m]; // Velocity

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
        p = x;
    }

    public void updateVelocity(Chromosome g, double C1, double C2, double maxVelocity) {
        double[][] R1 = new double[Main.n][Main.m];
        double[][] R2 = new double[Main.n][Main.m];

        for (int i = 0; i < R1.length; i++) {
            for (int j = 0; j < R1[0].length; j++) {
                R1[i][j] = Math.random();
                R2[i][j] = Math.random();
            }
        }

        double[][] personal = matrixOperation(C1, p.getWeights(), x.getWeights(), R1);
        double[][] global   = matrixOperation(C2, g.getWeights(), x.getWeights(), R2);

        v = sumMatrix(v, personal, global);

        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length;j++) {
                if(v[i][j] > maxVelocity) {
                    v[i][j] = maxVelocity;
                }
                if(v[i][j] < -maxVelocity) {
                    v[i][j] = -maxVelocity;
                }
            }
        }
    }

    public void updatePosition() {
        double[][] newChromosome = sumMatrix(x.getWeights(), v);

        x = new Chromosome(newChromosome);

        Gantt gantt = new Gantt();
        gantt.generatePhenoType(x);

        x.setFitness(gantt.getFitness());
        currentFitness = x.getFitness();

        if(currentFitness < bestFitness) {
            p = x;
            bestFitness = currentFitness;
        }
    }

    private static double[][] matrixOperation(double c, double[][] y, double[][] x, double[][] r) {
        double[][] res =  new double[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length;j++) {
                res[i][j] = c * (y[i][j] - x[i][j]) * r[i][j];
            }
        }
        return res;
    }

    private static double[][] sumMatrix(double[][] ... a){
        double[][] c =  new double[a[0].length][a[0][0].length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                for(double[][] temp : a) {
                    c[i][j] += temp[i][j];
                }
            }
        }
        return c;
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
