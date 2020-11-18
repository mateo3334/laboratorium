import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full_2020_086313:3306/mkiryczuk";

    static final String USER = "mkiryczuk";
    static final String PASS = "mkiryczuk";

    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_BIKE = "CREATE TABLE IF NOT EXISTS Rowery (ID int, Marka varchar(255), Model varchar(255), Rok_Produkcji varchar(255), Typ varchar(255) );";
    private static final String SELECT_ALL_FROM_BIKE = "SELECT ID, Marka, Model, Rok_Produkcji, Typ FROM Rowery";

    public static void main(String[] args) {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Laczenie z baza...");
            stmt.executeUpdate(CREATE_TABLE_BIKE);
            String selectedOperation;
            do
            {
                System.out.println("1. Pokaz dane\n2. Dodaj pojazd\n3. Edytuj pojazd\n4. Usun pojazd\nS. Exit wcisnij E");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(stmt);
                        break;
                    case "2" :
                        insertCar(stmt);
                        break;
                    case "3" :
                        updateCar(stmt);
                        break;
                    case "4" :
                        deleteCarById(stmt);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("E"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCarById(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery(SELECT_ALL_FROM_BIKE);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Wprowadz ID roweru do usuniecia");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Rowery WHERE ID= '"+id+"';";
        stmt.executeUpdate(deleteSql);
    }

    private static void updateCar(Statement stmt) throws SQLException {
        String type;
        String id;
        String marka;
        String model;
        String sql;
        String rokProdukcji;
        ResultSet rss = stmt.executeQuery(SELECT_ALL_FROM_BIKE);
        printOutHeader();

        printOutResult(rss);
        rss.close();
        System.out.println("Wprowadz ID edytowanego pojazdu");
        id = in.nextLine();

        System.out.println("Marka: ");
        marka = in.nextLine();

        System.out.println("Model: ");
        model = in.nextLine();

        System.out.println("Rok produkcji:");
        rokProdukcji = in.nextLine();

        System.out.println("Typ roweru");
        type = in.nextLine();
        sql = " UPDATE Rowery SET Model = '"+model+"' , Marka = '"+marka+"', Rok_Produkcji = '"+rokProdukcji+"',TYPE ='"+type+"' WHERE ID= '"+id+"';";
        stmt.executeUpdate(sql);
    }

    private static void insertCar(Statement stmt) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();

        System.out.println("Marka:");
        final String marka = in.nextLine();

        System.out.println("Model");
        final String model = in.nextLine();

        System.out.println("Rok produkcji:");
        final String rokProdukcji = in.nextLine();

        System.out.println("Typ pojazdu");
        final String type = in.nextLine();
        
        String sql = " INSERT INTO Rowery (ID, Model, Marka, Rok_Produkcji,TYPE) VALUES ('"+id+"', '"+model+"', '"+marka+"', '"+rokProdukcji+"','"+type+"')";
        stmt.executeUpdate(sql);
    }

    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_BIKE);
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }

    private static void printOutHeader() {
        System.out.println("ID    Marka    Model    Rok_Produkcji    Typ");
    }

    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String first;
        String last;
        String address;
        String city;
        while (rs.next()) {
            id = rs.getInt("ID");
            first = rs.getString("Marka");
            last = rs.getString("Model");
            address = rs.getString("Rok_Produkcji");
            city = rs.getString("Typ");

            System.out.println(id + "    " + first + "    " + last + "    " + address + "    " + city);
        }
    }
}
