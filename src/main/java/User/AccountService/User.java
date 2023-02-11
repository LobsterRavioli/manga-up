package User.AccountService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
public class User {

    private int id;
    private String username;
    private String password;
    private Set roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        roles = new HashSet();
    }

    public User(String username) {
        this.username = username;
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

    public void addRole(String role) {
        if (!roles.contains(role))
            roles.add(role);
    }

    public void removeRole(String role) {
        if (roles.contains(role))
            roles.remove(role);
    }

    public Set getRoles() {
        return this.roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean validateUser()
    {
        if(username == null || username.trim().equals("") || username.length() > 50
                || password == null || password.trim().equals("") || password.length() > 255)
            return false;
        else
            return true;
    }

    public static final String PASSWORD_FORMAT = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    public static final String USER_FORMAT = "^([a-zA-Z0-9_-]){5,10}$";


    public void setRoles(Set roles) {
        this.roles = roles;
    }


}
