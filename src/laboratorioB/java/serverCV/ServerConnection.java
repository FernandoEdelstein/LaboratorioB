package serverCV;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

/**
 * ServerConnection
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */

public class ServerConnection extends Thread{

    private Semaphore sem;
    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String username, password;

    /**
     * ServerConnection Constructor
     *
     * @param socket
     * @param sem
     * @param username
     * @param password
     */

    public ServerConnection(Socket socket, Semaphore sem, String username, String password) {
        this.username = username;
        this.password = password;
        this.socket=socket;
        this.sem=sem;

        start();
    }

    /**
     * Metodo fetchFromDb, si interfaccia con ServerResourcesImpl per eseguire la operazione richiesta
     *
     * @param in
     * @param out
     * @param c
     * @throws IOException
     * @throws SQLException
     */

    private void fetchFromDb(BufferedReader in, PrintWriter out, Connection c) throws IOException, SQLException {
        String richiesta;

        ServerResourcesImpl serverResources = new ServerResourcesImpl(in, out, c);

        while((richiesta = in.readLine())!= null) {

            switch (richiesta) {
                case "login" : serverResources.login();
                    break;
                case "inserireInDb" : serverResources.inserireInDb();
                    break;
                case "filtra" : serverResources.filtra();
                    break;
                case "riceviVaccinati" : serverResources.riceviVaccinati();
                    break;
                case "riceviValoriIndividuali" : serverResources.riceviValoriIndividuali();
                    break;
                case "registraNuovoCentro" : serverResources.registraNuovoCentro();
                    break;
                case "riceviSintomi" : serverResources.riceviSintomi();
                    break;
                case "riceviSegnalazione" : serverResources.riceviSegnalazione();
                    break;
                default: break;
            }
            serverResources.close(socket);
        }
        sem.release();
    }

    public void run(){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String connectionAddress = "jdbc:postgresql://" + ServerInfo.getIPSERVER()
                    + ":" + ServerInfo.getDBPORT() + "/"
                    + ServerInfo.getDBNAME();

            Connection connection = DriverManager.getConnection(connectionAddress,
                            ServerInfo.getPGUSERNAME(),
                            ServerInfo.getPGPASSWORD());

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                            fetchFromDb(in, out, connection);
                                } catch(IOException e) {
                                    if (out != null) {
                                        out.close();
                                    }
                                    if (in != null) {
                                        try {
                                            in.close();
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                }
                        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


