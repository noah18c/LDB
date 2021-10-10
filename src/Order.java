public class Order {
    int orderId, customerId;
    String status;

    public Order(int orderId, int customerId, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
    }

    public Order(int customerId, String status) {
        this.customerId = customerId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
