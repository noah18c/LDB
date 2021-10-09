import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
    private Connection con;
    private String firstName, lastName;
    private int customerID, phoneNumber, streetNumber;
    private String postalCode, streetName, numberAdd;

    public Customer(Connection con){
        this.con = con;
    }

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

    public String nameToString(){
        return firstName+" "+lastName;
    }

    public boolean recognizeCustomer() {
        String query = "SELECT first_name, last_name FROM customer WHERE first_name = '"+this.getFirstName()+"' AND last_name = '"+this.getLastName()+"';";
        ResultSet rs = null;

        boolean isKnown = false;
        try (Statement stmt = con.createStatement()) {
            rs = stmt.executeQuery(query);

            if(rs.equals(null)){
                isKnown = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isKnown;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getNumberAdd() {
        return numberAdd;
    }

    public void setNumberAdd(String numberAdd) {
        this.numberAdd = numberAdd;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
