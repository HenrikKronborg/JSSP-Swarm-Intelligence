package model;

import java.util.ArrayList;

public class Job {
    private int jobNumber;
    private ArrayList<Step> steps = new ArrayList<>();

    public Job(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getjobNumber() {
        return jobNumber;
    }

    public void setjobNumber(int machineNumber) {
        this.jobNumber = machineNumber;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void addStep(Step step) {
        this.steps.add(step);
    }
}
