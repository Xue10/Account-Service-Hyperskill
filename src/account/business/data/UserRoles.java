package account.business.data;

import java.util.HashSet;
import java.util.Set;

public class UserRoles {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private Set<String> roles;

    public UserRoles(long id, String name, String lastname, String email, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        Set<String> tmp = new HashSet<>();
        for (String s : roles) {
            tmp.add("ROLE_" + s);
        }
        this.roles = tmp;
    }

    public UserRoles(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        Set<String> tmp = new HashSet<>();
        Set<String> roles = user.getRole();
        for (String s : roles) {
            tmp.add("ROLE_" + s);
        }
        this.roles = tmp;
    }

    public long getId() {
        return id;
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

    public Set<String> getRoles() {
        return roles;
    }
}
