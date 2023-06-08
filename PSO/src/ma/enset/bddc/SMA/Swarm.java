package ma.enset.bddc.SMA;

import jade.core.behaviours.Behaviour;

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

public class Swarm extends Agent {
    private ParticleAgent[] particles;
    private double[] globalBestPosition;

    public Swarm(int numParticles) {
        particles = new ParticleAgent[numParticles];
        globalBestPosition = new double[2]; // Two-dimensional global best position

        for (int i = 0; i < numParticles; i++) {
            particles[i] = new ParticleAgent();
            particles[i].addBehaviour(new UpdateBehaviour());
        }
    }

    private class UpdateBehaviour extends Behaviour {
        public void action() {
            // Update each particle
            for (ParticleAgent particle : particles) {
                particle.update();
            }

            // Update global best position
            // ...
        }

        public boolean done() {
            // Check termination condition
            // ...
        }
    }
}
