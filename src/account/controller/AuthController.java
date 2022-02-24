package account.controller;

import account.business.data.SecurityEvent;
import account.business.data.User;
import account.business.data.UserRoles;
import account.business.service.AuthService;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public UserRoles signup(@Valid @RequestBody User user) {
        return authService.signup(user);
    }

    @PostMapping("/changepass")
    public Changepass changepass(@RequestBody NewPassword passwordBody, @AuthenticationPrincipal UserDetails details) {
        return authService.changepass(passwordBody, details);
    }
}


