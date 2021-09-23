package clientCV.centriVaccinali.adapters;

import clientCV.shared.ServerInfo;
import clientCV.cittadini.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ConnessioneAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class ConnessioneAdapter extends Adapter{

    @FXML
    private TextField portField, ipField;
    @FXML
    private Label infoLabel;

    /**
     * Controlla la connessione
     */
    public void connetti() {
        boolean connected;

        ServerInfo.setIPSERVER(ipField.getText());
        ServerInfo.setPORT(Integer.parseInt(portField.getText()));

        connected = pingHost(ServerInfo.getIPSERVER(), ServerInfo.getPORT());

        if (connected)
            infoLabel.setText("Connesso!");
        else
            infoLabel.setText("Non connesso, riprova");

    }

    /**
     * Ping Host
     *
     * @param host
     * @param port
     * @return
     */
    private static boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Imposta l'utente corrente
     *
     * @param utente
     */
    @Override
    public void setUtente(Utente utente) { }

    /**
     * Parametri di default
     *
     */
    public void setDefault() {
        ipField.setText("localhost");
        portField.setText("7070");
    }
}
