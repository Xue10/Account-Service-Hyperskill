package account.business;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;
    @Id
    @NotEmpty
    @Pattern(regexp = ".+@acme.com$")
    private String email;
    @NotEmpty
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String password;

    private String role = "USER";

    @OneToMany
    @JoinColumn(name = "employee")
    private List<Salary> salaries = new ArrayList<>();

    public User() {
    }

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email.toLowerCase();
        this.password = password;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

}
