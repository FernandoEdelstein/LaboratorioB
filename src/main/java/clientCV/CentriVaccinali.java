package clientCV;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CentriVaccinali extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(CentriVaccinali.class.getClassLoader().getResource(
                        "Layout/Login.fxml")));

        Scene scene = new Scene(root, 900, 600);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Centri Vaccinali");
        stage.show();
    }
}
