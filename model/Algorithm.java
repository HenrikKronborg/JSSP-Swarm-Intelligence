package model;

public interface Algorithm {
    int generations = 80;

    void run();

    Gantt getBestSolution();
}
