package clientCV;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * classe Main dei Clienti
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class CentriVaccinali extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../Layout/Login.fxml"));

        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Centri Vaccinali");
        primaryStage.show();

    }

    /**
     * Launcher dell'applicazione
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
