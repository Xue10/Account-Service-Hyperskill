package account.business;

import account.business.User;

public class UserOut {

    private long id;
    private String name;
    private String lastname;
    private String email;

    public UserOut(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
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
}