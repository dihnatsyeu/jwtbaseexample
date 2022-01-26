package by.dzmitryi.ebank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AccountController {


    @GetMapping(path = "/myAccount")
    public String myAccount() {
        return "This is securedMyAccount";
    }
}
