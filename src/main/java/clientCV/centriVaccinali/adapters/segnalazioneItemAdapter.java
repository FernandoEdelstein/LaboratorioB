package clientCV.centriVaccinali.adapters;

import clientCV.centriVaccinali.models.Segnalazione;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class segnalazioneItemAdapter {
    Segnalazione segnalazione;

    @FXML
    private Text sintomoText;

    @FXML
    private Text severitaText;

    @FXML
    private Text descrizioneText;

    public void setData(Segnalazione s){
        segnalazione = s;
        StringBuilder severita = new StringBuilder();

        for(int i = 1; i <= 5; i++) {
            if (i <= segnalazione.getSeverita())
                severita.append("●");
            else
                severita.append("○");
        }

        sintomoText.setText("Sintomo: " + segnalazione.getSintomo());
        severitaText.setText("Severità: " + severita);
        descrizioneText.setText("Descrizione: " + segnalazione.getDescrizione());

    }

}
