package clientCV.centriVaccinali.adapters;

import clientCV.CentriVaccinali;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class Adapter {
    public static final String path = "Layout/";

    public void changeScene(String layout, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(CentriVaccinali.class.getClassLoader().getResource(
                        path + layout)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
