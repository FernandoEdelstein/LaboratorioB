package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.shared.Check;
import clientCV.shared.Utente;
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
import serverCV.Proxy;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SegnalazioneAdapter extends Adapter implements Initializable {
    private CentroVaccinale centroVaccinale;
    private Utente utente;

    private Map<String, Integer> idevento;
    private boolean isNew = true;
    public static final int MAX_CARATTERI = 256;
    private int ultimoIdSegnalazione;

    @FXML
    private Text benvenutoText, nomeCentroText;
    @FXML
    private Button registratiBtn;
    @FXML
    private ComboBox<String> sintomoCombo, severitaCombo;
    @FXML
    private TextArea noteAggiuntiveTextArea;
    @FXML
    private Label descrizioneText;

    public void vaiACerca(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiAVisualizzaCentro(ActionEvent event) throws IOException {
        FXMLLoader loader = new
                FXMLLoader(CentriVaccinali.class.getClassLoader().getResource(path + "Centro.fxml"));
        Parent root = loader.load();

        Adapter mAdapter = loader.getController();
        CentroAdapter centroAdapter = loader.getController();
        mAdapter.setUtente(utente);
        centroAdapter.setCentro(centroVaccinale.getNome());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void inviaSegnalazione(ActionEvent event) throws IOException {
        String nomeCentro = centroVaccinale.getNome();
        String descrizione = noteAggiuntiveTextArea.getText().trim();
        String sintomo = sintomoCombo.getValue();
        int severita = Integer.parseInt(severitaCombo.getValue());
        String query;

        if(descrizione.isBlank() || sintomoCombo.getValue() == null) {
            mostraWarning("Campi vuoti", "Inserire una nota aggiuntiva");
            return;
        }

        if(isNew)
            query = "INSERT INTO segnalazioni (idevento, userid, centrovaccinale, severita, descrizione) " +
                    "VALUES("+idevento.get(sintomo)+", '"+utente.getUsername()+"', '"+nomeCentro+"', "+severita+",'"+descrizione+"')";
        else
            query = "UPDATE segnalazioni " +
                    "SET idevento = "+idevento.get(sintomo)+", severita = "+severita+", descrizione = '"+descrizione+"' " +
                    "WHERE userid = '"+utente.getUsername()+"'";

        System.out.println(query);
        Proxy proxy;

        try {
            proxy = new Proxy();
            proxy.insertDb(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        isNew = false;

            mostraWarning("Successo!", "La tua segnalazione è stata pubblicata!");

        vaiAVisualizzaCentro(event);
    }

    public void setCentro(CentroVaccinale centroVaccinale) {
        Check check = new Check();
        this.centroVaccinale = centroVaccinale;
        nomeCentroText.setText(check.lowercaseNotFirst(centroVaccinale.getNome()));
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
        registratiBtn.setDisable(true);

        Proxy proxy;
        ArrayList<Segnalazione> segnalazione;

        try {
            proxy = new Proxy();
            String query = "SELECT * " +
                    "FROM segnalazioni " +
                    "JOIN eventiavversi ON (eventiavversi.idevento = segnalazioni.idevento) " +
                    "WHERE userid = '" + utente.getUsername() + "'";
            segnalazione = proxy.getSegnalazione(query);

            if (segnalazione.size() > 0) {
                mostraWarning("Hai già fatto una segnalazione in precedenza", "Se modifichi la tua segnalazione, quella precedente \nsarà rimossa");
                sintomoCombo.setValue(segnalazione.get(0).getSintomo());

                    severitaCombo.setValue(Integer.toString(segnalazione.get(0).getSeverita()));
                noteAggiuntiveTextArea.setText(segnalazione.get(0).getDescrizione());
                stampaDescrizioneSintomo();
                isNew = false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stampaDescrizioneSintomo() {
        String sintomoComboBox = sintomoCombo.getValue();

        String query = "SELECT * " +
                "FROM eventiavversi " +
                "WHERE sintomo = '" + sintomoComboBox + "'";
        Proxy proxy;
        ArrayList<Sintomo> sintomi;

        try {
            proxy = new Proxy();
            sintomi = proxy.getSintomi(query);
            if(sintomi.size() > 0)
                descrizioneText.setText(sintomi.get(0).getDescrizione());

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String query = "SELECT * " +
                        "FROM eventiavversi";
        ArrayList<Sintomo> sintomi;
        Proxy proxy;
        idevento = new HashMap<>();

        try {
            proxy = new Proxy();
            sintomi = proxy.getSintomi(query);

            for (Sintomo sintomo: sintomi) {
                sintomoCombo.getItems().add(sintomo.getNome());
                idevento.put(sintomo.getNome(), sintomo.getIdevento());
            }

            for(int i = 1; i <= 5 ; i++){
                severitaCombo.getItems().add(String.valueOf(i));
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        noteAggiuntiveTextArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CARATTERI ? change : null));
    }
}
