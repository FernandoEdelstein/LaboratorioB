package clientCV.centriVaccinali.models;

/**
 * Sintomo
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public class Sintomo {
    private String nome, descrizione;
    private int idsintomo;

    /**
     * Costruttore Sintomo
     *
     * @param idevento
     * @param nome
     * @param descrizione
     */
    public Sintomo(int idevento, String nome, String descrizione) {
        this.idsintomo = idevento;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    /**
     * Get Nome
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Get Descrizione
     * @return Descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Get IdSintomo
     * @return IdSintomo
     */

    public int getIdsintomo() {
        return idsintomo;
    }
}
