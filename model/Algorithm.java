package model;

public interface Algorithm {
    int generations = 150;

    void run();

    Gantt getBestSolution();
}
