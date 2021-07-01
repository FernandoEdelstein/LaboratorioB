package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
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
    private Text benvenutoText;
    @FXML
    private Button btnRegistrati, btnLogout, btnVisualizza;
    @FXML
    private ListView<String> listaCentri;

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiACentro(ActionEvent event) throws IOException {

        if(listaCentri.getSelectionModel().getSelectedItem() == null) {
            mostraWarning("Nessun centro selezionato", "Seleziona un centro per vedere le informazioni in dettaglio");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass()
                .getClassLoader()
                .getResource(path + "Visualizza.fxml"));
        Parent root = loader.load();

        Adapter cAdapter = loader.getController();
        CentroAdapter centroAdapter = loader.getController();

        cAdapter.setUtente(utente);
        centroAdapter.setCentro(extractName(listaCentri.getSelectionModel().getSelectedItem()));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        if (utente == null) {
            benvenutoText.setText("Accesso come ospite");
            btnRegistrati.setDisable(false);
            btnLogout.setText("Accedi");
        }
        else {
            benvenutoText.setText("Ciao, " + utente.getUsername());
            btnRegistrati.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private String extractName(String centro) {
        StringBuilder name = new StringBuilder();

        for(int i = 0; i < centro.length(); i++) {
            if(centro.charAt(i + 2) == '•')
                break;
            else if(centro.charAt(i) != '"')
                name.append(centro.charAt(i));
        }

        return name.toString();
    }
}
