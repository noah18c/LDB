import java.sql.Timestamp;

public class Order {
    int orderId, customerId, deliveryId;
    String status;
    Timestamp orderDate;

    public Order(int orderId, int customerId, String status, Timestamp orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order(int customerId, String status, Timestamp orderDate) {
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
