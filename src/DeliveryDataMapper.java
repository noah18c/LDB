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

    public DeliveryDataMapper(Connection con){
        this.con = con;
    }

    /**
     * Find a delivery created less than 5 minutes ago going to the same postal code, if it exists
     * @param postalCode the postal code the delivery must go to
     * @return delivery if found
     */
    public Optional<Delivery> findMatchingDelivery(String postalCode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime currTime = LocalDateTime.now();
        System.out.println(currTime);
//        try {
//            PreparedStatement stmt = con.prepareStatement("SELECT delivery_id FROM delivery WHERE ")
//        }
        return null;
    }

    public void insert(Delivery delivery) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO delivery (delivery_person_id, delivery_time) values (?, ?)");
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


}
