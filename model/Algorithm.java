package model;

public interface Algorithm {
    int generations = 300;

    void run();

    Gantt getBestSolution();
}
