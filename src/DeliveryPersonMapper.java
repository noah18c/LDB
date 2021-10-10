import java.sql.*;
import java.util.Optional;

public class DeliveryPersonMapper {

    private Connection con;

    public DeliveryPersonMapper(Connection con){
        this.con = con;
    }

    public Optional<DeliveryPerson> find(String firstName) {
        DeliveryPerson dp = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM delivery_person WHERE delivery_person_first_name=?");
            pstmt.setString(1, firstName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dp = new DeliveryPerson(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex){
            // handle any errors
        }
        return Optional.ofNullable(dp);
    }

    public Optional<DeliveryPerson> find(int deliveryPersonId) {
        DeliveryPerson dp = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM delivery_person WHERE delivery_person_id=?");
            pstmt.setInt(1, deliveryPersonId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dp = new DeliveryPerson(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex){
            // handle any errors
        }
        return Optional.ofNullable(dp);
    }

}
