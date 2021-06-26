package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CercaAdapter extends Adapter implements Initializable {
    private Utente utente;
    private Check check = new Check();

    private ArrayList<CentroVaccinale> centrivaccinali;
    private ObservableList<String> data;

    @FXML
    private ComboBox<String> tipologiaComboBox;
    @FXML
    private RadioButton filtraComuneRadio, filtraNomeRadio;
    @FXML
    private TextField nomeTextField, comuneTextField;
    @FXML
    private Text welcomeTextField;
    @FXML
    private Button btnRegistrati, btnLogout, btnVisualizza;
    @FXML
    private ListView<String> centriListView;

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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
