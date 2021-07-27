package clientCV.centriVaccinali.adapters;

import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import serverCV.Proxy;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistraCittadinoAdapter extends Adapter{
    @FXML
    private TextField fieldNome, fieldCognome, fieldCodiceFiscale,
            fieldUsername, fieldEmail, fieldID;
    @FXML
    private PasswordField fieldPassword;

    private Utente utente;
    private Check check = new Check();

    public void vaiACerca(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    public void vaiALogin(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void registraCittadino() throws IOException, SQLException, InterruptedException {
        String nome = fieldNome.getText();
        String cognome = fieldCognome.getText();
        String user = fieldUsername.getText();
        String CF = fieldCodiceFiscale.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String id = fieldID.getText();

        if(nome.isBlank() || cognome.isBlank() || CF.isBlank() ||
                user.isBlank() || password.isBlank() || email.isBlank() || fieldID.getText().isBlank()) {
            mostraWarning("Campi mancanti", "Inserire tutti i campi richiesti");
            return;
        }

        //controllo codice fiscale
        if(!check.cfValido(CF)) {
            mostraWarning("Codice fiscale errato", "Il codice fiscale inserito è errato, riprovare");
            return;
        }

        //controllo email
        if(!check.emailValido(email)) {
            mostraWarning("Email errato", "L'email inserita è errata, riprovare");
            return;
        }

        // controllo id univoco
        if(!controllaID(id)) {
            mostraWarning("ID univoco errato", "L'ID univoco di vaccinazione viene fornito dall'operatore ed è \nformato da sole cifre");
            return;
        }

        String insertAsUtente = "INSERT INTO utenti VALUES('"+user+"','"+password+"','"+CF+"','"+nome+"','"+cognome+"')";
        Proxy proxyUtenti = new Proxy();
        proxyUtenti.insertDb(insertAsUtente);

        Thread.sleep(100);

        int IDvaccinazione = Integer.parseInt(id);
        String insertAsCittadino = "INSERT INTO cittadinivaccinati VALUES('"+user+"','"+email+"','"+IDvaccinazione+"')";
        Proxy proxyCittadini = new Proxy();
        proxyCittadini.insertDb(insertAsCittadino);

        mostraWarning("Sei registrato!", "Accedi!");


    }

    private boolean controllaID(String id) throws IOException {

        if(id.matches("^[a-zA-Z]+$"))
            return false;

        int IDUnivocoVaccinazione = Integer.parseInt(id);
        ArrayList<String> ids;
        String query = "SELECT * FROM idunivoci WHERE idvaccinazione = '"+IDUnivocoVaccinazione+"'";


        Proxy proxy = new Proxy();
        ids = proxy.getSingleValues(query, "idvaccinazione");

            return !ids.isEmpty();

        }

}
