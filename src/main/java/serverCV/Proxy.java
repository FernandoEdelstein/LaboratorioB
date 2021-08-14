package serverCV;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import clientCV.centriVaccinali.models.*;
import clientCV.cittadini.Cittadino;
import clientCV.shared.Check;
import clientCV.shared.Utente;


public class Proxy {

    private final Socket socket;
    private boolean operatore = false;

    private BufferedReader in = null;
    private PrintWriter out = null;



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

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public Utente login(String query, String User) throws IOException {
        out.println("login");
        out.println(query);
        out.println(User);

        boolean find = Boolean.parseBoolean(in.readLine());

        if(!find)
            return null;
        else {
            operatore = Boolean.parseBoolean(in.readLine());
            if(operatore) {
                String nome = in.readLine();
                String cognome = in.readLine();
                String CF = in.readLine();
                String username = in.readLine();
                String password = in.readLine();

                Utente u = new Utente(
                        nome,
                        cognome,
                        username,
                        password,
                        CF
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
                int idvaccinazione = Integer.parseInt(in.readLine());

                Cittadino u = new Cittadino(
                        password,
                        CF,
                        nome,
                        cognome,
                        email,
                        username,
                        idvaccinazione
                );
                return u;
            }
        }
    }

    public ArrayList<CentroVaccinale> filtra(String query) throws IOException, SQLException {
        out.println("filtra");
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

    public void registraNuovoCentro(String nomeCentro) throws IOException, SQLException {
        Check check = new Check();
        out.println("registraNuovoCentro");
        out.println(nomeCentro);
        out.println("CREATE TABLE vaccinati_" + check.nomeTabella(nomeCentro) +
                " (" +
                "nomecittadino VARCHAR(50), " +
                "cognomecittadino VARCHAR(50), " +
                "codicefiscale VARCHAR(50), " +
                "data DATE, vaccino VARCHAR(20), " +
                "idvaccinazione SMALLINT, " +
                "PRIMARY KEY(codicefiscale), " +
                "FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), " +
                "FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale)" +
                ")");
    }

    public void inserireInDb(String query) throws IOException, SQLException {
        out.println("inserireInDb");
        out.println(query);
    }

    public ArrayList<Vaccinato> riceviVaccinati(String query) throws IOException, SQLException {
        ArrayList<Vaccinato> vaccinati = new ArrayList<>();

        out.println("riceviVaccinati");
        out.println(query);

        while (true) {
            String nomecittadino = in.readLine();

            if (nomecittadino.equals("exit"))
                break;
            else {
                String cognomecittadino = in.readLine();
                String codfisc = in.readLine();
                String vaccino = in.readLine();
                int idvaccinazione = Integer.parseInt(in.readLine());

                vaccinati.add(new Vaccinato(
                        nomecittadino,
                        cognomecittadino,
                        codfisc,
                        null,
                        null,
                        Vaccino.valueOf(vaccino),
                        idvaccinazione
                ));
            }
        }
        return vaccinati;
    }

    public ArrayList<Sintomo> riceviSintomi(String query) throws IOException, SQLException {

        ArrayList<Sintomo> sintomi = new ArrayList<>();

        out.println("riceviSintomi");
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

    public ArrayList<Segnalazione> riceviSegnalazione(String query) throws IOException {
        ArrayList<Segnalazione> segnalazioni = new ArrayList<>();

        out.println("riceviSegnalazione");
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

    public ArrayList<String> riceviValoriIndividuali(String query, String columnLabel) throws IOException {
        ArrayList<String> values = new ArrayList<>();

        out.println("riceviValoriIndividuali");
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

}

