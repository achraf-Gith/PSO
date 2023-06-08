package ma.enset.bddc.SMA;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CoordinatorAgent extends Agent {
    private int[] cities;
    private int numVehicles;
    private int[] vehicleCapacities;
    private int[][] distanceMatrix;

    protected void setup() {
        // Agent initialization code
        Object[] args = getArguments();
        if (args != null && args.length > 2) {
            cities = (int[]) args[0];
            numVehicles = Integer.parseInt(args[1].toString());
            vehicleCapacities = (int[]) args[2];
            distanceMatrix = (int[][]) args[3];

            addBehaviour(new VRPBehaviour());
        } else {
            System.out.println("Invalid arguments for CoordinatorAgent");
            doDelete();
        }
    }

    private class VRPBehaviour extends SequentialBehaviour {
        public void onStart() {
            // Create and initialize particles
            // (Implement particle initialization strategy here)

            // Perform PSO iterations
            // (Implement PSO algorithm here)

            // Send best solution to vehicles
            int[] bestSolution = new int[] {1, 3, 4, 2}; // Example best solution
            for (int i = 0; i < numVehicles; i++) {
                int vehicleID = i + 1;
                String destination = Integer.toString(bestSolution[i]);

                // Create a message for each vehicle
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(getAID("Vehicle" + vehicleID));
                msg.setContent(destination);
                send(msg);
            }
        }

        public int onEnd() {
            // Terminate the program
            System.out.println("CoordinatorAgent terminating.");
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
