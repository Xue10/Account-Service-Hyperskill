package account.business;

import account.business.data.SecurityEvent;
import account.business.service.Util;
import account.repository.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private SecurityEventRepository events;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
            String path = request.getContextPath();
            String email = Util.getEmail();
            events.save(new SecurityEvent("ACCESS_DENIED", email, path, path));
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied!");
//            }
        }
    }
}
