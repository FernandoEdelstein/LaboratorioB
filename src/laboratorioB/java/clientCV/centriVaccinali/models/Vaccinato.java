package clientCV.centriVaccinali.models;

import clientCV.cittadini.Persona;

import java.sql.Date;

/**
 * Vaccinato
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class Vaccinato extends Persona {
    private String centroVaccinale;
    private Vaccino vaccino;
    private Date data;
    private int idVaccinazione;

    /**
     * Costruttore Vaccinato
     *
     * @param nome
     * @param cognome
     * @param CF
     * @param centroVaccinale
     * @param data
     * @param vaccino
     * @param idVaccinazione
     */
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

    /**
     * Get CentroVaccinale
     * @return centroVaccinale
     */
    public String getCentroVaccinale() {
        return centroVaccinale;
    }

    /**
     * Get Vaccino
     * @return Vaccino
     */
    public Vaccino getVaccino() {
        return vaccino;
    }

    /**
     * Get Data
     * @return Data
     */
    public Date getData() {
        return data;
    }

    /**
     * Get IdVaccinazione
     * @return idVaccinazione
     */
    public int getIdVaccinazione() {
        return idVaccinazione;
    }

    /**
     * Set CentroVaccinale
     * @param centroVaccinale
     */
    public void setCentroVaccinale(String centroVaccinale) {
        this.centroVaccinale = centroVaccinale;
    }

    /**
     * Set Vaccino
     * @param vaccino
     */
    public void setVaccino(Vaccino vaccino) {
        this.vaccino = vaccino;
    }

    /**
     * Set Data
     * @param data
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Set IdVaccinazione
     * @param idVaccinazione
     */
    public void setIdVaccinazione(int idVaccinazione) {
        this.idVaccinazione = idVaccinazione;
    }
}
