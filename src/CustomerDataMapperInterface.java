import java.util.Optional;

public interface CustomerDataMapperInterface {
    Optional<Customer> find(int customerID);
    void insert(Customer customer);
}
