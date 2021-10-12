import java.sql.*;
import java.util.Optional;

public class DeliveryPersonMapper {

    private Connection con;

    public DeliveryPersonMapper(Connection con){
        this.con = con;
    }

    public Optional<DeliveryPerson> find(String postalCode) {
        DeliveryPerson dp = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM delivery_person WHERE postal_code=? LIMIT 1");
            pstmt.setString(1, postalCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dp = new DeliveryPerson(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
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
                dp = new DeliveryPerson(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex){
            // handle any errors
        }
        return Optional.ofNullable(dp);
    }

    public void update(DeliveryPerson deliveryPersonToBeUpdated) {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE delivery_person SET postal_code = ?, available = ? WHERE delivery_person_id = ?;");
            pstmt.setString(1, deliveryPersonToBeUpdated.getPostalCode());
            pstmt.setBoolean(2, deliveryPersonToBeUpdated.isAvailable());
            pstmt.setInt(3, deliveryPersonToBeUpdated.getDeliveryPersonId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println("deliveryPerson update failed");
            System.out.println(ex.getMessage());
        }
    }

}
