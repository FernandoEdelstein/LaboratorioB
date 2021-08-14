package clientCV.centriVaccinali.models;

public class Sintomo {
    private String nome, descrizione;
    private int idsintomo;

    public Sintomo(int idevento, String nome, String descrizione) {
        this.idsintomo = idevento;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getIdsintomo() {
        return idsintomo;
    }
}
