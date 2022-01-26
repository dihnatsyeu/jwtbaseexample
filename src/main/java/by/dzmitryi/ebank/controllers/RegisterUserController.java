package by.dzmitryi.ebank.controllers;


import by.dzmitryi.ebank.model.SecurityCustomer;
import by.dzmitryi.ebank.repository.Customer;
import by.dzmitryi.ebank.service.SecureUserDetailsManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterUserController {

    private final SecureUserDetailsManager manager;

    public RegisterUserController(SecureUserDetailsManager manager) {
        this.manager = manager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody Customer customer) {
        SecurityCustomer securityCustomer = new SecurityCustomer(customer);
        manager.createUser(securityCustomer);
    }
}
