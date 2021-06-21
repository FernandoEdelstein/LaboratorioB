package clientCV.centriVaccinali.adapters;

import clientCV.shared.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInAdapter extends Adapter{
    @FXML
    private TextField unameField;
    @FXML
    private PasswordField passwordField;

    private Utente utente;


    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

}
