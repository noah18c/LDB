import java.sql.*;
import java.util.Optional;

public class CustomerDataMapper implements CustomerDataMapperInterface{

    private Connection con;

    public CustomerDataMapper(Connection con){
        this.con = con;

    }

    @Override
    public Optional<Customer> find(String firstName, String lastName) {
        Customer s = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM customer WHERE first_name=? AND last_name=?;");
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                s = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
            }
        } catch (SQLException ex){
            // handle any errors
        }
        return Optional.ofNullable(s);
    }

    @Override
    public void insert(Customer customer) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO customer (first_name, last_name, phone_number, address_id, order_history) values (?, ?, ?, ?, 0);");
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setInt(3, customer.getPhoneNumber());
            stmt.setInt(4, customer.getAddressId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in customer insertion");
        }
        try {
            PreparedStatement keystmt = con.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet rs = keystmt.executeQuery();
            if (rs.next())
                customer.setCustomerId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error in PK finding");
        }
    }

    public void update(Customer customerToBeUpdated) {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE customer SET order_history = ? WHERE customer_id = ?;");
            pstmt.setInt(1, customerToBeUpdated.getOrderHistory());
            pstmt.setInt(2, customerToBeUpdated.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println("Customer update failed");
        }
    }

}
