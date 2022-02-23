package account.business.data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;

    @NotEmpty
    @Pattern(regexp = ".+@acme.com$")
    @Column(unique = true)
    private String email;
    @NotEmpty
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> role = new HashSet<>();

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Set<Salary> salaries = new HashSet<>();

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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

//    public Set<Salary> getSalaries() {
//        return salaries;
//    }
//
//    public void setSalaries(Set<Salary> salaries) {
//        this.salaries = salaries;
//    }

//    public Set<Group> getUserGroups() {
//        return userGroups;
//    }
//
//    public void setUserGroups(Set<Group> userGroups) {
//        this.userGroups = userGroups;
//    }
}
