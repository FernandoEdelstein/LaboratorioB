package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.shared.Check;
import clientCV.cittadini.Utente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import clientCV.Proxy;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * SegnalazioneAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class SegnalazioneAdapter extends Adapter implements Initializable {
    private CentroVaccinale centroVaccinale;
    private Utente utente;

    private Map<String, Integer> idSintomo;
    private boolean nuovaSegnalazione = true;
    public static final int MAX_CARATTERI = 256;

    @FXML
    private Text benvenutoText, nomeCentroText;
    @FXML
    private Button registratiBtn, logoutBtn;
    @FXML
    private ComboBox<String> sintomoCombo, severitaCombo;
    @FXML
    private TextArea noteAggiuntiveTextArea;
    @FXML
    private Label descrizioneText;

    /**
     * Vai alla schermata Cerca
     *
     * @param event
     * @throws IOException
     */
    public void vaiACerca(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    /**
     * Vai alla schermata Registrati
     *
     * @param event
     * @throws IOException
     */
    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
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
     * Vai alla schermata Centro
     *
     * @param event
     * @throws IOException
     */
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

    /**
     * Invia Segnalazione
     * Controlla i campi vuoti ed esegue la query per inserirla sul db
     *
     * Nel caso ci sia gi?? una segnalazione, viene aggiornata
     *
     * @param event
     * @throws IOException
     */
    public void inviaSegnalazione(ActionEvent event) throws IOException {
        String nomeCentro = centroVaccinale.getNome();
        String descrizione = noteAggiuntiveTextArea.getText().trim();
        String sintomo = sintomoCombo.getValue();
        int severita = Integer.parseInt(severitaCombo.getValue());
        String query;



        if(descrizione.isBlank() || sintomoCombo.getValue() == null) {
            mostraWarning("Campi vuoti", "Compilare tutti i campi per procedere");
            return;
        }

        if(nuovaSegnalazione)
            query = "INSERT INTO segnalazioni " +
                    "VALUES("+generaIdSegnalazione()+", " + idSintomo.get(sintomo)+", '"+utente.getUsername()+"', '"+nomeCentro+"', "+severita+",'"+descrizione+"')";
        else
            query = "UPDATE segnalazioni " +
                    "SET idsintomo = "+ idSintomo.get(sintomo)+", severita = "+severita+", descrizione = '"+descrizione+"' " +
                    "WHERE userid = '"+utente.getUsername()+"'";


        Proxy proxy;

        try {
            proxy = new Proxy();
                proxy.inserireInDb(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nuovaSegnalazione = false;

            mostraWarning("Successo!", "La tua segnalazione ?? stata pubblicata!");

        vaiAVisualizzaCentro(event);
    }

    /**
     * Imposta il centro corrente
     * @param centroVaccinale
     */

    public void setCentro(CentroVaccinale centroVaccinale) {
        Check check = new Check();
        this.centroVaccinale = centroVaccinale;
        nomeCentroText.setText(check.primaMaiuscola(centroVaccinale.getNome()));
    }

    /**
     * Imposta l'utente corrente
     * Imposta i sintomi sulla comboBox
     * @param utente
     */
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
        registratiBtn.setText("Invia Segnalazione");

        Proxy proxy;
        ArrayList<Segnalazione> segnalazione;

        try {
            proxy = new Proxy();
            String query = "SELECT * " +
                    "FROM segnalazioni " +
                    "JOIN sintomi ON (sintomi.idsintomo = segnalazioni.idsintomo) " +
                    "WHERE userid = '" + utente.getUsername() + "'";
            segnalazione = proxy.riceviSegnalazione(query);

            if (segnalazione.size() > 0) {
                mostraWarning("Hai gi?? fatto una segnalazione in precedenza", "Se modifichi la tua segnalazione, quella precedente \nsar?? rimossa");
                sintomoCombo.setValue(segnalazione.get(0).getSintomo());

                    severitaCombo.setValue(Integer.toString(segnalazione.get(0).getSeverita()));
                noteAggiuntiveTextArea.setText(segnalazione.get(0).getDescrizione());
                stampaDescrizioneSintomo();
                nuovaSegnalazione = false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Stampa la descrizione del sintomo
     */
    public void stampaDescrizioneSintomo() {
        String sintomoComboTxt = sintomoCombo.getValue();
        String query = "SELECT * FROM sintomi WHERE sintomo = '" + sintomoComboTxt + "'";
        Proxy proxy;
        ArrayList<Sintomo> sintomi;

        try {
            proxy = new Proxy();
            sintomi = proxy.riceviSintomi(query);
            if(sintomi.size() > 0)
                descrizioneText.setText(sintomi.get(0).getDescrizione());

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Impostazione al momento di inizializzare la schermata
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        severitaCombo.setValue("1");

        String query = "SELECT * " +
                        "FROM sintomi";
        ArrayList<Sintomo> sintomi;
        Proxy proxy;
        idSintomo = new HashMap<>();

        try {
            proxy = new Proxy();
            sintomi = proxy.riceviSintomi(query);

            for (Sintomo sintomo: sintomi) {
                sintomoCombo.getItems().add(sintomo.getNome());
                idSintomo.put(sintomo.getNome(), sintomo.getIdsintomo());
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        String[] livelliSeverita = {"1","2","3","4","5"};
        severitaCombo.getItems().addAll(livelliSeverita);

        noteAggiuntiveTextArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CARATTERI ? change : null));

        sintomoCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                stampaDescrizioneSintomo();
            }
        });

    }

    /**
     * Genera un UID della segnalazione, verifica che non sia presente nel db
     * @return
     */
    private int generaIdSegnalazione() {
        ArrayList<String> tmpID = new ArrayList<>();
        Random rand = new Random();
        int uIDSegnalazione = -1;

        Proxy proxy;

        while(true) {
            uIDSegnalazione = rand.nextInt(Short.MAX_VALUE);
            String getIDquery = "SELECT idsegnalazione " +
                    "FROM segnalazioni " +
                    "WHERE idsegnalazione = '"+uIDSegnalazione+"'";
            try {
                proxy = new Proxy();
                tmpID = proxy.riceviValoriIndividuali(getIDquery, "idsegnalazione");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (tmpID.isEmpty())
                break;
        }

        return uIDSegnalazione;
    }
}
