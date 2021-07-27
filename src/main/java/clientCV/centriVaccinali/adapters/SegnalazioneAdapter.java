package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.shared.Check;
import clientCV.shared.Utente;
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
import serverCV.Proxy;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class SegnalazioneAdapter extends Adapter implements Initializable {
    private CentroVaccinale centroVaccinale;
    private Utente utente;

    private Map<String, Integer> idevento;
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

    public void vaiACerca(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

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

        if(nuovaSegnalazione)
            query = "INSERT INTO segnalazioni " +
                    "VALUES("+generaIdSegnalazione()+", " +idevento.get(sintomo)+", '"+utente.getUsername()+"', '"+nomeCentro+"', "+severita+",'"+descrizione+"')";
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

        nuovaSegnalazione = false;

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
        registratiBtn.setVisible(false);

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
                nuovaSegnalazione = false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stampaDescrizioneSintomo() {
        String sintomoComboTxt = sintomoCombo.getValue();
        String query = "SELECT * FROM eventiavversi WHERE sintomo = '" + sintomoComboTxt + "'";
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

    private int generaIdSegnalazione() {
        ArrayList<String> tmpID = new ArrayList<>();
        Random r = new Random();
        int uIDSegnalazione = -1;
        int counter = 1;
        Proxy proxyID;

        while(true) {
            uIDSegnalazione = r.nextInt(Short.MAX_VALUE);
            String getIDquery = "SELECT idsegnalazione " +
                    "FROM segnalazioni " +
                    "WHERE idsegnalazione = '"+uIDSegnalazione+"'";
            try {
                proxyID = new Proxy();
                tmpID = proxyID.getSingleValues(getIDquery, "idsegnalazione");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Tentativo numero " + counter + ": " + uIDSegnalazione);

            if (tmpID.isEmpty())
                break;

            counter++;
        }

        return uIDSegnalazione;
    }
}
