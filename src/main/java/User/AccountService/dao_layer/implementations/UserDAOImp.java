
package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.User;
import User.AccountService.dao_layer.interfaces.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


public class UserDAOImp implements UserDAO {

    private DataSource ds;

    private static final String USER_TABLE = "User";
    private static final String USER_ROLE_TABLE = "user_roles";

    private static final String RETRIEVE_ALL = "SELECT * "+
                                               "FROM "+USER_TABLE+" AS U";

    public UserDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    @Override
    public void create(User user) throws SQLException {

    }

    @Override
    public void delete(User user) throws SQLException {

    }

    @Override
    public User getUserById(int id) throws SQLException {
        return null;
    }

    @Override
    public User checkUsername(String login) throws SQLException {
        return null;
    }

    @Override
    public Collection<User> getAllUsers(String role_name) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<User> users = new LinkedList<User>();

        String selectQuery = RETRIEVE_ALL;

        if(role_name != null && !role_name.equals(""))
        {
            selectQuery += ", "+USER_ROLE_TABLE+" AS R WHERE U.id=R.user_id AND R.role_name= ?";
        }

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);

            if(role_name != null && !role_name.equals(""))
            {
                preparedStatement.setString(1, role_name);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                User userBean = new User();

                userBean.setId(rs.getInt("id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setPassword(rs.getString("password"));

                users.add(userBean);
            }
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            finally
            {
                if(connection != null)
                    connection.close();
            }
        }

        return users;
    }
}