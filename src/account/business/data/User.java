package account.business.data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
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
    @OneToMany
    private Set<RoleGroup> role;

    @OneToMany
    @JoinColumn(name = "user_email")
    private List<Salary> salaries = new ArrayList<>();

//
//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @JoinTable(name = "user_groups",
//            joinColumns =@JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id"
//            ))
//    private Set<Group> userGroups= new HashSet<>();

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

    public Set<RoleGroup> getRole() {
        return role;
    }

    public void setRole(Set<RoleGroup> role) {
        this.role = role;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

//    public Set<Group> getUserGroups() {
//        return userGroups;
//    }
//
//    public void setUserGroups(Set<Group> userGroups) {
//        this.userGroups = userGroups;
//    }
}
