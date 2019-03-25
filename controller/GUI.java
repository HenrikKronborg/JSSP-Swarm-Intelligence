package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.Gantt;
import model.Job;
import model.Node;
import model.utils.Data;
import view.GanttChart;

import java.io.File;
import java.net.URL;
import java.util.*;

public class GUI implements Initializable {
    @FXML
    private GanttChart<Number,String> chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private CategoryAxis yAxis;
    @FXML
    private ChoiceBox cBox;
    @FXML
    private Label fit;

    private HashMap<Integer,String> colorMap;
    Random r = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initChoiceBox();
        initChart();
    }

    private void initChoiceBox() {
        File folder = new File("./src/test_data/");
        File[] listOfFiles = folder.listFiles();

        ObservableList<String> objects = FXCollections.observableArrayList();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].getName().charAt(0) !='.'){
                    objects.add(listOfFiles[i].getName());
                }
            }
        }
        cBox.setItems(objects.sorted());
        cBox.setValue(cBox.getItems().get(0));
    }
    private void initColor() {
        colorMap = new HashMap<>();
        ArrayList<Integer> randomColors = new ArrayList<>(25);
        for(int i = 0; i < 25; i++) {
            randomColors.add(i+1);
        }
        Collections.shuffle(randomColors);

        int counter = 0;
        for(Job j : Main.jobs) {
            if(counter == 25) {
                System.out.println("No more colors... reset");
                counter = 0;
            }
            colorMap.put(j.getjobNumber(), "status-" + randomColors.get(counter++));
        }
    }
    private void initChart() {
        String[] machines = new String[] { "Machine 1", "Machine 2", "Machine 3" };

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));

        chart.setTitle("Job schedule");
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);
        chart.setAnimated(false);
        chart.getStylesheets().add(getClass().getResource("../view/ganttchart.css").toExternalForm());
    }

    @FXML
    private void onCBChange(){
        Data.ReadData((String) cBox.getValue());
        initColor();
    }

    @FXML
    public void run() {
        BA b = new BA();
        b.run();

        Gantt best = b.getBestSolution();

        drawBest(best);

        fit.setText("Fitness: "+ best.getFitness());
        System.out.println("Gantt created: " + best.getFitness());
    }

    private void drawBest(Gantt best) {
        chart.getData().clear();

        // Update Y axis block
        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

        ArrayList<String> machines = new ArrayList<>();

        for(int i = 0; i < best.getMachineTimeline().size(); i++) {
            machines.add("Machine " + (i+1));
        }
        yAxis.getCategories().clear();
        yAxis.setCategories(FXCollections.<String>observableArrayList(machines));

        // Update Y axis block ended.

        for(int i = 0; i < best.getMachineTimeline().size(); i++) {
            XYChart.Series series = new XYChart.Series();
            for(int j = 0; j < best.getMachineTimeline().get(i).size(); j++) {
                Node currNode = best.getMachineTimeline().get(i).get(j);
                series.getData().add(new XYChart.Data(currNode.getStartTime(), "Machine " + (i+1), new GanttChart.ExtraData(currNode.getProcessingTime(), colorMap.get(currNode.getJobNumber()), currNode)));
            }
            chart.getData().add(series);
        }
    }

}
