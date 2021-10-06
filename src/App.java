
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

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select pizza_name From pizza");
            String data = "";
            int i = 1;
            while(rs.next()) {
                data+=rs.getString(i);
            }
            System.out.println(data);

            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
