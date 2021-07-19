package clientCV.cittadini;

import clientCV.shared.Utente;

public class Cittadino extends Utente {
    private String email;
    private int idVaccinazione;

    public Cittadino(
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

    @Override
    public String toString(){
        return " Cittadino to String -> Username: " + getUsername()
                + " , password: " + getPassword()
                + " , nome: " + getNome()
                + " , cognome: " + getCognome()
                + " , email: " + email
                + " , CF: " + getCF()
                + " , IDVac: " + getIdVaccinazione();
    }

    public String getEmail() {
        return email;
    }

    public int getIdVaccinazione() {
        return idVaccinazione;
    }



}
