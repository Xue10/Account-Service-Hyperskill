package account.business.data;

import javax.persistence.*;

@Entity
@Table
public class RoleGroup {
    @Id
    private String name;

    public RoleGroup() {
    }

    public RoleGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
