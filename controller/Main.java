package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Job;
import model.Step;
import model.utils.Data;

import java.util.ArrayList;

public class Main extends Application {
    public static int n = 0; // Number of jobs
    public static int m = 0; // Number of machines

    public static ArrayList<Job> jobs = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);

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

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("JSSP");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }
}
