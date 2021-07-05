package serverCV;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import clientCV.centriVaccinali.models.*;
import clientCV.cittadini.Cittadino;
import clientCV.shared.Check;
import clientCV.shared.Utente;


public class Proxy{

    private final Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean isOperatore = false;


    public Proxy() throws IOException {

        socket = new Socket(ServerInfo.getIPSERVER(), ServerInfo.getPORT());

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                    true);
        } catch (IOException e) {

            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            throw e;
        }
    }


    public ArrayList<Sintomo> getSintomi(String query) throws IOException, SQLException {

        ArrayList<Sintomo> sintomi = new ArrayList<>();

        out.println("searchSintomi");
        out.println(query);

        while (true) {

            String nomeSintomo = in.readLine();

            if (nomeSintomo.equals("exit"))
                break;
            else {
                int idevento = Integer.parseInt(in.readLine());
                String descrizione = in.readLine();
                sintomi.add(new Sintomo(
                        idevento,
                        nomeSintomo,
                        descrizione
                ));
            }
        }

        return sintomi;
    }


    public ArrayList<String> getSingleValues(String query, String columnLabel) throws IOException {
        ArrayList<String> values = new ArrayList<>();

        out.println("getSingleValues");
        out.println(query);
        out.println(columnLabel);

        while (true) {
            String value = in.readLine();
            if (value.equals("exit"))
                break;
            else {
                values.add(value);
            }
        }
        return values;
    }


    public ArrayList<Segnalazione> getSegnalazione(String query) throws IOException {
        ArrayList<Segnalazione> segnalazioni = new ArrayList<>();

        out.println("getSegnalazione");
        out.println(query);

        while (true) {
            String centroVaccinale = in.readLine();

            if (centroVaccinale.equals("exit"))
                break;
            else {
                String userid = in.readLine();
                String sintomo = in.readLine();
                int severita = Integer.parseInt(in.readLine());
                String descrizione = in.readLine();

                segnalazioni.add(new Segnalazione(
                        userid,
                        sintomo,
                        severita,
                        descrizione,
                        centroVaccinale
                ));
            }
        }
        return segnalazioni;
    }


    public ArrayList<Vaccinato> getVaccinati(String query) throws IOException, SQLException {
        ArrayList<Vaccinato> vaccinati = new ArrayList<>();

        out.println("getVaccinati");
        out.println(query);

        while (true) {
            String nomecittadino = in.readLine();

            if (nomecittadino.equals("exit"))
                break;
            else {
                String cognomecittadino = in.readLine();
                String codfisc = in.readLine();
                String vaccino = in.readLine();
                int idvacc = Integer.parseInt(in.readLine());

                vaccinati.add(new Vaccinato(
                        nomecittadino,
                        cognomecittadino,
                        codfisc,
                        null,
                        null,
                        Vaccino.valueOf(vaccino),
                        idvacc
                ));
            }
        }
        return vaccinati;
    }


    public void insertDb(String query) throws IOException, SQLException {
        out.println("insertDb");
        out.println(query);
    }


    public void populateCentriVaccinali(String nomeCentro) throws IOException, SQLException {
        Check util = new Check();
        out.println("populateCentriVaccinali");
        out.println(nomeCentro);
        out.println("CREATE TABLE vaccinati_" + util.formatTableName(nomeCentro) +
                " (" +
                "nomecittadino VARCHAR(50), " +
                "cognomecittadino VARCHAR(50), " +
                "codicefiscale VARCHAR(50), " +
                "data DATE, vaccino VARCHAR(20), " +
                "idvacc SMALLINT, " +
                "PRIMARY KEY(codicefiscale), " +
                "FOREIGN KEY(idvacc) REFERENCES idunivoci(idvacc), " +
                "FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale)" +
                ")");
    }


    public ArrayList<CentroVaccinale> filter(String query) throws IOException, SQLException {
        out.println("filter");
        out.println(query);
        ArrayList<CentroVaccinale> centrivaccinali = new ArrayList<>();

        while (true) {

            String nome = in.readLine();

            if(nome.equals("exit"))
                break;
            else {
                Tipologia tipologia = Tipologia.valueOf(in.readLine());
                Qualificatore qualificatore = Qualificatore.valueOf(in.readLine());
                String strada = in.readLine();
                String civico = in.readLine();
                String comune = in.readLine();
                String provincia = in.readLine();
                String cap = in.readLine();

                centrivaccinali.add(new CentroVaccinale(
                        nome,
                        new Indirizzo(
                                qualificatore,
                                strada,
                                civico,
                                comune,
                                provincia,
                                cap
                        ),
                        tipologia
                ));
            }
        }

        return centrivaccinali;
    }


    public Utente login(String query, String User) throws IOException {
        out.println("login");
        out.println(query);
        out.println(User);

        boolean find = Boolean.parseBoolean(in.readLine());

        if(!find)
            return null;
        else {
            isOperatore = Boolean.parseBoolean(in.readLine());
            if(isOperatore) {
                String nome = in.readLine();
                String cognome = in.readLine();
                String CF = in.readLine();
                String username = in.readLine();
                String password = in.readLine();

                Utente u = new Utente(
                        nome,
                        cognome,
                        CF,
                        username,
                        password
                );
                return u;
            }
            else {
                String nome = in.readLine();
                String cognome = in.readLine();
                String CF = in.readLine();
                String username = in.readLine();
                String password = in.readLine();
                String email = in.readLine();
                int idvacc = Integer.parseInt(in.readLine());

                Cittadino u = new Cittadino(
                        nome,
                        cognome,
                        CF,
                        email,
                        username,
                        password,
                        idvacc
                );
                return u;
            }
        }
    }


    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean getOperatore() {
        return isOperatore;
    }
}

