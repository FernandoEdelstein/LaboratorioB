package serverCV;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try{
            ClientResourcesImpl obj = new ClientResourcesImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CentriVaccinali" , obj);

            System.out.println("Server Ready");

        }catch (Exception e){
            System.out.println("TTYChatServer err: " + e.getMessage());
        }
    }
}
