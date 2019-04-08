package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.*;
import model.utils.Data;
import view.GanttChart;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;

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
    @FXML
    private ToggleGroup algorithmChoice;
    @FXML
    private Button hundredBtn;
    @FXML
    private Button avgBtn;

    private HashMap<Integer,String> colorMap;
    Random r = new Random();

    private final int N = 4;
    private Thread[] threads = new Thread[N];
    private CountDownLatch doneSignal = new CountDownLatch(N);

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
    private void onCBChange() {
        Data.ReadData((String) cBox.getValue());
        initColor();
    }

    @FXML
    public void run() {
        RadioButton selectedRadioButton = (RadioButton) algorithmChoice.getSelectedToggle();
        Gantt best = null;

        if(selectedRadioButton.getText().equals("BA")) {
            BA ba = new BA();
            ba.run();
            best = ba.getBestSolution();
        }
        else if(selectedRadioButton.getText().equals("PSO")) {
            PSO pso = new PSO();
            pso.run();
            best = pso.getBestSolution();
        }

        /*
        Chromosome c = new Chromosome();
        c.generateChromosome();
        Gantt gantt = new Gantt();
        gantt.generatePhenoType(c);

        best = gantt;
        */

        drawBest(best);

        fit.setText("Makespan: "+ best.getFitness());
        System.out.println(selectedRadioButton.getText() + " solution created. Makespan: " + best.getFitness());
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
                series.getData().add(new XYChart.Data(currNode.getStartTime(), "Machine " + (i+1), new GanttChart.ExtraData(currNode.getProcessingTime(), colorMap.get(currNode.getJobNumber()), currNode, best.getFitness())));
            }
            chart.getData().add(series);
        }
    }

    @FXML
    private void avgAndDev(){
        RadioButton selectedRadioButton = (RadioButton) algorithmChoice.getSelectedToggle();
        Algorithm algorithm;

        int numberOfRuns = 100;
        double sum = 0.0;
        double[] results = new double[numberOfRuns];

        for (int i=0; i<numberOfRuns;i++){
            if(selectedRadioButton.getText().equals("BA")) {
                algorithm = new BA();
            }
            else {
                algorithm = new PSO();
            }
            algorithm.run();
            results[i] = algorithm.getBestSolution().getFitness();
            sum += algorithm.getBestSolution().getFitness();
        }
        double avg = sum/numberOfRuns;
        double sd = 0.0;
        for(double x : results){
            sd += Math.pow(x-avg,2);
        }
        sd /= (numberOfRuns-1);
        sd = Math.sqrt(sd);

        System.out.println("Results whit n=" + numberOfRuns+", on: "+selectedRadioButton.getText());
        System.out.println("Average: " + avg);
        System.out.println("Standard deviation: " + sd);


    }

    @FXML
    private void bestOf100(){
        doneSignal = new CountDownLatch(N);
        avgBtn.setDisable(true);
        hundredBtn.setDisable(true);

        Thread t = new Thread(() -> {
            ArrayList<Gantt> results = runThreads();
            try {
                doneSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> printBest(results));
        });
        t.start();

    }

    private void printBest(ArrayList<Gantt> results){
        RadioButton selectedRadioButton = (RadioButton) algorithmChoice.getSelectedToggle();
        Gantt best = null;
        for (Gantt result : results) {
            if (best == null) {
                best = result;
            } else if (best.getFitness() > result.getFitness()) {
                best = result;
            }
        }
        drawBest(best);

        fit.setText("Makespan: "+ best.getFitness());
        System.out.println("Best of 100." + selectedRadioButton.getText() + " solution created. Makespan: " + best.getFitness());

        avgBtn.setDisable(false);
        hundredBtn.setDisable(false);
    }




    private ArrayList<Gantt> runThreads() {
        ArrayList<Gantt> population = new ArrayList<>();
        RadioButton selectedRadioButton = (RadioButton) algorithmChoice.getSelectedToggle();
        int toAdd = 25;

        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int added = 0;
                while(added < toAdd) {
                    Algorithm algorithm;
                    if(selectedRadioButton.getText().equals("BA")) {
                        algorithm = new BA();
                    }
                    else {
                        algorithm = new PSO();
                    }
                    algorithm.run();
                    population.add(algorithm.getBestSolution());
                    added++;
                }
                doneSignal.countDown();
            });
            threads[i].start();
        }

        return population;
    }
}
