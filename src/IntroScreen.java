import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class IntroScreen {
    private Connection con;
    public ArrayList<String> pizzas = new ArrayList<>();
    public ArrayList<String> drinks = new ArrayList<>();
    public ArrayList<String> desserts = new ArrayList<>();

    public IntroScreen(Connection con){
        this.con = con;
    }


    public void run() {
        welcomeMessage();
        //printMenu();
       // choices();
        customerInfo();
        orderCreation();
    }

    private void orderCreation() {

    }

    private void customerInfo() {
        System.out.println("Please enter customer details: ");
        String firstName, lastName, streetName, postalCode;
        int phoneNumber, houseNumber;

        Scanner s = new Scanner(System.in);
        System.out.print("First Name: ");
        firstName = s.nextLine();

        System.out.print("Last Name: ");
        lastName = s.nextLine();

        // try to find customer
        CustomerDataMapper customerMapper = new CustomerDataMapper(con);
        AddressDataMapper addressMapper = new AddressDataMapper(con);

        Optional<Customer> opt = customerMapper.find(firstName, lastName);
        Customer cust;
        Address address = null;

        if (opt.isPresent()) {
            System.out.println("We found your details in the system:");
            cust = opt.get();
            Optional<Address> optAdr = addressMapper.find(cust.getAddressId());
            if (optAdr.isPresent())
                address = optAdr.get();
            System.out.println("Phone number: " + cust.getPhoneNumber());
            System.out.println("Street name: " + address.getStreetName());
            System.out.println("House number: " + address.getHouseNumber());
            System.out.println("Postal code: " + address.getPostalCode());
        }
        else {
            System.out.print("Phone number: ");
            phoneNumber = s.nextInt();
            System.out.print("Street name: ");
            s.nextLine();
            streetName = s.nextLine();

            System.out.print("House number: ");
            houseNumber = s.nextInt();
            System.out.print("Postal code: ");
            s.nextLine();
            postalCode = s.nextLine();


            address = new Address(postalCode, streetName, houseNumber);
            int addressId = addressMapper.insert(address);
            cust = new Customer(firstName, lastName, phoneNumber, addressId);
            customerMapper.insert(cust);
        }
        // if no existing customer, create new one
    }

    private void choices() {
        HashMap<Integer, String> itemsByNumber = new HashMap<>();
        int counter = 1;
        for (int i = 0; i< pizzas.size(); i++) {
            itemsByNumber.put(counter++, pizzas.get(i));
        }
        for (int i = 0; i< drinks.size(); i++) {
            itemsByNumber.put(counter++, drinks.get(i));
        }
        for (int i = 0; i< desserts.size(); i++) {
            itemsByNumber.put(counter++, desserts.get(i));
        }

        System.out.println("\nPlease make your choice (1, 2, ...), to stop press any other key");
        Scanner s = new Scanner(System.in);
        ArrayList<Integer> choices = new ArrayList<>();

        System.out.print("Please choose atleast one pizza first: ");
        while(s.hasNextInt()){
           int c = s.nextInt();
           if(c < pizzas.size() && c > 0){
               choices.add(c);
               break;
           } else {
               System.out.println("Please choose atleast one pizza...");
           }

        }

        System.out.println("Now you may add additional items. ");
        while(s.hasNextInt()){
            int c = s.nextInt();
            if(c>0 && c<counter) {
                choices.add(c);
            } else {
                System.out.println("Please select a valid number");
            }
        }

        System.out.print("\nYour order: ");
        for (int choice : choices) {
            System.out.print(itemsByNumber.get(choice) + ", ");
        }
    }


    private void printMenu() {
        String query1 = "SELECT pizza_name, pizza_price FROM pizza";
        String query2 = "SELECT drink_name, drink_price FROM drink";
        String query3 = "SELECT dessert_name, dessert_price FROM dessert";

        ArrayList<String> pizzaPrices = new ArrayList<>();
        ArrayList<String> drinkPrices = new ArrayList<>();
        ArrayList<String> dessertPrices = new ArrayList<>();


        ResultSet rs1, rs2, rs3;

        try(Statement stmt = con.createStatement()){
            rs1 = stmt.executeQuery(query1);
            while(rs1.next()){
                pizzas.add(rs1.getString("pizza_name"));
                pizzaPrices.add(rs1.getString("pizza_price"));

            }

            rs2 = stmt.executeQuery(query2);
            while(rs2.next()){
                drinks.add(rs2.getString("drink_name"));
                drinkPrices.add(rs2.getString("drink_price"));
            }

            rs3 = stmt.executeQuery(query3);
            while(rs3.next()){
                desserts.add(rs3.getString("dessert_name"));
                dessertPrices.add(rs3.getString("dessert_price"));
            }

            int counter = 1;

            System.out.println("");
            for(int i = 0; i < pizzas.size() ; i++){
                System.out.println(counter++ + " " + pizzas.get(i)+" | "+pizzaPrices.get(i));
            }

            System.out.println("");
            for(int i = 0; i < drinks.size() ; i++){
                System.out.println(counter++ + " " + drinks.get(i)+" | "+drinkPrices.get(i));
            }

            System.out.println("");
            for(int i = 0; i < desserts.size() ; i++){
                System.out.println(counter++ + " " + desserts.get(i)+" | "+dessertPrices.get(i));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void welcomeMessage() {
        System.out.println("Welcome!!!");
        System.out.println("This is the pizza shop.");
        System.out.println("Here you can find the menu!");
    }
}
