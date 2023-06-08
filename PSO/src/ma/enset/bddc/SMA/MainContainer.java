package ma.enset.bddc.SMA;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainContainer {
    public static void main(String[] args) {
        try {
            // Get the JADE runtime instance
            Runtime runtime = Runtime.instance();

            // Create a main container
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.GUI, "true");

            ContainerController mainContainer = runtime.createMainContainer(profile);

            // Create a dedicated container for the coordinator agent
            AgentContainer coordinatorContainer = runtime.createAgentContainer(profile);

            // Create the coordinator agent
            Object[] coordinatorArgs = new Object[] {
                    new int[] {1, 2, 3, 4}, // Example city coordinates
                    3, // Number of vehicles
                    new int[] {100, 150, 200}, // Vehicle capacities
                    new int[][] {{0, 5, 8, 6}, {5, 0, 10, 7}, {8, 10, 0, 9}, {6, 7, 9, 0}} // Distance matrix
            };
            AgentController coordinatorController = coordinatorContainer.createNewAgent("CoordinatorAgent", CoordinatorAgent.class.getName(), coordinatorArgs);
            coordinatorController.start();

            // Create a dedicated container for the vehicle agents
            AgentContainer vehicleContainer = runtime.createAgentContainer(profile);

            // Create the vehicle agents
            for (int i = 0; i < 3; i++) {
                Object[] vehicleArgs = new Object[] {
                        i + 1, // Vehicle ID
                        coordinatorArgs[2] // Vehicle capacity
                };
                AgentController vehicleController = vehicleContainer.createNewAgent("Vehicle" + (i + 1), VehicleAgent.class.getName(), vehicleArgs);
                vehicleController.start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
