package bbdd;

import java.sql.*;

public class Main {
    private final static String DB_SERVER = "localhost";
    private final static int DB_PORT = 3306;
    private final static String DB_NAME = "titanic_spaceship";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "";

    public static final String URL = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            assert false;
        }
        try {
            connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);

            /* ========================= Ej 1 =========================== */
            nuevoPlaneta("Kepler-186f", 3.3 * Math.pow(10, 24), 8800, "Copernico");
            nuevoPlaneta("HD 209458 b (Osiris)", 1.4 * Math.pow(10, 27), 100000, "Beta Pictoris");
            nuevoPlaneta("LHS 1140 b", 8.3 * Math.pow(10, 24), 8800, "Copernico");

            /* ========================= Ej 2 =========================== */
            listaPasajerosCabina("A", 60, "S");

            /* ========================= Ej 3 =========================== */
            listaOrigenes();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            assert false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                assert false;
            }
        }
    }

    private static void nuevoPlaneta(String nombre, double masa, int radio, String sistema) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            if (statement.execute("SELECT * FROM titanic_spaceship.planetas")) {
                resultSet = statement.getResultSet();
                resultSet.moveToInsertRow();
                resultSet.updateString(1, nombre);
                resultSet.updateDouble(2, masa);
                resultSet.updateInt(3, radio);
                resultSet.updateString(4, sistema);
                resultSet.insertRow();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            assert false;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
        }
    }

    private static void listaPasajerosCabina(String cubierta, int cabina, String lado) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            if (statement.execute("SELECT nombre, edad FROM titanic_spaceship.pasajeros WHERE cubierta = '" + cubierta +
                    "' AND numero_cabina = " + cabina + " AND lado_cabina = '" + lado + "'")) {
                resultSet = statement.getResultSet();
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    System.out.println("Nombre: " + resultSet.getString(1) + " Edad: " + resultSet.getString(2));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            assert false;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
        }
    }

    private static void listaOrigenes() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            if (statement.execute("SELECT sistema, pl.nombre, COUNT(DISTINCT id) FROM pasajeros pa " +
                    "RIGHT JOIN planetas pl ON pa.planeta_natal = pl.nombre AND pa.sistema_natal = pl.sistema " +
                    "GROUP BY pl.nombre, sistema ORDER BY sistema")) {
                resultSet = statement.getResultSet();
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    System.out.println("Sistema: " + resultSet.getString(1) +" Planeta: " + resultSet.getString(2) +
                            " Total: " + resultSet.getInt(3));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            assert false;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    assert false;
                }
            }
        }
    }
}
