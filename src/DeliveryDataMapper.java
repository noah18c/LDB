import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class DeliveryDataMapper {
    private Connection con;

    public DeliveryDataMapper(Connection con){
        this.con = con;
    }


    public void insert(Delivery delivery) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO delivery (delivery_person_id, delivery_time, address_id) values (?, ?, ?)");
            stmt.setInt(1, delivery.getDeliveryPersonId());
            stmt.setTimestamp(2, delivery.getDeliveryTime());
            stmt.setInt(3, delivery.getAddressId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in delivery insertion");
        }
        try {
            PreparedStatement keystmt = con.prepareStatement("SELECT SCOPE_IDENITY()");
            ResultSet rs = keystmt.executeQuery();
            delivery.setDeliveryId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error in PK finding for delivery");
        }
    }


}
