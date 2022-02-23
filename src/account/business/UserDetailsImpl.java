package account.business;


import account.business.data.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class UserDetailsImpl implements UserDetails {
    private final String email;
    private final String password;
    private final Set<GrantedAuthority> roles;
    private final boolean isNonLocked;

    public UserDetailsImpl(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        Set<GrantedAuthority> userRoles = new HashSet<>();
        Set<String> role = user.getRole();
        for (String r : role) {
            userRoles.add(new SimpleGrantedAuthority("ROLE_" + r));
        }
        this.roles = userRoles;
        this.isNonLocked = user.isNonLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
