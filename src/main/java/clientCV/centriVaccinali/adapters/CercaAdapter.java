package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Tipologia;
import clientCV.shared.Check;
import clientCV.shared.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
    @FXML
    private ScrollPane centriScroll;
    @FXML
    private GridPane grid;

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiALogIn(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
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


            grid.getChildren().clear();
            //Creazione dei Risultati
                for (int i = 0; i<centrivaccinali.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getClassLoader()
                            .getResource(path + "CentroSearchItem.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();

                    CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                    centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                    grid.add(anchorPane,0, i);

                    GridPane.setMargin(anchorPane, new Insets(10,0,0,0));

                }

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

            grid.getChildren().clear();
            for (int i = 0; i<centrivaccinali.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getClassLoader()
                        .getResource(path + "CentroSearchItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                grid.add(anchorPane,0, i);

                GridPane.setMargin(anchorPane, new Insets(10,0,0,0));

            }
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
            btnLogout.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        vaiALogIn(actionEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            benvenutoText.setText("Ciao, " + utente.getUsername());
            btnRegistrati.setDisable(true);
        }

        populateListView();
    }

    private void populateListView() {
        String query = "SELECT * FROM centrivaccinali";

        try {
            proxy = new Proxy();
            centrivaccinali = proxy.filter(query);
            data = FXCollections.observableArrayList();
            for (int i = 0; i<centrivaccinali.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getClassLoader()
                        .getResource(path + "CentroSearchItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                grid.add(anchorPane,0, i);

                GridPane.setMargin(anchorPane, new Insets(10,0,0,0));

            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tipo = {Tipologia.OSPEDALIERO.toString(), Tipologia.HUB.toString(), Tipologia.AZIENDALE.toString()};
        tipologiaCBox.getItems().addAll(tipo);


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
