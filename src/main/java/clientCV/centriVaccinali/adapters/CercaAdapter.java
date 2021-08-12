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


    @FXML
    private ComboBox<String> tipologiaCBox;
    @FXML
    private RadioButton radComuneTipologia, radNome;
    @FXML
    private TextField nomeField, comuneField;
    @FXML
    private Text benvenutoText;
    @FXML
    private Button registratiBtn, logoutBtn;
    @FXML
    private ScrollPane centriScroll;
    @FXML
    private GridPane centriGrid;

    public void vaiARegistrati(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml", utente, event);
    }

    public void vaiALogIn(ActionEvent event) throws IOException {
        cambiaSchermata("Login.fxml", event);
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


            centriGrid.getChildren().clear();
            //Creazione dei Risultati
                for (int i = 0; i<centrivaccinali.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getClassLoader()
                            .getResource(path + "CentroSearchItem.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();

                    CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                    centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                    centriGrid.add(anchorPane,0, i);

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

            centriGrid.getChildren().clear();
            for (int i = 0; i<centrivaccinali.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getClassLoader()
                        .getResource(path + "CentroSearchItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                centriGrid.add(anchorPane,0, i);

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
            registratiBtn.setDisable(false);
            logoutBtn.setText("Accedi");
            logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
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
            registratiBtn.setVisible(false);
        }

        fillGridPane();
    }

    private void fillGridPane() {
        String query = "SELECT * FROM centrivaccinali";

        try {
            proxy = new Proxy();
            centrivaccinali = proxy.filter(query);
            for (int i = 0; i<centrivaccinali.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getClassLoader()
                        .getResource(path + "CentroSearchItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                CentroSearchItemAdapter centroSearchItemAdapter = fxmlLoader.getController();
                centroSearchItemAdapter.setData(centrivaccinali.get(i), utente);

                centriGrid.add(anchorPane,0, i);

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
        centriScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        centriGrid.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
    }
}
