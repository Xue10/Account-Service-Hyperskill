package account.business;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.business.data.SecurityEvent;
import account.business.data.User;
import account.business.service.AuthService;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository users;

    @Autowired
    private SecurityEventRepository events;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getRemoteUser();
        String path = request.getContextPath();
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
            } else {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
            }
        }

        super.onAuthenticationFailure(request, response, exception);
    }

}
