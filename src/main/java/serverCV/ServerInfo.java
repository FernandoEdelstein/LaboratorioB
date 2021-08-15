package serverCV;

/**
 * Server Info
 *
 * @author Fernando Edelstein 740403 VA
 * @author Eliana Monteleone 741025 VA
 */

public class ServerInfo {

    /**
     * Postgres USERNAME
     */

        private static String PGUSERNAME = "postgres";

    /**
     * Postgres Password
     */

        private static String PGPASSWORD = "pass";

    /**
     * Postgres Port
     */

        private static final int DBPORT = 5432;

    /**
     * Postgres Database name
     */

        private static final String DBNAME = "centrivaccinali";

    /**
     * Server Port
     */

        private static int PORT = 7070;

    /**
     * Server ip
     */

        private static String IPSERVER = "localhost";



            //Setters
            public static void setPGUSERNAME(String PGUSERNAME) {
                ServerInfo.PGUSERNAME = PGUSERNAME;
            }

            public static void setPGPASSWORD(String PGPASSWORD) {
                ServerInfo.PGPASSWORD = PGPASSWORD;
            }

            public static void setPORT(int PORT) {
                ServerInfo.PORT = PORT;
            }

            public static void setIPSERVER(String IPSERVER) {
                ServerInfo.IPSERVER = IPSERVER;
            }



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
