import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class CustomerDataMapper implements CustomerDataMapperInterface{

    private Connection con;

    public CustomerDataMapper(Connection con){
        this.con = con;

    }

    @Override
    public Optional<Customer> find(int customerID) {
        return Optional.empty();
    }

    @Override
    public void insert(Customer customer) {
    }
}
