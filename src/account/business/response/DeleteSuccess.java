package account.business.response;

public class DeleteSuccess {
    private String user;
    private String status = "Deleted successfully!";

    public DeleteSuccess(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }
}
