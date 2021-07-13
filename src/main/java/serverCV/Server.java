package serverCV;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Semaphore;

import serverCV.ServerConnection;

public class Server {
    public static void main(String[] args) {
        try{
            Semaphore sem= new Semaphore(100);
            ServerSocket server = new ServerSocket(ServerInfo.getPORT());

            System.out.println("Server Ready");
            try {
                System.out.println("Started " + server);

                while(true) {
                    Socket socket = server.accept();

                    new ServerConnection(socket, sem, ServerInfo.getPGUSERNAME(), ServerInfo.getPGPASSWORD());
                }
            } catch(Exception e)  {
                e.printStackTrace();
            }

        }catch (Exception e){
            System.out.println("Server err: " + e.getMessage());
        }
    }
}
