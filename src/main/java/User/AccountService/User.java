package User.AccountService.beans;

import java.util.HashSet;
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

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        roles = new HashSet();
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

    public void addRole(Roles role){
        if(!roles.contains(role.getValue()))
            roles.add(role.getValue());
    }

    public void removeRole(Roles role){
        if(roles.contains(role.getValue()))
            roles.remove(role.getValue());
    }

    public Set getRoles()
    {
        return this.roles;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
