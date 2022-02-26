package account.business.data;

public class LockUnlock {
    private String user;
    private String operation;

    public LockUnlock() {
    }

    public LockUnlock(String user, String operation) {
        this.user = user;
        this.operation = operation;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public String getOperation() {
        return operation;
    }
}
