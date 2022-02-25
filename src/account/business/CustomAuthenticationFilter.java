package account.business;

import account.business.service.CustomRememberMeServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class CustomAuthenticationFilter extends BasicAuthenticationFilter {

    private RememberMeServices rememberMeServices = new CustomRememberMeServices();

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, RememberMeServices rememberMeServices) {
        super(authenticationManager);
        this.rememberMeServices = rememberMeServices;
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, RememberMeServices rememberMeServices) {
        super(authenticationManager, authenticationEntryPoint);
        this.rememberMeServices = rememberMeServices;
    }


}
