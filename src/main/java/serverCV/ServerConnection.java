package serverCV;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

public class ServerConnection extends Thread{

    private Semaphore sem;
    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String username, password;

    public ServerConnection(Socket socket, Semaphore semaphore, String username, String password) {
        this.socket = socket;
        sem = semaphore;
        this.username = username;
        this.password = password;

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
        try (Connection c = DriverManager.getConnection(
                "jdbc:postgresql://" + ServerInfo.getIPSERVER() +
                        ":" + ServerInfo.getDBPORT() +
                        "/" + ServerInfo.getDBNAME(),
                        username,
                        password)) {
                                try {
                                    in = new BufferedReader(
                                            new InputStreamReader(socket.getInputStream()));
                                    out = new PrintWriter(
                                            new BufferedWriter(
                                                    new OutputStreamWriter(socket.getOutputStream())), true);

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

    }

}
