package clientCV.centriVaccinali.adapters;

import clientCV.shared.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import java.io.IOException;

/**
 * HomeCittadiniAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class HomeCittadiniAdapter extends Adapter {

    @FXML
    private Text benvenutoText;
    @FXML
    private Button logoutBtn, registratiBtn, cercaBtn;

    private Utente utente;

    /**
     * Vai alla schermata Cerca
     *
     * @param event
     * @throws IOException
     */
    public void vaiACercaScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Cerca.fxml", utente, event);
    }

    /**
     * Vai alla schermata Registrati
     *
     * @param event
     * @throws IOException
     */
    public void vaiARegistratiScene(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("RegistraCittadino.fxml",utente, event);
    }

    /**
     * Vai alla schermata LogIn
     *
     * @param event
     * @throws IOException
     */
    public void vaiALogIn(ActionEvent event) throws IOException {
        cambiaSchermataConUtente("Login.fxml", utente, event);
    }

    /**
     * Implementazione del bottone LogOut
     * Chiede conferma prima di tornare alla Home e settare l'user a null
     * @param event
     */
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

    /**
     * Imposta l'utente corrente
     * Nel caso sia nullo, attiva il bottone Accedi
     *
     * @param utente
     */
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
            logoutBtn.setText("Logout");
            registratiBtn.setVisible(false);
        }


    }
}
