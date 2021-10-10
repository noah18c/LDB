import java.sql.Timestamp;

public class Delivery {

    private int deliveryId, deliveryPersonId, addressId;
    private Timestamp deliveryTime;

    public Delivery(int deliveryId, int deliveryPersonId, Timestamp deliveryTime, int addressId){
        this.deliveryId = deliveryId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryTime = deliveryTime;
        this.addressId = addressId;
    }

    public Delivery(int deliveryPersonId, Timestamp deliveryTime, int addressId){
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryTime = deliveryTime;
        this.addressId = addressId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
