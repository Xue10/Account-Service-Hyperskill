package account.business.service;

import account.business.service.LoginAttemptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Service
public class CustomRememberMeServices implements RememberMeServices {

    @Autowired
    LoginAttemptService loginAttemptService;

    private static final Log logger = LogFactory.getLog("remember");

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("loginFail");
        String email = request.getRemoteUser();
        String path = request.getContextPath();
        loginAttemptService.onFailure(email, path);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        logger.debug("loginSuccess");
        loginAttemptService.onSuccess(request.getRemoteUser());
    }
}
