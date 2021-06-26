package serverCV;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public interface ServerResources {
    void login() throws IOException, SQLException;
    void close(Socket socket) throws IOException;

    void filtra() throws IOException, SQLException;

    void getSintomi() throws IOException, SQLException;

    void getSegnalazione() throws IOException, SQLException;

    void getSingleValues() throws IOException, SQLException;

    void getVaccinati() throws IOException, SQLException;

    void inserisciDb() throws IOException, SQLException;

    void riempiCentriVaccinali() throws IOException, SQLException;
}
