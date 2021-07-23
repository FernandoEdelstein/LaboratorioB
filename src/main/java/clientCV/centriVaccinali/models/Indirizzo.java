package clientCV.centriVaccinali.models;

import clientCV.shared.Check;

public class Indirizzo {
    private String strada, civico, comune, provincia, CAP;
    private Qualificatore qualificatore;

        private Check check = new Check();

    public Indirizzo(Qualificatore qualificatore,
                     String strada,
                     String civico,
                     String comune,
                     String provincia,
                     String CAP) {

        this.qualificatore = qualificatore;
        this.strada = strada;
        this.civico = civico;
        this.comune = comune;
        this.provincia = provincia;

        if(Integer.parseInt(CAP) > 9 && Integer.parseInt(CAP) < 98101 && CAP.length() == 5)
            this.CAP = CAP;
        else
            this.CAP = "00000";
    }

    //Getters
    public String getStrada() {
        return strada;
    }

    public String getCivico() {
        return civico;
    }

    public String getComune() {
        return comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCAP() {
        return CAP;
    }

    public Qualificatore getQualificatore() {
        return qualificatore;
    }

    //To String
    @Override
    public String toString() {
        return check.lowercaseNotFirst(qualificatore.toString()) +
                " " +
                strada +
                " " +
                civico +
                " - " +
                comune +
                " (" +
                provincia +
                "), " +
                CAP;
    }

}
