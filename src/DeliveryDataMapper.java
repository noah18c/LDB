import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class DeliveryDataMapper {
    private Connection con;
    private DeliveryPersonMapper deliveryPersonMapper;

    public DeliveryDataMapper(Connection con){
        deliveryPersonMapper = new DeliveryPersonMapper(con);
        this.con = con;
    }

    /**
     * Find a delivery created less than 5 minutes ago going to the same postal code, if it exists
     * @param postalCode the postal code the delivery must go to
     * @return delivery if found
     */
    public Optional<Delivery> findMatchingDelivery(String postalCode) {
        Delivery d = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM delivery WHERE left_shop = ? AND delivery_person_id = (SELECT delivery_person_id FROM delivery_person WHERE postal_code = ?) LIMIT 1;");
            stmt.setString(2, postalCode);
            stmt.setBoolean(1, false);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DeliveryPerson dp = deliveryPersonMapper.find(rs.getInt(2)).get();
                d = new Delivery(rs.getInt(1), dp, rs.getTimestamp(3), this, new DeliveryPersonMapper(con));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(d);
    }

    public void insert(Delivery delivery) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO delivery (delivery_person_id, delivery_time, left_shop) values (?, ?, false)");
            stmt.setInt(1, delivery.getDeliveryPersonId());
            stmt.setTimestamp(2, delivery.getDeliveryTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error in delivery insertion");
        }
        try {
            PreparedStatement keystmt = con.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet rs = keystmt.executeQuery();
            if (rs.next())
                delivery.setDeliveryId(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error in PK finding for delivery");
        }
    }

    public void update(Delivery deliveryToBeUpdated) {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE delivery SET left_shop = ? WHERE delivery_id = ?;");
            pstmt.setBoolean(1, deliveryToBeUpdated.getOut());
            pstmt.setInt(2, deliveryToBeUpdated.getDeliveryId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println("Delivery update failed");
        }
    }


}
