package clientCV.shared;

public class ServerInfo {
    private static String PGUSERNAME = "postgres";

    private static String PGPASSWORD = "pass";

    private static final int DBPORT = 5432;

    private static final String DBNAME = "centrivaccinali";

    private static int PORT = 8888;

    private static String IPSERVER = "localhost";

    //Getters
    public static int getDBPORT() {
        return DBPORT;
    }

    public static String getPGUSERNAME() {
        return PGUSERNAME;
    }

    public static String getPGPASSWORD() {
        return PGPASSWORD;
    }

    public static int getPORT() {
        return PORT;
    }

    public static String getDBNAME() {
        return DBNAME;
    }

    public static String getIPSERVER() {
        return IPSERVER;
    }
}
