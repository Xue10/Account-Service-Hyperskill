package account.business.data;

public class RoleOperation {
    private String user;
    private String role;
    private String operation;

    public RoleOperation() {
    }

    public RoleOperation(String user, String role, String operation) {
        this.user = user;
        this.role = role;
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
