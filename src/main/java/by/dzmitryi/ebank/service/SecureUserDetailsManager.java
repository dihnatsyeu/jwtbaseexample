package by.dzmitryi.ebank.service;

import by.dzmitryi.ebank.model.SecurityCustomer;
import by.dzmitryi.ebank.repository.Customer;
import by.dzmitryi.ebank.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecureUserDetailsManager implements UserDetailsManager {

    private final PasswordEncoder passwordEncoder;
    protected final CustomerRepository repository;

    public SecureUserDetailsManager(CustomerRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        SecurityCustomer securityCustomer = (SecurityCustomer) user;
        Customer customer = securityCustomer.getCustomer();
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        repository.save(customer);

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        try {
            UserDetails user = loadUserByUsername(username);
            if (user != null) {
                return true;
            }
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customerList = repository.findByEmail(username);
        if (customerList.size() == 0) {
            throw new UsernameNotFoundException("No customer with username " + username);
        }
        return new SecurityCustomer(customerList.get(0));
    }

}
