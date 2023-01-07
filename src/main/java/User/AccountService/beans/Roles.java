package User.AccountService.beans;

public enum Roles {
    END_USER("EU"),
    CATALOG_MANAGER("CM"),
    ORDER_MANAGER("OM"),
    USER_MANAGER("UM");
    private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
