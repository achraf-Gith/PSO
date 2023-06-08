package ma.enset.bddc.SMA;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class GUI extends Application {
    private static final int NUM_PARTICLES = 50;
    private static final int NUM_ITERATIONS = 100;

    private PSOAlgorithm pso;
    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;

    public void start(Stage primaryStage) {
        pso = new PSOAlgorithm();
        pso.setup();

        // Initialize JavaFX components
        primaryStage.setTitle("PSO Visualization");
        chart = createChart();
        Button startButton = new Button("Start");
        startButton.setOnAction(event -> startPSO());

        BorderPane root = new BorderPane();
        root.setCenter(chart);
        root.setBottom(startButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private LineChart<Number, Number> createChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Iteration");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Best Fitness");

        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("PSO Algorithm");

        series = new XYChart.Series<>();
        series.setName("Best Fitness");
        chart.getData().add(series);

        return chart;
    }

    private void startPSO() {
        pso.addBehaviour(new GUIBehaviour());
    }

    private class GUIBehaviour extends OneShotBehaviour {
        public void action() {
            int currentIteration = 0;

            while (currentIteration < NUM_ITERATIONS) {
                Platform.runLater(() -> {
                    // Update chart with best fitness value
                    series.getData().add(new XYChart.Data<>(currentIteration, pso.getBestFitness()));
                });

                // Sleep for a short interval
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentIteration++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}