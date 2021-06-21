package clientCV.cittadini;

import clientCV.shared.Utente;

public class CittadinoRegistrato extends Utente {
    private String email;
    private int idVaccinazione;

    public CittadinoRegistrato(
            String username,
            String password,
            String nome,
            String cognome,
            String email,
            String CF,
            int idVaccinazione) {

        super(nome, cognome, CF, username, password);
        this.email = email;
        this.idVaccinazione = idVaccinazione;
    }

    public String getEmail() {
        return email;
    }

    public int getIdVaccinazione() {
        return idVaccinazione;
    }

}
