package account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class AuthController {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder encoder;

    long id = users == null ? 0 : users.count();

    List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @PostMapping("/auth/signup")
    public UserOut signup(@Valid @RequestBody User user) {
        if (users.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        String password = user.getPassword();

        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        user.setId(++id);
        user.setPassword(encoder.encode(password));
        users.save(user);
        return new UserOut(user);
    }

    @PostMapping("/auth/changepass")
    public Changepass changepass( @RequestBody NewPassword passwordBody, @AuthenticationPrincipal UserDetails details) {
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

    @GetMapping("/empl/payment")
    public UserOut get(@AuthenticationPrincipal UserDetails details) {

        String email = details.getUsername();
        User user = users.findByEmailIgnoreCase(email).get();
        return new UserOut(user);
    }
}

class UserOut {

    private long id;
    private String name;
    private String lastname;
    private String email;

    public UserOut(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
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
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}

