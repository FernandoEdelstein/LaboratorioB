package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.cittadini.Cittadino;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import clientCV.Proxy;
import serverCV.ServerInfo;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;

/**
 * LogInAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class LogInAdapter extends Adapter {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Utente utente;

    /**
     * Vai alla schermata Impostazioni
     *
     * @throws IOException
     */
    public void vaiAImpostazioni() throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(CentriVaccinali.class.getClassLoader().getResource(
                        "Layout/Connessione.fxml")));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Impostazioni connessione");
        stage.show();
    }

    /**
     * Vai alla schermata Registrati
     *
     * @param event
     * @throws IOException
     */
    public void vaiARegistra(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        cambiaSchermataConUtente("RegistraCittadino.fxml", null, event);
    }

    /**
     * Vai alla schermata HomeCittadini come ospite
     *
     * @param event
     * @throws IOException
     */
    public void logInOspite(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        cambiaSchermataConUtente("HomeCittadini.fxml", null, event);
    }

    /**
     * Controlla il collegamento col proxy ed esegue il log in basandosi sul tipo di utente
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void controllaLogIn(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            mostraWarning("Campi vuoti", "Inserire username e password per accedere");
            return;
        }

        Proxy proxy = new Proxy();
        String query = "SELECT * " +
                "FROM utenti " +
                "WHERE userid = '" + username+
                "'AND pass = '" + password +"'";
        utente = proxy.login(query, username);

        if(utente == null) {

            mostraWarning("Utente non trovato", "Username e Password non corrispondono a nessun utente");
        } else {
            if(utente instanceof Cittadino) {
                cambiaSchermataConUtente("HomeCittadini.fxml", utente, event);

            }
            else {
                cambiaSchermataConUtente("HomeCentri.fxml", utente, event);

            }
        }
    }

    /**
     * Imposta l'utente corrente
     *
     * @param utente
     */
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * Controlla la connessione
     *
     * @return boolean
     * @throws IOException
     * @throws SQLException
     */
    public boolean tryConnection() throws IOException, SQLException {
        boolean connected;

        connected = pingHost(ServerInfo.getIPSERVER(), ServerInfo.getPORT());
        if (!connected) {
            vaiAImpostazioni();
            return false;
        }

        Check check = new Check();

            //Se non ci sono valori sul database, allora riempi i database con i dati di default
        check.databaseVuoto();

        return true;
    }

    /**
     * Ping Host
     *
     * @param host
     * @param port
     * @return boolean
     */
    private static boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);

                return true;
        } catch (IOException e) {
            e.printStackTrace();
                return false;
        }
    }

}
