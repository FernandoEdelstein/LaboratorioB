package serverCV;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * Server Class
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */

public class Server {
    /**
     * First Runnable Program
     * @param args
     */

    public static void main(String[] args) {
        try{
            Semaphore sem= new Semaphore(100);
            ServerSocket server = new ServerSocket(ServerInfo.getPORT());

            Scanner scan = new Scanner(System.in);

            System.out.println("DB Username: ");
                ServerInfo.setPGUSERNAME(scan.nextLine());
            System.out.println("DB Password: ");
                ServerInfo.setPGPASSWORD(scan.nextLine());

            while(!tryConnection(ServerInfo.getPGUSERNAME(),
                    ServerInfo.getPGPASSWORD(),
                    ServerInfo.getDBNAME(),
                    ServerInfo.getIPSERVER(),
                    ServerInfo.getDBPORT())){
                System.out.println("Errore nel inserimento di credenziali, riprova!");

                System.out.println("DB Username: ");
                    ServerInfo.setPGUSERNAME(scan.nextLine());
                System.out.println("DB Password: ");
                    ServerInfo.setPGPASSWORD(scan.nextLine());
            }

            System.out.println("Connesso!");

            try {
                System.out.println("Server: " + server);

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

    public static Boolean tryConnection(String username, String password, String database, String ipAddress, int port) throws ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        try{
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql://" + ipAddress + ":" +
                            port + "/" +
                            database +
                            "?&useUnicode=true&characterEncoding=utf8",
                            username,
                            password);
        }catch (SQLException e){
            return false;
        }
        return true;
    }
}
