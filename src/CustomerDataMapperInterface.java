import java.util.Optional;

public interface CustomerDataMapperInterface {
    Optional<Customer> find(String firstName, String lastName);
    void insert(Customer customer);
}
