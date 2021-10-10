import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeliveryPerson {

    private String postalCode, firstName;
    private int deliveryPersonId;

    public DeliveryPerson(int deliveryPersonId, String postalCode, String firstName){
        this.deliveryPersonId = deliveryPersonId;
        this.postalCode = postalCode;
        this.firstName = firstName;
    }

    public DeliveryPerson(String postalCode, String firstName) {
        this.postalCode = postalCode;
        this.firstName = firstName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }
}
