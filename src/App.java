
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/ldb";
        String user = "root";
        String password = "Aftershave1808";




        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        }

        IntroScreen intro = new IntroScreen(con);
        intro.run();





        /*
        String query = "SELECT * FROM pizza";
        ResultSet rs = null;


        try (Statement stmt = con.createStatement()) {
            rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("pizza_name"));
            }
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            if(rs != null){

            }
        }
        */



    }
}
