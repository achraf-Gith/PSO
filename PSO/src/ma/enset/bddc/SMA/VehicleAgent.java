package ma.enset.bddc.SMA;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VehicleAgent extends Agent {
    private int vehicleID;
    private int capacity;
    private int[] route;

    protected void setup() {
        // Agent initialization code
        Object[] args = getArguments();
        if (args != null && args.length > 1) {
            vehicleID = Integer.parseInt(args[0].toString());
            capacity = Integer.parseInt(args[1].toString());
            route = new int[0]; // Initialize empty route

            addBehaviour(new VRPBehaviour());
        } else {
            System.out.println("Invalid arguments for VehicleAgent");
            doDelete();
        }
    }

    private class VRPBehaviour extends CyclicBehaviour {
        public void action() {
            // Wait for messages from the central coordinator
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Process the received message
                ACLMessage reply = msg.createReply();

                // Perform vehicle-specific operations here
                // (e.g., update route based on received information)
                String content = msg.getContent();
                int destination = Integer.parseInt(content);

                // Update the route by adding the destination
                int[] updatedRoute = new int[route.length + 1];
                System.arraycopy(route, 0, updatedRoute, 0, route.length);
                updatedRoute[route.length] = destination;
                route = updatedRoute;

                System.out.println("Vehicle " + vehicleID + " updated route: " + java.util.Arrays.toString(route));

                // Send the reply message back to the coordinator
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(Integer.toString(vehicleID));
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
