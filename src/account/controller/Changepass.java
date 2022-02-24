package account.controller;

public class Changepass {
    private String email;
    private String status = "The password has been updated successfully";

    public Changepass(String email) {
        this.email = email.toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}

