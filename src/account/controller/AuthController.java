package account.controller;

import account.business.data.User;
import account.business.data.UserRoles;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder encoder;


    List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @PostMapping("/signup")
    public UserRoles signup(@Valid @RequestBody User user) {
        if (users.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        String password = user.getPassword();

        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        user.setPassword(encoder.encode(password));
        if (users.count() == 0) {
            user.setRole(Set.of("ADMINISTRATOR"));
        }
        users.save(user);
        return new UserRoles(user);
    }

    @PostMapping("/changepass")
    public Changepass changepass(@RequestBody NewPassword passwordBody, @AuthenticationPrincipal UserDetails details) {
        String oldPassword = details.getPassword();
        String newPassword = passwordBody.getNew_password();
        if (newPassword == null || newPassword.length() < 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be 12 chars minimum!");
        }
        if (encoder.matches(newPassword, oldPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
        if (breachedPasswords.contains(newPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        String email = details.getUsername();
        User user = users.findByEmailIgnoreCase(email).get();
        user.setPassword(encoder.encode(newPassword));
        users.save(user);
        return new Changepass(email);
    }


}

class NewPassword {

    private String new_password;

    public NewPassword() {}

    public NewPassword(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password() {
        return new_password;
    }
}

class Changepass {
    private String email;
    private String status = "The password has been updated successfully";

    public Changepass(String email) {
        this.email = email.toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}

