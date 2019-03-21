package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import model.Gantt;
import model.Job;
import model.Node;
import view.GanttChart;

import java.net.URL;
import java.util.*;

public class GUI implements Initializable {

    @FXML
    private GanttChart<Number,String> chart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;

    private GraphicsContext gc;

    private HashMap<Integer,String> colorMap;
    Random r = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColor();

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
        chart.setBlockHeight( 50);
        String machine;

        machine = machines[0];
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData( 1, "status-red")));
        series1.getData().add(new XYChart.Data(2, machine, new GanttChart.ExtraData( 1, "status-red")));
        series1.getData().add(new XYChart.Data(3, machine, new GanttChart.ExtraData( 1, "status-green")));

        machine = machines[1];
        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData( 1, "status-green")));
        series2.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData( 1, "status-green")));
        series2.getData().add(new XYChart.Data(2, machine, new GanttChart.ExtraData( 2, "status-red")));

        machine = machines[2];
        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData( 1, "status-blue")));
        series3.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData( 2, "status-red")));
        series3.getData().add(new XYChart.Data(3, machine, new GanttChart.ExtraData( 1, "status-green")));

        chart.getData().addAll(series1, series2, series3);

        chart.getStylesheets().add(getClass().getResource("../view/ganttchart.css").toExternalForm());

    }

    private void initColor() {
        colorMap = new HashMap<>();
        int counter = 2;
        for(Job j : Main.jobs){
            if(counter == 26){
                System.out.println("No more colors... reset");
                counter = 2;
            }
                colorMap.put(j.getjobNumber(),"status-"+counter++);
        }

    }


    @FXML
    public void run(){
        BA b = new BA();
        b.setJobs(Main.jobs);
        b.run();
        Gantt best = b.getBestSolution();

        drawBest(best);

    }

    private void drawBest(Gantt best){
        chart.getData().clear();

        // Update Y axis block
        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

        ArrayList<String> machines = new ArrayList<>();

        for(int i = 0; i < best.getMachinTimeline().size(); i++){
                machines.add("Machine "+(i+1));
        }
        yAxis.getCategories().clear();
        yAxis.setCategories(FXCollections.<String>observableArrayList(machines));

        // Update Y axis block ended.

        for(int i = 0; i < best.getMachinTimeline().size(); i++){
            XYChart.Series series = new XYChart.Series();
            for(int j = 0; j < best.getMachinTimeline().get(i).size(); j++){
                Node currNode = best.getMachinTimeline().get(i).get(j);
                series.getData().add(new XYChart.Data(currNode.getStartTime(), "Machine "+(i+1), new GanttChart.ExtraData( currNode.getProcessingTime(), colorMap.get(currNode.getJobNumber()))));
            }
            chart.getData().add(series);
        }
        chart.getStylesheets().add(getClass().getResource("../view/ganttchart.css").toExternalForm());
    }

}
