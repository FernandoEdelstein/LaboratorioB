package clientCV.centriVaccinali.models;

public class Sintomo {
    private String nome, descrizione;
    private int idevento;

    public Sintomo(int idevento, String nome, String descrizione) {
        this.idevento = idevento;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getIdevento() {
        return idevento;
    }
}
