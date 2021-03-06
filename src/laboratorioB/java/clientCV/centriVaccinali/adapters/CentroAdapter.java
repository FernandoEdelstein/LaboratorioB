package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Vaccinato;
import clientCV.cittadini.Cittadino;
import clientCV.shared.Check;
import clientCV.cittadini.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import clientCV.Proxy;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CentroAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class CentroAdapter extends Adapter{
    private Utente utente;
    private CentroVaccinale centroVaccinale;

    @FXML
    private Text benvenutoText, nomeText, tipologiaText, numSegnalazioni, mediaSeverita;
    @FXML
    private Button segnalaBtn, registratiBtn, logoutBtn;
    @FXML
    private Label indirizzoText;
    @FXML
    private GridPane segnalazioniGrid;
    @FXML
    private ScrollPane segnalazioniScroll;


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
     * Vai alla schermata LogIn
     *
     * @param event
     * @throws IOException
     */
    public void vaiALogin(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
    }

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
     * Nel caso sia nullo, attiva il bottone Accedi
     *
     * @param utente
     */
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;

        if (utente == null) {
            benvenutoText.setText("Accesso come ospite");
            segnalaBtn.setDisable(true);
            logoutBtn.setText("Accedi");
            logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        vaiALogin(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            benvenutoText.setText("Ciao, " + utente.getUsername());
            segnalaBtn.setDisable(false);
            registratiBtn.setText("Invia Segnalazione");

            registratiBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try{
                        saltaASegnalazione(actionEvent);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Imposta il centro richiesto
     * @param centro
     */
    public void setCentro(String centro){
        //Imposta il background dello Scroll Pane
        segnalazioniScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        segnalazioniGrid.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");

        Proxy proxy, proxy2;
        Check check = new Check();

        String query = "SELECT * FROM centrivaccinali WHERE nome = '" + centro + "'";
        String querySegnalazione = "SELECT * " +
                                    "FROM segnalazioni JOIN sintomi ON (sintomi.idsintomo = segnalazioni.idsintomo)" +
                                    "WHERE centrovaccinale = '" + centro + "'";
        StringBuilder severita = new StringBuilder();

        int totaleSegnalazioni = 0;
            ArrayList<Segnalazione> segnalazioni = new ArrayList<>();

        try {
            proxy = new Proxy();
            proxy2 = new Proxy();
            centroVaccinale = proxy.filtra(query).get(0);
            segnalazioni = proxy2.riceviSegnalazione(querySegnalazione);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

            for (int i = 0; i<segnalazioni.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getClassLoader()
                        .getResource(path + "SegnalazioneItem.fxml"));

                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                segnalazioneItemAdapter segnalazioneAdapter = fxmlLoader.getController();
                segnalazioneAdapter.setData(segnalazioni.get(i));

                segnalazioniGrid.add(anchorPane, 0, i);

                GridPane.setMargin(anchorPane, new Insets(10, 0, 0, 0));

                totaleSegnalazioni += segnalazioni.get(i).getSeverita();

                severita.delete(0, severita.length());
            }

        nomeText.setText(check.primaMaiuscola(centroVaccinale.getNome()));
        tipologiaText.setText("Tipologia: " + centroVaccinale.getTipologia().toString());
        indirizzoText.setText("Indirizzo: " + centroVaccinale.getIndirizzo().toString());

        numSegnalazioni.setText(String.valueOf(segnalazioni.size()));

        double media = ((double) totaleSegnalazioni) / segnalazioni.size();
        if (Double.isNaN(media))
            mediaSeverita.setText("0,0 / 5,00");
        else
            mediaSeverita.setText(String.format("%.02f", media) + " / 5,00");

    }

    /**
     * Vai alla schermata per inserire una segnalazione
     * Controlla che l'utente corrente sia presente nella tabella del centro selezionato
     *
     * @param event
     * @throws IOException
     */
    public void vaiASegnalazione(ActionEvent event) throws IOException {
        Proxy proxy;
        Check check = new Check();
        Cittadino cittadino = (Cittadino)utente;
        String query = "SELECT * FROM vaccinati_" + check.nomeTabella(centroVaccinale.getNome()) + " WHERE idvaccinazione = " + cittadino.getIdVaccinazione();

        try {
            proxy = new Proxy();
            ArrayList<Vaccinato> vaccinati = proxy.riceviVaccinati(query);

            if(vaccinati.isEmpty()) {
                mostraWarning("Non sei registrato a questo centro vaccinale", "Puoi segnalare eventi avversi solo presso il centro \nvaccinale in cui ti ?? stato somministrato il vaccino");
                return;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new
                FXMLLoader(CentriVaccinali.class.getClassLoader().getResource(path + "Segnalazione.fxml"));
        Parent root = loader.load();

        Adapter mAdapter = loader.getController();
        SegnalazioneAdapter segnalaAdapter = loader.getController();

            mAdapter.setUtente(utente);
            segnalaAdapter.setCentro(centroVaccinale);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saltaASegnalazione(ActionEvent event) throws IOException {
        Proxy proxy, proxy2;


        String query = "SELECT * FROM centrivaccinali WHERE nome = (SELECT centrovaccinale FROM idunivoci WHERE codicefiscale = '"+ utente.getCF() +"')";

        try {
            proxy = new Proxy();
            centroVaccinale = proxy.filtra(query).get(0);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        Check check = new Check();
        Cittadino cittadino = (Cittadino)utente;
        String query2 = "SELECT * FROM vaccinati_" + check.nomeTabella(centroVaccinale.getNome()) + " WHERE idvaccinazione = " + cittadino.getIdVaccinazione();

        try {
            proxy2 = new Proxy();
            ArrayList<Vaccinato> vaccinati = proxy2.riceviVaccinati(query2);

            if(vaccinati.isEmpty()) {
                mostraWarning("Non sei registrato a questo centro vaccinale", "Puoi segnalare eventi avversi solo presso il centro \nvaccinale in cui ti ?? stato somministrato il vaccino");
                return;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new
                FXMLLoader(CentriVaccinali.class.getClassLoader().getResource(path + "Segnalazione.fxml"));
        Parent root = loader.load();

        Adapter mAdapter = loader.getController();
        SegnalazioneAdapter segnalaAdapter = loader.getController();

        mAdapter.setUtente(utente);
        segnalaAdapter.setCentro(centroVaccinale);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
