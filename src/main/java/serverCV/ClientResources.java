package serverCV;

import clientCV.centriVaccinali.models.CentroVaccinale;
import clientCV.centriVaccinali.models.Segnalazione;
import clientCV.centriVaccinali.models.Sintomo;
import clientCV.centriVaccinali.models.Vaccinato;
import clientCV.shared.Utente;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientResources extends Remote {
    Utente login(String query, String user) throws IOException, RemoteException;

    void close() throws IOException, RemoteException;

    void inserisciDb(String query) throws IOException, SQLException, RemoteException;

    ArrayList<CentroVaccinale> filtra(String query) throws IOException, SQLException, RemoteException;

    ArrayList<Sintomo> getSintomi(String query) throws IOException, SQLException, RemoteException;

    ArrayList<String> getSingleValues(String query, String columnLabel) throws IOException, SQLException, RemoteException;

    ArrayList<Segnalazione> getSegnalazione (String query) throws IOException, RemoteException;

    ArrayList<Vaccinato> getVaccinati(String query) throws IOException, SQLException, RemoteException;

    void compilaCentriVaccinali(String tabella) throws IOException, SQLException, RemoteException;

}
