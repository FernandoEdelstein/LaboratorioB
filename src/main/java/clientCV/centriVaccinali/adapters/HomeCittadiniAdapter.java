package clientCV.centriVaccinali.adapters;

import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class HomeCittadiniAdapter extends Adapter {

    @FXML
    private Text welcomeTextField;
    @FXML
    private Button btnLogout, btnRegistrati;

    private Utente utente;

    public void vaiACercaScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    public void vaiARegistratiScene(ActionEvent event) throws IOException {
        cambiaSchermata("RegistraCittadino.fxml", event);
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        if (utente == null) {
            welcomeTextField.setText("Accesso come ospite");
            btnRegistrati.setDisable(false);
            btnLogout.setText("Accedi");
        }
        else {
            welcomeTextField.setText("Ciao, " + utente.getUsername());
            btnRegistrati.setDisable(true);
            btnLogout.setText("Logout");
        }

    }
}
