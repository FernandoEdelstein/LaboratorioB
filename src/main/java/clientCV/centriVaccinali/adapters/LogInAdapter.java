package clientCV.centriVaccinali.adapters;

import clientCV.shared.PersonaRegistrata;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInAdapter extends Adapter{
    @FXML
    private TextField unameField;
    @FXML
    private PasswordField passwordField;

    private PersonaRegistrata utente;


}
