import Server.Server;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
            Server server = new Server();
            registry.bind("serverBriscola", server);
            System.out.println("I'm waiting for someone...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }


    }

}
