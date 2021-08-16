package serverCV;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

/**
 * OperazioniServer, descrive tutte le operazioni che realizza il ServerResourcesImpl
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public interface OperazioniServer {
    void login() throws IOException, SQLException;

    void close(Socket socket) throws IOException;

    void riceviSintomi() throws IOException, SQLException;

    void inserireInDb() throws IOException, SQLException;

    void registraNuovoCentro() throws IOException, SQLException;

    void riceviValoriIndividuali() throws IOException, SQLException;

    void riceviVaccinati() throws IOException, SQLException;

    void filtra() throws IOException, SQLException;

    void riceviSegnalazione() throws IOException, SQLException;
    }
