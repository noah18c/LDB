import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderDataMapper {

    private Connection con;

    public OrderDataMapper(Connection con){
        this.con = con;
    }

    public Optional<Order> find(int orderId) {
        Order s = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM orders WHERE order_id=?;");
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                s = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3));
            }
        } catch (SQLException ex){
            System.out.println("Error in finding address");
        }
        return Optional.ofNullable(s);
    }

    public void insert(Order order) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO orders (customer_id, order_status) values (?, ?);");
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in order insertion");
            System.out.println(e.getMessage());
        }

        try {
            PreparedStatement keystmt = con.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet rs = keystmt.executeQuery();
            while (rs.next())
                order.setOrderId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error in PK finding");
        }
    }
}
