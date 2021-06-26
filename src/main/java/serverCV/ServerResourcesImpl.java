package serverCV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerResourcesImpl implements ServerResources{
    private BufferedReader in;
    private PrintWriter out;
    private Connection connection;

    public ServerResourcesImpl (BufferedReader in, PrintWriter out, Connection connection) {
        this.in = in;
        this.out = out;
        this.connection = connection;
    }

    @Override
    public void login() throws IOException, SQLException {
        String query = in.readLine();
        String userid = in.readLine();
        Statement stmt = connection.createStatement();

        ResultSet result = stmt.executeQuery(query);
        boolean eRegistrato = false;
        if(result.next())
            eRegistrato = true;

        if(eRegistrato) {
            String query1 = "SELECT * FROM cittadiniregistrati " +
                    "JOIN utentiregistrati ON cittadiniregistrati.userid = utentiregistrati.userid " +
                    "WHERE utentiregistrati.userid = '" + userid + "'";
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
            out.println(result.getString("pword"));

            if(eCittadino)
                out.println(r.getString("email"));

            if(eCittadino)
                out.println(r.getString("idvacc"));
        }
        else
            out.println("false");
    }

    @Override
    public void close(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public void filtra() throws IOException, SQLException {

    }

    @Override
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

    @Override
    public void getSegnalazione() throws IOException, SQLException {

    }

    @Override
    public void getSingleValues() throws IOException, SQLException {

    }

    @Override
    public void getVaccinati() throws IOException, SQLException {

    }

    @Override
    public void inserisciDb() throws IOException, SQLException {

    }

    @Override
    public void riempiCentriVaccinali() throws IOException, SQLException {

    }
}
