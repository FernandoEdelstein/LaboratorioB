package clientCV.centriVaccinali.adapters;

import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.io.IOException;

public class HomeCittadiniAdapter extends Adapter {

    @FXML
    private Text benvenutoText;
    @FXML
    private Button logoutBtn, registratiBtn, cercaBtn;

    private Utente utente;

    public void vaiACercaScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    public void vaiARegistratiScene(ActionEvent event) throws IOException {
        cambiaSchermata("RegistraCittadino.fxml", event);
    }

    public void vaiALogIn(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        if (utente == null) {
            benvenutoText.setText("Accesso come ospite");
            registratiBtn.setDisable(false);
            logoutBtn.setText("Accedi");
            logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        vaiALogIn(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            benvenutoText.setText("Ciao, " + utente.getUsername());
            registratiBtn.setDisable(true);
            logoutBtn.setText("Logout");
        }

    }
}
