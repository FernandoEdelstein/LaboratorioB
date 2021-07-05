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
import serverCV.Proxy;
import serverCV.ServerInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;

public class LogInAdapter extends Adapter{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Utente utente;

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

    public void vaiARegistra(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        cambiaSchermataConUtente("RegistraCittadino.fxml", null, event);
    }

    public void logInOspite(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        cambiaSchermataConUtente("HomeCittadini.fxml", null, event);
    }

    public void controllaLogIn(ActionEvent event) throws IOException, SQLException {

        if(!tryConnection())
            return;

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            mostraWarning("Campi vuoti", "Inserisci username e password validi per accedere");
            return;
        }


        Proxy proxy = new Proxy();
        String query = "select * from utentiregistrati where userid = '" + username+ "'and pword = '" + password +"'";
        utente = proxy.login(query, username);

        if(utente == null) {
            mostraWarning("Utente inesistente", "Username e Password non corrispondono a nessun utente\nregistrato");
        } else {
            if(utente instanceof Cittadino) {
                cambiaSchermataConUtente("HomeCittadino.fxml", utente, event);
            }
            else {
                cambiaSchermataConUtente("HomeOperatore.fxml", utente, event);
            }
        }
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public boolean tryConnection() throws IOException, SQLException {
        boolean connected;

        connected = pingHost(ServerInfo.getIPSERVER(), ServerInfo.getPORT());
        System.out.println(connected);
        if (!connected) {
            vaiAImpostazioni();
            return false;
        }

        Check util = new Check();
        util.populateDatabase();

        return true;

    }

    private static boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 1500);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
