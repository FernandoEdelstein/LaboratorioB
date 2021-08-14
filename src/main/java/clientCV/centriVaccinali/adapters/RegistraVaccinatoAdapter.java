package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.Vaccino;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import serverCV.Proxy;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class RegistraVaccinatoAdapter extends Adapter implements Initializable {

    private Utente utente;

    @FXML
    private Text benvenutoText;

    @FXML
    private Button registraCentroBtn, registraVaccinatoBtn, logoutBtn;

    @FXML
    private TextField nomeField, codFiscaleField, cognomeField;

    @FXML
    private ComboBox<String> centrivaccinaliCombo;

    @FXML
    private ComboBox<String> vaccinoCombo;

    @FXML
    private DatePicker dataField;


    public void vaiARegistraCentroScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCentroVaccinale.fxml", utente, event);
    }

    public void vaiAHome(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("HomeCentri.fxml", utente, event);
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


    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        benvenutoText.setText("Ciao, " + utente.getUsername());
    }

    public void registraVaccinato(ActionEvent event) throws ParseException, IOException, SQLException, InterruptedException {
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String CF = codFiscaleField.getText();
        String vaccino = (String) vaccinoCombo.getValue();
        String centrovaccinale = (String) centrivaccinaliCombo.getValue();

        LocalDate date = dataField.getValue();

        Check check = new Check();

        //Controllo del nome, cognome e CF
        if(nome.isBlank() || cognome.isBlank() || CF.isBlank()
                || vaccino == null || centrovaccinale == null || date == null) {
            mostraWarning("Campi mancanti",
                    "Inserire tutti i campi richiesti");
            return;
        }

        //Controllo della data
        if(date.isAfter(LocalDate.now())) {
            mostraWarning("Data errata",
                    "Inserire una data corretta");
            return;
        }

        if(!check.cfValido(CF)) {
            mostraWarning("Codice fiscale errato", "Il codice fiscale inserito è errato, riprovare");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = date.toString();
        Date myDate = dateFormat.parse(data);

        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

        int idvaccino = generaUID();

        if(nuovoVaccinato(CF)) {
            String insertIntoIdunivoci = "INSERT INTO idunivoci VALUES('"+idvaccino+"', '"+CF+"')";
            Proxy proxy1 = new Proxy();
            proxy1.inserireInDb(insertIntoIdunivoci);

            Thread.sleep(100);

            String query = "INSERT INTO vaccinati_" + check.nomeTabella(centrovaccinale.toLowerCase()) + " VALUES('"+nome+"', '"+cognome+"','"+CF+"','"+sqlDate+"','"+vaccino+"', '"+idvaccino+"')";
            Proxy proxy = new Proxy();
            proxy.inserireInDb(query);

            mostraWarning("Cittadino registrato", "Cittadino si è registrato con ID: " + idvaccino);

                vaiAHome(event);
        }
        else
            mostraWarning("Errore", "Questo cittadino è già stato registrato");
    }

    private boolean nuovoVaccinato(String codfisc) {

        String getCF = "SELECT codicefiscale " +
                        "FROM idunivoci " +
                        "WHERE codicefiscale = '"+codfisc+"'";
        ArrayList<String> tmpCF = new ArrayList<>();

        Proxy proxy;

        try {
            proxy = new Proxy();
            tmpCF = proxy.riceviValoriIndividuali(getCF, "codicefiscale");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpCF.isEmpty();
    }

    private int generaUID() {
        ArrayList<String> tmpID = new ArrayList<>();
        Random rand = new Random();
        int idvacc = -1;
        Proxy proxy;

        while(true) {
            idvacc = rand.nextInt(Short.MAX_VALUE);
            String getIDquery = "SELECT idvaccinazione " +
                                "FROM idunivoci " +
                                "WHERE idvaccinazione = '"+idvacc+"'";
            try {
                proxy = new Proxy();
                tmpID = proxy.riceviValoriIndividuali(getIDquery, "idvaccinazione");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Vaccinato Registrato! Id Vaccinazione: " + ": " + idvacc);

            if (tmpID.isEmpty())
                break;


        }
        return idvacc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Proxy proxy;
        ArrayList<String> nomiCentri;
        String query = "SELECT * FROM centrivaccinali";

        String[] vaccini = {Vaccino.ASTRAZENECA.toString(),
                Vaccino.JOHNSONANDJOHNSON.toString(),
                Vaccino.MODERNA.toString(),
                Vaccino.PFIZER.toString()};

        vaccinoCombo.getItems().addAll(vaccini);

        try {
            proxy = new Proxy();
            nomiCentri = proxy.riceviValoriIndividuali(query, "nome");
            centrivaccinaliCombo.getItems().addAll(nomiCentri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
