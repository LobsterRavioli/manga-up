package User.AccountService.dao_layer.interfaces;


import User.AccountService.beans.User;

import java.util.Collection;

public interface UserRoleDAO {


    public Collection getRoles(User user);
    public void setRoles(User user, Collection roles);


}

