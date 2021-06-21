package clientCV.centriVaccinali.models;

public class Segnalazione {
    private String userid, sintomo, descrizione, centroVaccinale;
    private int severita;

    public Segnalazione(String userid,
                        String sintomo,
                        int severita,
                        String descrizione,
                        String centroVaccinale) {

        this.userid = userid;
        this.sintomo = sintomo;
        this.severita = severita;
        this.descrizione = descrizione;
        this.centroVaccinale = centroVaccinale;
    }

    public int getSeverita() {
        return severita;
    }

    public void setSeverita(int severita) {
        this.severita = severita;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCentroVaccinale() {
        return centroVaccinale;
    }

    public void setCentroVaccinale(String centroVaccinale) {
        this.centroVaccinale = centroVaccinale;
    }

    public String getSintomo() {
        return sintomo;
    }

    public void setSintomo(String sintomo) {
        this.sintomo = sintomo;
    }
}
