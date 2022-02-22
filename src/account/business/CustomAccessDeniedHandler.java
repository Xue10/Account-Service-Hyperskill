package account.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
//            if (errorPage != null) {
//                // Put exception into request scope (perhaps of use to a view)
//                request.setAttribute(WebAttributes.ACCESS_DENIED_403,
//                        accessDeniedException);
//
//                // Set the 403 status code.
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//
//                // forward to error page.
//                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
//                dispatcher.forward(request, response);
//            }
//            else {
                response.sendError(HttpStatus.FORBIDDEN.value(),
                        "Access Denied!");
//            }
        }
    }
}
