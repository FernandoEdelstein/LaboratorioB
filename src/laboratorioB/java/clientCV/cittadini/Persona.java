package clientCV.cittadini;

/**
 * Persona
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public abstract class Persona {
    private String nome;
    private String cognome;
    private String CF;

    /**
     * Constructor Persona
     *
     * @param nome
     * @param cognome
     * @param CF
     */
    public Persona(String nome, String cognome, String CF) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
    }

    //Getters
    /**
     * Get Nome
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Get Cognome
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Get CF
     * @return CF
     */
    public String getCF() {
        return CF;
    }

}
