package account.business.service;

import account.business.data.SecurityEvent;
import account.business.data.User;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LoginAttemptService {
    private final UserRepository users;
    private final SecurityEventRepository events;

    @Autowired
    public LoginAttemptService(UserRepository users, SecurityEventRepository events) {
        this.users = users;
        this.events = events;
    }

    public void onSuccess(String email) {
        if (users.existsByEmail(email)) {
            User user = users.findByEmailIgnoreCase(email).get();
            if (user.getFailedAttempt() > 0) {
                user.setFailedAttempt(0);
                users.save(user);
            }
        }
    }

    public void onFailure(String email, String path) {

        Optional<User> userOptional = users.findByEmailIgnoreCase(email);
        User user = userOptional.orElse(null);

        if (user != null) {
            if (user.isNonLocked()) {
                if (user.getFailedAttempt() < 5) {
                    user.setFailedAttempt(user.getFailedAttempt() + 1);
                    users.save(user);
                    events.save(new SecurityEvent("LOGIN_FAILED", email, path, path));
                } else {
                    user.setNonLocked(false);
                    user.setFailedAttempt(0);
                    users.save(user);
                    events.save(new SecurityEvent("LOGIN_FAILED", email, path, path));
                    events.save(new SecurityEvent("BRUTE_FORCE", email, path, path));
                    events.save(new SecurityEvent("LOCK_USER", email, "Lock user " + email, path));
                }
            }
        } else {
            events.save(new SecurityEvent("LOGIN_FAILED", email, path, path));
        }
    }
}
