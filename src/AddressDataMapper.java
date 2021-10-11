import java.sql.*;
import java.util.Optional;

public class AddressDataMapper {

    private Connection con;

    public AddressDataMapper(Connection con){
        this.con = con;

    }

    public Optional<Address> find(int addressId) {
        Address s = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM address WHERE address_id=?;");
            pstmt.setInt(1, addressId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                s = new Address(rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex){
            System.out.println("Error in finding address");
        }
        return Optional.ofNullable(s);
    }

    public int insert(Address address) {
        int id = 0;
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO address (postal_code, street_name, house_number) values (?, ?, ?1);");
            stmt.setString(1, address.getPostalCode());
            stmt.setString(2, address.getStreetName());
            stmt.setInt(3, address.getHouseNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in address insertion");
        }

        try {
            PreparedStatement findstmt = con.prepareStatement("SELECT address_id FROM address WHERE postal_code=? AND street_name=? AND house_number=?;");
            findstmt.setString(1, address.getPostalCode());
            findstmt.setString(2, address.getStreetName());
            findstmt.setInt(3, address.getHouseNumber());

            ResultSet rs = findstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error in address id finding");
            System.out.println(e.getMessage());
        }
        return id;
    }
}
