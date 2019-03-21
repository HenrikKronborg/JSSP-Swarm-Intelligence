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
import view.GanttChart;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    @FXML
    private GanttChart<Number,String> chart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;

    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] machines = new String[] { "Machine 1", "Machine 2", "Machine 3" };

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));

        chart.setTitle("Machine Monitoring");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        String machine;

        machine = machines[0];
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(0, machine, new GanttChart.ExtraData( 1, "status-red")));
        series1.getData().add(new XYChart.Data(1, machine, new GanttChart.ExtraData( 1, "status-green")));
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


    @FXML
    public void run(){
        JSSP j = new JSSP();
        BA b = new BA();
        b.setJobs(Main.jobs);
        j.setAlgorithm(b);

        j.run();

    }
}
