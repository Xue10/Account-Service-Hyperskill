package account.business.service;

import account.business.data.SecurityEvent;
import account.business.data.User;
import account.business.data.UserRoles;
import account.controller.Changepass;
import account.controller.NewPassword;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final SecurityEventRepository events;


    List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @Autowired
    public AuthService(UserRepository users, PasswordEncoder encoder, SecurityEventRepository events) {
        this.users = users;
        this.encoder = encoder;
        this.events = events;
    }

    public UserRoles signup(User user) {
        if (users.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        String password = user.getPassword();

        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        user.setPassword(encoder.encode(password));
        if (users.count() == 0) {
            user.getRole().add("ADMINISTRATOR");
        } else {
            user.getRole().add("USER");
        }
        users.save(user);
        events.save(new SecurityEvent("CREATE_USER", "Anonymous", user.getEmail(), "/api/auth/signup"));
        return new UserRoles(user);
    }

    public Changepass changepass(NewPassword passwordBody, UserDetails details) {
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
        events.save(new SecurityEvent("CHANGE_PASSWORD", email, email, "/api/auth/changepass"));
        return new Changepass(email);
    }
}

