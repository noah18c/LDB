import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;

public class IntroScreen {
    private Connection con;
    public ArrayList<String> pizzas = new ArrayList<>();
    public ArrayList<String> drinks = new ArrayList<>();
    public ArrayList<String> desserts = new ArrayList<>();

    ArrayList<String> pizzaPrices;
    ArrayList<String> drinkPrices;
    ArrayList<String> dessertPrices;

    OrderDataMapper orderDataMapper;
    CustomerDataMapper customerMapper;
    AddressDataMapper addressMapper;
    DeliveryDataMapper deliveryDataMapper;
    DeliveryPersonMapper deliveryPersonMapper;

    ArrayList<Integer> choices;
    float totalPrice;

    Customer cust;
    Order order;
    Address address;
    Delivery delivery;
    DeliveryPerson deliveryPerson;

    HashMap<Integer, String> itemsByNumber;
    HashMap<Integer, String> pricesByNumber;

    DateTimeFormatter dtf;

    public IntroScreen(Connection con){
        this.con = con;
        orderDataMapper = new OrderDataMapper(con);
        customerMapper = new CustomerDataMapper(con);
        addressMapper = new AddressDataMapper(con);
        deliveryDataMapper = new DeliveryDataMapper(con);
        deliveryPersonMapper = new DeliveryPersonMapper(con);

        dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }


    public void run() throws InterruptedException {
        welcomeMessage();
        TimeUnit.SECONDS.sleep(2);
        printMenu();
        TimeUnit.SECONDS.sleep(2);
        choices();
        customerInfo();
        TimeUnit.SECONDS.sleep(3);
        orderCreation();
        deliveryCreation();
    }

    private void deliveryCreation() {
        // Check if there exists a delivery with time created < 5 minutes ago with the same postal code
        // if so, add the order to that delivery
//        deliveryDataMapper.findMatchingDelivery(address.getPostalCode());

        //if not:
        // find a delivery person with the corresponding postal code
        DeliveryPerson dp = null;
        Optional<DeliveryPerson> optdp = deliveryPersonMapper.find(address.getPostalCode());
        if (optdp.isPresent()) {
            dp = optdp.get();
            System.out.println("Delivery person assigned: " + dp.getDeliveryPersonId());
        }
        else
            System.out.println("No delivery persons in that area");
        //Create a new delivery, add that deliveryID to the order

        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);

        Delivery delivery = new Delivery(dp.getDeliveryPersonId(), timestamp);
        deliveryDataMapper.insert(delivery);
        order.setDeliveryId(delivery.getDeliveryId());
        // set a 5-minute timer, then have the delivery go out
        // Set the delivery guy to unavailable for 30 minutes
    }

    private void orderCreation() {
        System.out.println("\nYour order has been finalized: ");

        int pizzasOrdered = 0;
        System.out.print("Pizzas: ");
        for (int choice : choices) {
            if (choice < pizzas.size()) {
                System.out.print(itemsByNumber.get(choice) + ", ");
                pizzasOrdered++;
            }
        }

        System.out.print("\nDrinks: ");
        for (int choice : choices) {
            if (choice >= pizzas.size() && choice < drinks.size() + pizzas.size())
                System.out.print(itemsByNumber.get(choice) + ", ");
        }

        System.out.print("\nDesserts: ");
        for (int choice : choices) {
            if (choice >= drinks.size() + pizzas.size())
                System.out.print(itemsByNumber.get(choice) + ", ");
        }

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("\n\nTotal price: " + df.format(totalPrice));

        //discount voucher
        if (discountVoucher(cust, pizzasOrdered)) {
            System.out.println("You are eligible for a discount!");
            System.out.println("Discounted price: " + df.format(totalPrice*0.90));
        }
        else if(cust.getOrderHistory() >= 10) {
            System.out.println("You will be eligible for a discount on your next order!");
        }
        else {
            System.out.println("You are not yet eligible for a discount, order " + (10-cust.getOrderHistory()) + " more pizza(s) for a voucher");
        }

        order = new Order(cust.getCustomerId(), "Processing");
        orderDataMapper.insert(order);
    }

    private boolean discountVoucher(Customer c, int pizzasOrdered) {
        int orderCount = 0;
        boolean voucherAvailable;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT order_history FROM customer WHERE customer_id = ?");
            stmt.setInt(1, c.getCustomerId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                orderCount = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (orderCount >= 10) {
            voucherAvailable = true;
            c.setOrderHistory(orderCount-10+pizzasOrdered);
            customerMapper.update(c);
        }
        else {
            voucherAvailable = false;
            c.setOrderHistory(orderCount+pizzasOrdered);
            customerMapper.update(c);
        }

        return voucherAvailable;
    }

    private void customerInfo() {
        System.out.println("\n\nPlease enter customer details: ");
        String firstName, lastName, streetName, postalCode;
        int phoneNumber, houseNumber;
        char streetAdd;

        Scanner s = new Scanner(System.in);
        firstName = "";
        while(firstName.isEmpty()){
            System.out.print("First Name: ");
            firstName = s.nextLine();
            firstName.toLowerCase();
        }

        lastName = "";
        while(lastName.isEmpty()){
            System.out.print("Last Name: ");
            lastName = s.nextLine();
            lastName.toLowerCase();
        }

        // try to find customer

        Optional<Customer> opt = customerMapper.find(firstName, lastName);
        address = null;

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

            phoneNumber = 0;
            while(phoneNumber <= 600000000){
                System.out.print("Phone Number: ");
                if(s.hasNextInt()){
                    phoneNumber = s.nextInt();
                } else {
                    String dummy = s.next();
                    phoneNumber = 0;
                    System.out.println("Please use only positive integers");
                }
                if(phoneNumber<=600000000){
                    System.out.println("Please select a proper phone number, like 0612345678");
                }
            }

            s.nextLine(); //consumed by previous nextInt();

            postalCode = "";
            while(postalCode.isEmpty() || postalCode.length() < 6){
                System.out.print("Postal Code: ");
                postalCode = s.nextLine();
                postalCode.toLowerCase();
            }

            streetName = "";
            while(streetName.isEmpty()){
                System.out.print("Street Name: ");
                streetName = s.nextLine();
                streetName.toLowerCase();
            }

            houseNumber = 0;
            while(houseNumber <= 0){
                System.out.print("House Number: ");
                if(s.hasNextInt()){
                    houseNumber = s.nextInt();
                } else {
                    String dummy = s.next();
                    houseNumber = 0;
                    System.out.println("Please select an integer");
                }
            }

            System.out.println("That's about it!");


            address = new Address(postalCode, streetName, houseNumber);
            int addressId = addressMapper.insert(address);
            cust = new Customer(firstName, lastName, phoneNumber, addressId);
            customerMapper.insert(cust);
        }
        // if no existing customer, create new one
    }

    private void choices() {
        itemsByNumber = new HashMap<>();
        pricesByNumber = new HashMap<>();

        int counter = 1;
        for (int i = 0; i< pizzas.size(); i++) {
            pricesByNumber.put(counter, pizzaPrices.get(i));
            itemsByNumber.put(counter++, pizzas.get(i));
        }
        for (int i = 0; i< drinks.size(); i++) {
            pricesByNumber.put(counter, drinkPrices.get(i));
            itemsByNumber.put(counter++, drinks.get(i));
        }
        for (int i = 0; i< desserts.size(); i++) {
            pricesByNumber.put(counter, dessertPrices.get(i));
            itemsByNumber.put(counter++, desserts.get(i));
        }

        System.out.println("\nPlease make your choice (1, 2, ...), to stop press any other key");
        Scanner s = new Scanner(System.in);

        choices = new ArrayList<>();

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
            totalPrice += Float.parseFloat(pricesByNumber.get(choice));
            System.out.print(itemsByNumber.get(choice) + ", ");
        }
    }


    private void printMenu() {
        String query1 = "SELECT pizza_name, pizza_price FROM pizza";
        String query2 = "SELECT drink_name, drink_price FROM drink";
        String query3 = "SELECT dessert_name, dessert_price FROM dessert";

        pizzaPrices = new ArrayList<>();
        drinkPrices = new ArrayList<>();
        dessertPrices = new ArrayList<>();


        ResultSet rs1, rs2, rs3;

        try(Statement stmt = con.createStatement()){
            if (pizzas.isEmpty()) {
                rs1 = stmt.executeQuery(query1);
                while (rs1.next()) {
                    pizzas.add(rs1.getString("pizza_name"));
                    pizzaPrices.add(rs1.getString("pizza_price"));

                }

                rs2 = stmt.executeQuery(query2);
                while (rs2.next()) {
                    drinks.add(rs2.getString("drink_name"));
                    drinkPrices.add(rs2.getString("drink_price"));
                }

                rs3 = stmt.executeQuery(query3);
                while (rs3.next()) {
                    desserts.add(rs3.getString("dessert_name"));
                    dessertPrices.add(rs3.getString("dessert_price"));
                }
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
