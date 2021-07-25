package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Vaccinato;
import clientCV.cittadini.Cittadino;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverCV.Proxy;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiALogin(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
    }

    public void vaiACerca(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

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
            registratiBtn.setDisable(true);
        }
    }

    public void setCentro(String centro){
        Proxy proxy, proxy2;
        Check check = new Check();

        String query = "SELECT * FROM centrivaccinali WHERE nome = '" + centro.toLowerCase() + "'";
        String querySegnalazione = "SELECT * " +
                                    "FROM segnalazioni JOIN eventiavversi ON (eventiavversi.idevento = segnalazioni.idevento)" +
                                    "WHERE centrovaccinale = '" + centro.toLowerCase() + "'";
        StringBuilder descrizioni = new StringBuilder();
        StringBuilder severita = new StringBuilder();

        int totaleSegnalazioni = 0;

            ArrayList<Segnalazione> segnalazioni = new ArrayList<>();

        try {
            proxy = new Proxy();
            proxy2 = new Proxy();
            centroVaccinale = proxy.filter(query).get(0);
            segnalazioni = proxy2.getSegnalazione(querySegnalazione);

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

        nomeText.setText(check.lowercaseNotFirst(centroVaccinale.getNome()));
        tipologiaText.setText("Tipologia: " + centroVaccinale.getTipologia().toString());
        indirizzoText.setText("Indirizzo: " + centroVaccinale.getIndirizzo().toString());

        numSegnalazioni.setText(String.valueOf(segnalazioni.size()));

        double media = ((double) totaleSegnalazioni) / segnalazioni.size();
        if (Double.isNaN(media))
            mediaSeverita.setText("0,0 / 5,00");
        else
            mediaSeverita.setText(String.format("%.02f", media) + " / 5,00");

    }

    public void vaiASegnalazione(ActionEvent event) throws IOException {
        Proxy proxy;
        Check check = new Check();
        Cittadino cittadino = (Cittadino)utente;
        String query = "SELECT * FROM vaccinati_" + check.formatTableName(centroVaccinale.getNome()) + " WHERE idvaccinazione = " + cittadino.getIdVaccinazione();

        try {
            proxy = new Proxy();
            ArrayList<Vaccinato> vaccinati = proxy.getVaccinati(query);

            if(vaccinati.isEmpty()) {
                mostraWarning("Non sei registrato a questo centro vaccinale", "Puoi segnalare eventi avversi solo presso il centro \nvaccinale in cui ti è stato somministrato il vaccino");
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
