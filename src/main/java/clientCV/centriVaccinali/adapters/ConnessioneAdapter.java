package clientCV.centriVaccinali.adapters;

import clientCV.shared.ServerInfo;
import clientCV.shared.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnessioneAdapter extends Adapter{

    @FXML
    private TextField portField, ipField;
    @FXML
    private Label infoLabel;

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

    private static boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void setUtente(Utente utente) { }

    public void setDefault() {
        ipField.setText("localhost");
        portField.setText("7070");
    }
}
