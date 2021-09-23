package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.shared.Check;
import clientCV.cittadini.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * CentroSearchItemAdapter
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class CentroSearchItemAdapter {
    private CentroVaccinale centro;

    private Check check = new Check();

    @FXML
    private Button entraBtn;

    @FXML
    private Text nomeCentroText;

    @FXML
    private Text tipoText;

    @FXML
    private Text indirizzoText;

    /**
     * Imposta il file fxml con i dati del centro
     * @param c
     * @param u
     */
    public void setData(CentroVaccinale c, Utente u){
        this.centro = c;

        nomeCentroText.setText(check.primaMaiuscola(centro.getNome()));
        tipoText.setText(String.valueOf(centro.getTipologia()));
        indirizzoText.setText("Indirizzo: " + centro.getIndirizzo().toString());

        entraBtn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getClassLoader()
                    .getResource(Adapter.path + "Centro.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Adapter cAdapter = loader.getController();
            CentroAdapter centroAdapter = loader.getController();

            cAdapter.setUtente(u);
            centroAdapter.setCentro(centro.getNome());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

}
