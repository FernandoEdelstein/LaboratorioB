package clientCV.centriVaccinali.models;

import clientCV.shared.Persona;

import java.sql.Date;

public class Vaccinato extends Persona {
    private String centroVaccinale;
    private Vaccino vaccino;
    private Date data;
    private int idVaccinazione;


    public Vaccinato (String nome,
                      String cognome,
                      String CF,
                      String centroVaccinale,
                      Date data,
                      Vaccino vaccino,
                      int idVaccinazione) {

        super(nome, cognome, CF);

        this.centroVaccinale = centroVaccinale;
        this.data = data;
        this.idVaccinazione = idVaccinazione;
        this.vaccino = vaccino;
    }


    public String getCentroVaccinale() {
        return centroVaccinale;
    }

    public Vaccino getVaccino() {
        return vaccino;
    }

    public Date getData() {
        return data;
    }

    public int getIdVaccinazione() {
        return idVaccinazione;
    }

    public void setCentroVaccinale(String centroVaccinale) {
        this.centroVaccinale = centroVaccinale;
    }

    public void setVaccino(Vaccino vaccino) {
        this.vaccino = vaccino;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setIdVaccinazione(int idVaccinazione) {
        this.idVaccinazione = idVaccinazione;
    }
}
