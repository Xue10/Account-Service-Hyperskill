package account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder encoder;

    long id = users == null ? 0 : users.count();

    @PostMapping("/auth/signup")
    public UserOut signup(@Valid @RequestBody User user) {
        user.setEmail(user.getEmail().toLowerCase());
        if (users.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        user.setId(++id);
        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
        return new UserOut(user);
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

