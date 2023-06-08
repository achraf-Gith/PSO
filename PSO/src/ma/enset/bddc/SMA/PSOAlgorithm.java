package ma.enset.bddc.SMA;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import ma.enset.bddc.SMA.Swarm;

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


public class PSOAlgorithm extends Agent {
    private Swarm swarm;
    private int numIterations;
    private boolean isFinished;

    protected void setup() {
        numIterations = 100; // Number of iterations
        isFinished = false;

        swarm = new Swarm(50);
        swarm.addBehaviour(new PSOBehaviour());
    }

    private class PSOBehaviour extends Behaviour {
        private int currentIteration;

        public void action() {
            if (currentIteration < numIterations) {
                swarm.update();
                currentIteration++;
            } else {
                isFinished = true;
            }
        }

        public boolean done() {
            return isFinished;
        }
    }
}
