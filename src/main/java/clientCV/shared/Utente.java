package clientCV.shared;

public class Utente extends Persona{
    private String username, password;

    public Utente(String nome,
                  String cognome,
                  String username,
                  String password,
                  String CF) {
        super(nome, cognome, CF);
        this.username = username;
        this.password = password;
    }

    //Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
