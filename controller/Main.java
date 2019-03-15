package controller;

import model.Job;
import model.Step;
import model.utils.Data;

import java.util.ArrayList;

public class Main {
    public static int n = 0; // Number of jobs
    public static int m = 0; // Number of machines

    public static ArrayList<Job> jobs = new ArrayList<>();

    public static void main(String[] args) {
        Data data = new Data();
        data.ReadData("./src/test_data/1.txt");

        /*
        // Only used to confirm that jobs with steps work
        for(Job j : jobs) {
            for(Step s : j.getSteps()) {
                System.out.println(s.getMachineNumber());
                System.out.println(s.getProcessingTime());
                System.out.println();
            }
            System.out.println("##############################################");
        }
        */
    }
}
