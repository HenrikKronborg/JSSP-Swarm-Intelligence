package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();

        gc.fillRect(0,0,100,100);
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
