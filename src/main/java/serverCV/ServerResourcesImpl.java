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
        Statement stmt = connection.createStatement();

        ResultSet result = stmt.executeQuery(query);
        boolean eRegistrato = false;
        if(result.next())
            eRegistrato = true;

        if(eRegistrato) {
            String query1 = "SELECT * FROM cittadinivaccinati " +
                    "JOIN utenti ON cittadinivaccinati.userid = utenti.userid " +
                    "WHERE utenti.userid = '" + userid + "'";
            Statement st = connection.createStatement();
            ResultSet r = st.executeQuery(query1);

            boolean eCittadino = false;
            out.println("true");

            if (r.next()) {
                // Operatore? > No
                out.println("false");
                eCittadino = true;

            } else
                // Operatore? > Si
                out.println("true");

            out.println(result.getString("nome"));
            out.println(result.getString("cognome"));
            out.println(result.getString("codicefiscale"));
            out.println(result.getString("userid"));
            out.println(result.getString("pass"));

            if(eCittadino)
                out.println(r.getString("email"));

            if(eCittadino)
                out.println(r.getString("idvacc"));
        }
        else
            out.println("false");
    }


    public void close(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void filtra() throws IOException, SQLException {

    }


    public void getSintomi() throws IOException, SQLException {
        String query= in.readLine();
        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        try {
            while (resultSet.next()) {
                out.println(resultSet.getString("sintomo"));
                out.println(resultSet.getInt("idevento"));
                out.println(resultSet.getString("descrizione"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void insertDb() throws IOException, SQLException {
        String query = in.readLine();
        Statement stmt = connection.createStatement();
        try {
            stmt.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void populateCentriVaccinali() throws IOException, SQLException {
        String nomeCentro = in.readLine();
        String createTableQuery= in.readLine();

        Statement stmt = connection.createStatement();
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            // check if "vaccinati_nomecentro" table exist
            ResultSet tables = dbm.getTables(null, null, "vaccinati_" +nomeCentro, null);
            if (!tables.next()) {
                // Table does no exists
                stmt.executeUpdate(createTableQuery);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getSingleValues() throws IOException, SQLException {
        String query= in.readLine();
        String columnLabel = in.readLine();
        Statement stmt= connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        try {
            while (rs.next()) {
                out.println(rs.getString(columnLabel));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void getVaccinati() throws IOException, SQLException {
        String query = in.readLine();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        try {
            while (rs.next()) {
                out.println(rs.getString("nomecittadino"));
                out.println(rs.getString("cognomecittadino"));
                out.println(rs.getString("codicefiscale"));
                out.println(rs.getString("vaccino"));
                out.println(rs.getString("idvacc"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void filter() throws IOException, SQLException {
        String query= in.readLine();
        Statement stmt= connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        try {
            while (rs.next()) {
                //Mando in output al Proxy.filter i campi per CentroVaccinale
                out.println(rs.getString("nome"));
                out.println(rs.getString("tipologia"));
                out.println(rs.getString("qualificatore"));
                out.println(rs.getString("strada"));
                out.println(rs.getString("civico"));
                out.println(rs.getString("comune"));
                out.println(rs.getString("provincia"));
                out.println(rs.getString("cap"));
            }
            out.println("exit");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void getSegnalazione() throws IOException, SQLException {
        String query = in.readLine();
        Statement stmt= connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        try {
            while (rs.next()) {
                out.println(rs.getString("centrovaccinale"));
                out.println(rs.getString("userid"));
                out.println(rs.getString("sintomo"));
                out.println(rs.getString("severita"));
                out.println(rs.getString("descrizione"));
            }
            out.println("exit");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


}
