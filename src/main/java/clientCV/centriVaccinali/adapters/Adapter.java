package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public abstract class Adapter {
    public static final String path = "Layout/";

    public void impostaComboBox(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-font: 13px \"Montserrat\";" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-border-style: solid;\n" +
                "    -fx-border-color: silver;\n" +
                "    -fx-border-width: 1.5;\n" +
                "    -fx-background-color: rgba(255,255,255,0.75);");
    }

    public void cambiaSchermata(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(CentriVaccinali.class.getClassLoader()
                .getResource(path + fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cambiaSchermataConUtente(String fxml, Utente utente, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CentriVaccinali.class.getClassLoader()
                .getResource( path + fxml));

        Parent root = fxmlLoader.load();

        Adapter adapter = fxmlLoader.getController();
        adapter.setUtente(utente);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void mostraWarning(String title, String body) {
        Alert warning = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        warning.setHeaderText(title);
        warning.setContentText(body);
        warning.show();
    }

    public abstract void setUtente(Utente utente);

}
