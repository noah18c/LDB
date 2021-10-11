public class Order {
    int orderId, customerId, deliveryId;
    String status;



    Long orderDate;

    public Order(int orderId, int customerId, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
    }

    public Order(int customerId, String status) {
        this.customerId = customerId;
        this.status = status;
    }

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
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
