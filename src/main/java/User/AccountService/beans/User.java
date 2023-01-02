package User.AccountService.beans;

public abstract class User {
    public static final String END_USER = "ENDUSER";
    public static final String CATALOG_MANAGER = "CATALOGMANAGERUSER";
    public static final String ORDER_MANAGER = "ORDERMANAGERUSER";

    protected Long id;
    protected String username;
    protected String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String getRole();
}
