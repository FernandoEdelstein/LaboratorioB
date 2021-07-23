package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Tipologia;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.collections.FXCollections;
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
import serverCV.Proxy;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CercaAdapter extends Adapter implements Initializable {
    private Utente utente;
    private Check check = new Check();
    private Proxy proxy;
    private ArrayList<CentroVaccinale> centrivaccinali;
    private ObservableList<String> data;


    @FXML
    private ComboBox<String> tipologiaCBox;
    @FXML
    private RadioButton radComuneTipologia, radNome;
    @FXML
    private TextField nomeField, comuneField;
    @FXML
    private Text benvenutoText;
    @FXML
    private Button btnRegistrati, btnLogout, btnVisualizza;
    @FXML
    private ListView<String> listaCentri;

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiALogin(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
    }

    public void visualizzaCentro(ActionEvent event) throws IOException {

        if(listaCentri.getSelectionModel().getSelectedItem() == null) {
            mostraWarning("Nessun centro selezionato", "Seleziona un centro per vedere le informazioni in dettaglio");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass()
                .getClassLoader()
                .getResource(path + "Centro.fxml"));
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


    public void mostraCentriVaccinali() throws IOException, SQLException {

        if(radNome.isSelected()) {
            String nome = nomeField.getText().trim();

            if(nome.isBlank()) {
                mostraWarning("Campi mancanti", "Inserire il nome del centro per effettuare la ricerca");
                return;
            }

            //ricerca per nome
            proxy = new Proxy();
            String query = "SELECT * " +
                    "FROM centrivaccinali " +
                    "WHERE nome LIKE '%" + nome.toLowerCase() + "%'";
            centrivaccinali = proxy.filter(query);

            if(centrivaccinali.size() == 0)
                mostraWarning("Nessun centro trovato", "Non ci sono centri vaccinali registrati con questo nome");

            data = FXCollections.observableArrayList();
            for (CentroVaccinale centro: centrivaccinali)
                data.add(
                        "\"" +
                                check.lowercaseNotFirst(centro.getNome()) + "\"  •  " +
                                centro.getIndirizzo().getComune() + "  •  " +
                                centro.getTipologia()
                );

            listaCentri.setItems(data);

        }
        else if(radComuneTipologia.isSelected()) {
            String comune = check.lowercaseNotFirst(comuneField.getText().trim());
            String tipologia = tipologiaCBox.getValue();

            if(comune.isBlank() || tipologia == null) {
                mostraWarning("Campi mancanti", "Inserire il comune e la tipologia\nper effettuare la ricerca");
                return;
            }

            //ricerca per comune e tipologia
            proxy = new Proxy();

            String query = "SELECT * " +
                    "FROM centrivaccinali " +
                    "WHERE comune LIKE '%" + comune + "%' " +
                    "AND tipologia='" + tipologia + "'";

            centrivaccinali = proxy.filter(query);

            if(centrivaccinali.size() == 0)
                mostraWarning("Nessun centro trovato", "Non esistono centri vaccinali registrati \n corrispondenti ai criteri di ricerca");

            data = FXCollections.observableArrayList();
            for (CentroVaccinale centro: centrivaccinali)
                data.add(
                        "\"" +
                                check.lowercaseNotFirst(centro.getNome()) + "\"  •  " +
                                centro.getIndirizzo().getComune() + "  •  " +
                                centro.getTipologia()
                );
            listaCentri.setItems(data);
        }
    }

    public void enableFiltering () {
        nomeField.setDisable(radComuneTipologia.isSelected());
        comuneField.setDisable(radNome.isSelected());
        tipologiaCBox.setDisable(radNome.isSelected());
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

    private void populateListView() {

        String query = "SELECT * FROM centrivaccinali";

        try {
            proxy = new Proxy();
            centrivaccinali = proxy.filter(query);
            data = FXCollections.observableArrayList();
            for (CentroVaccinale centro: centrivaccinali)
                data.add(
                        "\"" +
                                check.lowercaseNotFirst(centro.getNome()) + "\"  •  " +
                                centro.getIndirizzo().getComune() + "  •  " +
                                centro.getTipologia()
                );
            //data.addAll(centrivaccinali);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        listaCentri.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipo = {Tipologia.OSPEDALIERO.toString(), Tipologia.HUB.toString(), Tipologia.AZIENDALE.toString()};
        tipologiaCBox.getItems().addAll(tipo);
        populateListView();
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
