package serverCV;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

public class ServerConnection extends Thread{

    private Semaphore sem;
    private Socket socket = new Socket("localhost", 5432);
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String username, password;

    public ServerConnection(Socket socket, Semaphore sem, String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        this.socket=socket;
        this.sem=sem;

        start();
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
            Connection c = DriverManager.getConnection(
                    "jdbc:postgresql://" +
                            ServerInfo.getIPSERVER() +
                            ":" +5432+ "/" +
                            ServerInfo.getDBNAME(),
                            username,
                            password);
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                    serveClient(in, out, c);
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

    private void serveClient(BufferedReader in, PrintWriter out, Connection c) throws IOException, SQLException {
        String operation;
        ServerResourcesImpl dBhelper = new ServerResourcesImpl(in, out, c);

        while((operation = in.readLine())!= null) {

            switch (operation) {
                case "insertDb" : dBhelper.insertDb();
                    break;
                case "populateCentriVaccinali" : dBhelper.populateCentriVaccinali();
                    break;
                case "filter" : dBhelper.filter();
                    break;
                case "searchSintomi" : dBhelper.getSintomi();
                    break;
                case "getSingleValues" : dBhelper.getSingleValues();
                    break;
                case "login" : dBhelper.login();
                    break;
                case "getSegnalazione" : dBhelper.getSegnalazione();
                    break;
                case "getVaccinati" : dBhelper.getVaccinati();
                    break;
                default: break;
            }
            dBhelper.close(socket);
        }
        sem.release();
    }

    }


