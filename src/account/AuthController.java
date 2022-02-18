package account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/auth/signup")
    public UserOut signup(@Valid @RequestBody User user) {
        if (user.getEmail().matches(".+@acme.com$")) {
            return new UserOut(user.getName(), user.getLastname(), user.getEmail());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}

class UserOut {

    private String name;
    private String lastname;
    private String email;

    public UserOut(String name, String lastname, String email) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
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

