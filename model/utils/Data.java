package model.utils;

import controller.Main;
import model.Job;
import model.Step;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    public static void ReadData(String filename) {
        String line;
        int lineCounter = 0;
        Main.jobs.clear();

        try {
            FileReader fileReader = new FileReader("./src/test_data/" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                // Remove double space
                line = line.trim().replaceAll("\\s+", " ");

                if(!line.isEmpty()) {
                    // Split on space
                    String[] splittedLine = line.split(" ");

                    // Convert splitted line to integers
                    int[] intLine = new int[splittedLine.length];
                    for (int i = 0; i < splittedLine.length; i++) {
                        intLine[i] = Integer.parseInt(splittedLine[i]);
                    }

                    // Read number of jobs and machines
                    if (lineCounter == 0) {
                        Main.n = intLine[0];
                        Main.m = intLine[1];
                    }
                    // Read data about a job, creating every step and saving the Job to an ArrayList
                    else {
                        Job job = new Job(lineCounter - 1);

                        for (int i = 0; i < intLine.length; i += 2) {
                            Step step = new Step(intLine[i], intLine[i + 1]);
                            job.addStep(step);
                        }

                        Main.jobs.add(job);
                    }
                }

                lineCounter++;
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Couldn't find file");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
