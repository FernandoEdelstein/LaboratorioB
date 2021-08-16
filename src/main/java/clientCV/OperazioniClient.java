package clientCV;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.centriVaccinali.models.Vaccinato;
import clientCV.shared.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * OperazioniClient, descrive tutte le operazioni che realizza il Proxy
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */
public interface OperazioniClient {
    void close() throws IOException;

    Utente login(String query, String User) throws IOException;

    ArrayList<CentroVaccinale> filtra(String query) throws IOException, SQLException;

    void registraNuovoCentro(String nomeCentro) throws IOException, SQLException;

    void inserireInDb(String query) throws IOException, SQLException;

    ArrayList<Vaccinato> riceviVaccinati(String query) throws IOException, SQLException;

    ArrayList<Sintomo> riceviSintomi(String query) throws IOException, SQLException;

    ArrayList<Segnalazione> riceviSegnalazione(String query) throws IOException;

    ArrayList<String> riceviValoriIndividuali(String query, String colonna) throws IOException;

    }
