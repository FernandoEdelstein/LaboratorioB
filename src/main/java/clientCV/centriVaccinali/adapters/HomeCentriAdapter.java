package clientCV.centriVaccinali.adapters;

import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class HomeCentriAdapter extends Adapter {
    private Utente utente;
    @FXML
    private Text benvenutoText;

    public void vaiARegistraCentroScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCentro.fxml", utente, event);
    }

    public void vaiARegistraVaccinatoScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraVaccinato.fxml", utente, event);
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
    }

}
