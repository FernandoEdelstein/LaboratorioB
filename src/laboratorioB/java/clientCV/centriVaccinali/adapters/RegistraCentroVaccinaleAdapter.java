package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Qualificatore;
import clientCV.centriVaccinali.models.Tipologia;
import clientCV.shared.Check;
import clientCV.cittadini.Utente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import clientCV.Proxy;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * RegistraCentroVaccinaleAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class RegistraCentroVaccinaleAdapter extends Adapter implements Initializable {
    private Utente utente;
    private Check check = new Check();

    @FXML
    private TextField nomeField, stradaField, civicoField, provField, comuneField, capField;
    @FXML
    private ComboBox<String> qualificatoreCombo, tipologiaCombo;
    @FXML
    private Text benvenutoText;

    /**
     * Vai alla schermata Registra Vaccinato
     *
     * @param event
     * @throws IOException
     */
    public void vaiARegistraVaccinato(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraVaccinato.fxml", utente, event);
    }

    /**
     * Vai alla schermata Home
     *
     * @param event
     * @throws IOException
     */
    public void vaiAHome(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("HomeCentri.fxml", utente, event);
    }

    /**
     * Implementazione del bottone LogOut
     * Chiede conferma prima di tornare alla Home e settare l'user a null
     * @param event
     */
    public void logoutBtnImpl(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma LogOut");
        alert.setHeaderText("Stai per eseguire il LogOut");
        alert.setContentText("Vuoi Continuare?");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType okButton = new ButtonType("Si", ButtonBar.ButtonData.YES);

        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    cambiaSchermataConUtente("Login.fxml", null, event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (type == noButton) {
                alert.close();
            } else {
            }
        });
    }

    /**
     * Imposta l'utente corrente
     *
     * @param utente
     */
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
    }

    /**
     * Controlla i campi e registra il centro
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void registraCentro(ActionEvent event) throws IOException, SQLException {
        String nomeCentro = nomeField.getText().trim();
        String tipologia = tipologiaCombo.getValue();
        String qualificatore = qualificatoreCombo.getValue();
        String strada = check.primaMaiuscola(stradaField.getText());
        String civico = civicoField.getText();
        String comune = check.primaMaiuscola(comuneField.getText());
        String cap = capField.getText();
        String provincia = provField.getText();

        // Controllo dei campi
        //Nome Centro
        if(nomeCentro.isBlank() || qualificatore == null || strada.isBlank() ||
                civico.isBlank() || comune.isBlank() || provincia.isBlank()
                || tipologia == null) {
            mostraWarning("Campi mancanti", "Inserire tutti i campi richiesti");
            return;
        }

        // CAP
        if(!cap.matches("[0-9]+")) {
            mostraWarning("Errore nei dati", "Il CAP inserito non ?? valido");
            return;
        } else if(cap.length() != 5 || Integer.parseInt(cap) < 10) {
            mostraWarning("Errore nei dati", "Il CAP inserito ?? errato o non esistente");
            return;
        }

        // controllo provincia
        if(provincia.length() != 2 || !provincia.matches("^[a-zA-Z]+$")) {
            mostraWarning("Errore nei dati", "La provincia inserita ?? errata");
            return;
        }

        // controllo civico
        if(civico.length()  > 5) {
            mostraWarning("Errore nei dati", "Il numero civico inserito ?? errato");
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
            mostraWarning("Centro gi?? registrato", "Questo centro ?? gi?? stato registrato");
        else {
            if(provinciaValida())
                mostraWarning("Provincia non valida", "Inserisci una provincia valida");
            //Se il centro e stato registrato correttamente
            else{
                proxy.inserireInDb(query);
                proxy1.registraNuovoCentro(nomeCentro);
                mostraWarning("Successo", "Centro registrato correttamente!");
                vaiAHome(event);
            }
        }

    }

    /**
     * Controlla che la provincia inserita sia una provincia valida
     * @return boolean
     */
    private boolean provinciaValida() {
        Proxy proxy;
        ArrayList<String> province = new ArrayList<>();
        String prov = provField.getText().trim().toUpperCase();

        String query = "SELECT * FROM province " +
                "WHERE sigla = '" + prov + "'";
        try {
            proxy = new Proxy();
            province = proxy.riceviValoriIndividuali(query,"sigla");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return province.isEmpty();
    }

    /**
     * Controlla che il centro non esista gi??
     * @return boolean
     */
    private boolean controlaCentro() {
        Proxy proxy;
        ArrayList<CentroVaccinale> centriVaccinali = new ArrayList<>();
        String centro = nomeField.getText().trim().toLowerCase();

        String query = "SELECT * FROM centrivaccinali " +
                        "WHERE nome = '" + centro + "'";

        try {
            proxy = new Proxy();
            centriVaccinali = proxy.filtra(query);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return !centriVaccinali.isEmpty();
    }

    /**
     * Imposta parametri di inizializzazione
     * Limita la quantit?? e tipologia dei caratteri
     *
     * @param url
     * @param resourceBundle
     */
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

        //Imposta limiti di caratteri
        nomeField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 50 ? change : null));

        stradaField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 30 ? change : null));

        comuneField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 40 ? change : null));

        provField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 2 ? change : null));
        provField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                provField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });


        capField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 5 ? change : null));
        capField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    capField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        civicoField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 4 ? change : null));
    }

}
