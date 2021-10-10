import java.sql.Connection;

public class Customer {
    private String firstName, lastName;
    private int phoneNumber, addressId, customerId, orderHistory;

    public Customer(String firstName, String lastName, int phoneNumber, int addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
        orderHistory = 0;
    }

    public Customer(int customerId, String firstName, String lastName, int phoneNumber, int addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
        this.customerId = customerId;
        orderHistory = 0;
    }

    public int getCustomerId() { return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAddressId() { return addressId;}

    public void setAddressId(int addressId) {this.addressId = addressId;}

    public int getOrderHistory() { return orderHistory;}

    public void setOrderHistory(int orderHistory) {this.orderHistory = orderHistory;}
}
