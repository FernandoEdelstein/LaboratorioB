package serverCV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

public class ServerResourcesImpl{
    private BufferedReader in;
    private PrintWriter out;
    private Connection connection;

    public ServerResourcesImpl (BufferedReader in, PrintWriter out, Connection connection) {
        this.in = in;
        this.out = out;
        this.connection = connection;
    }


    public void login() throws IOException, SQLException {
        String query = in.readLine();
        String userid = in.readLine();

            Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);
        boolean registrato = false;

        if(resultSet.next())
            registrato = true;

        if(registrato) {
            String query1 = "SELECT * FROM cittadinivaccinati " +
                            "JOIN utenti ON cittadinivaccinati.userid = utenti.userid " +
                            "WHERE utenti.userid = '" + userid + "'";

                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(query1);

            boolean cittadino = false;
            out.println("true");

            if (resultSet1.next()) {
                // Non e operatore
                out.println("false");
                cittadino = true;
            } else {
                // Se e operatore
                out.println("true");
            }

                out.println(resultSet.getString("nome"));
                out.println(resultSet.getString("cognome"));
                out.println(resultSet.getString("codicefiscale"));
                out.println(resultSet.getString("userid"));
                out.println(resultSet.getString("pass"));

            if(cittadino)
                out.println(resultSet1.getString("email"));

            if(cittadino)
                out.println(resultSet1.getString("idvaccinazione"));
        }
        else
            out.println("false");
    }


    public void close(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
    }



    public void riceviSintomi() throws IOException, SQLException {
        String query= in.readLine();
        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        try {
            while (resultSet.next()) {
                out.println(resultSet.getString("sintomo"));
                out.println(resultSet.getInt("idsintomo"));
                out.println(resultSet.getString("descrizione"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void inserireInDb() throws IOException, SQLException {
        String query = in.readLine();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void registraNuovoCentro() throws IOException, SQLException {
        String nomeCentro = in.readLine();
        String createTableQuery= in.readLine();

        Statement statement = connection.createStatement();

        try {
            DatabaseMetaData dbm = connection.getMetaData();
            // Verifica se esiste la tabella vaccinati-"nome-centro"
            ResultSet tables = dbm.getTables(null, null, "vaccinati_" +nomeCentro, null);
            if (!tables.next()) {
                // Se la tabella non esiste
                statement.executeUpdate(createTableQuery);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void riceviValoriIndividuali() throws IOException, SQLException {
        String query= in.readLine();
        String columnLabel = in.readLine();

        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        try {
            while (resultSet.next()) {
                out.println(resultSet.getString(columnLabel));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void riceviVaccinati() throws IOException, SQLException {
        String query = in.readLine();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        try {
            while (resultSet.next()) {
                out.println(resultSet.getString("nomecittadino"));
                out.println(resultSet.getString("cognomecittadino"));
                out.println(resultSet.getString("codicefiscale"));
                out.println(resultSet.getString("vaccino"));
                out.println(resultSet.getString("idvaccinazione"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void filtra() throws IOException, SQLException {
        String query= in.readLine();
        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        try {
            while (resultSet.next()) {
                out.println(resultSet.getString("nome"));
                out.println(resultSet.getString("tipologia"));
                out.println(resultSet.getString("qualificatore"));
                out.println(resultSet.getString("strada"));
                out.println(resultSet.getString("civico"));
                out.println(resultSet.getString("comune"));
                out.println(resultSet.getString("provincia"));
                out.println(resultSet.getString("cap"));
            }
            out.println("exit");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void riceviSegnalazione() throws IOException, SQLException {
        String query = in.readLine();
        Statement statement= connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);
        try {
            while (resultSet.next()) {
                out.println(resultSet.getString("centrovaccinale"));
                out.println(resultSet.getString("userid"));
                out.println(resultSet.getString("sintomo"));
                out.println(resultSet.getString("severita"));
                out.println(resultSet.getString("descrizione"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

}
