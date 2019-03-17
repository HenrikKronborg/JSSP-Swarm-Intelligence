package controller;

public class ACO {
    // https://pdfs.semanticscholar.org/4f18/1229caa22a7fa589b6871083c5ca3679307f.pdf
    // 271911163_Ant_Colony_Optimization_for_Job_Shop_Scheduling_to_Minimize_the_Total_Weighted_Tardiness
    private int numberOfAnts = 10;
    private int iterations = 100;
    private double alpha = 1; // Pheromone importance
    private double beta = 5; // Distance priority. Should always be larger than alpha
    private double p;

    public ACO() {
        Solve();
    }

    private void Solve() {
        for(int i = 0; i < iterations; i++) {

        }
    }
}
