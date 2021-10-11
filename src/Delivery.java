import java.sql.Timestamp;

public class Delivery {

    private int deliveryId, deliveryPersonId;
    private Timestamp deliveryTime;

    public Delivery(int deliveryId, int deliveryPersonId, Timestamp deliveryTime, int addressId){
        this.deliveryId = deliveryId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryTime = deliveryTime;
    }

    public Delivery(int deliveryPersonId, Timestamp deliveryTime){
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryTime = deliveryTime;
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

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
