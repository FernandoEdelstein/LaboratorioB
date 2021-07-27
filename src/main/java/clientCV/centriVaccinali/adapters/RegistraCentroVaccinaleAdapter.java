package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Qualificatore;
import clientCV.centriVaccinali.models.Tipologia;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import serverCV.Proxy;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistraCentroVaccinaleAdapter extends Adapter implements Initializable {
    private Utente utente;
    private Check check = new Check();

    @FXML
    private TextField nomeField, stradaField, civicoField, provField, comuneField, capField;
    @FXML
    private ComboBox<String> qualificatoreCombo, tipologiaCombo;
    @FXML
    private Text benvenutoText;

    public void vaiARegistraVaccinatoScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraVaccinato.fxml", utente, event);
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
    }

    public void registraCentro() throws IOException, SQLException {
        String nomeCentro = nomeField.getText().trim();
        String tipologia = tipologiaCombo.getValue();
        String qualificatore = qualificatoreCombo.getValue();
        String strada = check.lowercaseNotFirst(stradaField.getText());
        String civico = civicoField.getText();
        String comune = check.lowercaseNotFirst(comuneField.getText());
        String cap = capField.getText();
        String provincia = provField.getText();

        // Controllo dei campi
        if(nomeCentro.isBlank() || qualificatore == null || strada.isBlank() ||
                civico.isBlank() || comune.isBlank() || provincia.isBlank()
                || tipologia == null) {
            mostraWarning("Campi mancanti", "Inserire tutti i campi richiesti");
            return;
        }

        // CAP
        if(!cap.matches("[0-9]+")) {
            mostraWarning("Errore nei dati", "Il CAP inserito non è valido");
            return;
        } else if(cap.length() != 5 || Integer.parseInt(cap) < 10) {
            mostraWarning("Errore nei dati", "Il CAP inserito è errato o non esistente");
            return;
        }

        // controllo provincia
        if(provincia.length() != 2 || !provincia.matches("^[a-zA-Z]+$")) {
            mostraWarning("Errore nei dati", "La provincia inserita è errata");
            return;
        }

        // controllo civico
        if(civico.length()  > 5) {
            mostraWarning("Errore nei dati", "Il numero civico inserito è errato");
            return;
        }

        String query = "INSERT INTO centrivaccinali VALUES('"
                + nomeCentro.toLowerCase() + "', '"
                + tipologia + "', '"
                + qualificatore + "', '"
                + strada + "', '"
                + civico + "', '"
                + comune + "', '"
                + provincia.toUpperCase() + "', '"
                + cap + "')";

        Proxy proxy = new Proxy();
        Proxy proxy1 = new Proxy();

        if (controlaCentro())
            mostraWarning("Centro già registrato", "Questo centro è già stato registrato");
        else {
            proxy.insertDb(query);
            proxy1.populateCentriVaccinali(nomeCentro);
            mostraWarning("Successo", "Centro registrato correttamente!");
        }

    }

    private boolean controlaCentro() {
        Proxy proxy;
        ArrayList<CentroVaccinale> centriVaccinali = new ArrayList<>();
        String centro = nomeField.getText().trim().toLowerCase();

        String query = "SELECT * FROM centrivaccinali " +
                "WHERE nome = '" + centro + "'";

        try {
            proxy = new Proxy();
            centriVaccinali = proxy.filter(query);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return !centriVaccinali.isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipologia = {
                Tipologia.AZIENDALE.toString(),
                Tipologia.HUB.toString(),
                Tipologia.OSPEDALIERO.toString() };

        tipologiaCombo.getItems().addAll(tipologia);

        String[] qualificatore = {
                Qualificatore.VIA.toString(),
                Qualificatore.VIALE.toString(),
                Qualificatore.PIAZZA.toString(),
                Qualificatore.CORSO.toString() };
        qualificatoreCombo.getItems().addAll(qualificatore);

    }

}
