package serverCV;


import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.centriVaccinali.models.Vaccinato;
import clientCV.shared.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientResourcesImpl implements ClientResources {
    public ClientResourcesImpl(){ }


    @Override
    public Utente login(String query, String user) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void inserisciDb(String query) throws IOException, SQLException {

    }

    @Override
    public ArrayList<CentroVaccinale> filtra(String query) throws IOException, SQLException {
        return null;
    }

    @Override
    public ArrayList<Sintomo> getSintomi(String query) throws IOException, SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getSingleValues(String query, String columnLabel) throws IOException, SQLException {
        return null;
    }

    @Override
    public ArrayList<Segnalazione> getSegnalazione(String query) throws IOException {
        return null;
    }

    @Override
    public ArrayList<Vaccinato> getVaccinati(String query) throws IOException, SQLException {
        return null;
    }

    @Override
    public void compilaCentriVaccinali(String tabella) throws IOException, SQLException {

    }
}
