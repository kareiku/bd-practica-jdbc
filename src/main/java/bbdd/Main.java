package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final static String DB_SERVER = null;
    private final static int DB_PORT = 3306;
    private final static String DB_NAME = null;
    private final static String DB_USER = null;
    private final static String DB_PASS = null;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
        Connection connection = DriverManager.getConnection(url, DB_USER, DB_PASS);

        // TODO Prueba sus funciones
        //  1. Añade los planetas a la base de datos
        //  2. Muestra por pantalla la lista de pasajeros de la cabina A-60-S
        //  3. Muestra por pantalla una lista de sistemas, planetas y número de pasajeros con origen en ellos

        connection.close();
    }

    private static void nuevoPlaneta(String nombre, double masa, int radio, String sistema) throws SQLException {
        // TODO Añade planetas a la base de datos
    }

    private static void listaPasajerosCabina(String cubierta, int cabina, String lado) throws SQLException {
        // TODO Muestra por pantalla una lista de pasajeros de una cabina
    }

    private static void listaOrigenes() throws SQLException {
        // TODO Muestra por pantalla una lista de planetas, sistemas y número de pasajeros provinientes de ellos
    }
}
