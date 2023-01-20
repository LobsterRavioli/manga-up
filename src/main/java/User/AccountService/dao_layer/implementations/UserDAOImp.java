
package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.User;
import User.AccountService.dao_layer.interfaces.UserDAO;
import utils.DAOException;

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

    private static final String MANAGES_TABLE = "manages";

    private static final String CREATE = "INSERT INTO "+USER_TABLE+
            " (id, username, password)"+
            " VALUES (?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+USER_TABLE+" WHERE id = ? ;";
    private static final String RETRIEVE = "SELECT * FROM "+USER_TABLE+" WHERE id = ? ;";

    private static final String RETRIEVE_BY_USERNAME = "SELECT * FROM "+USER_TABLE+" WHERE username = ? ;";
    private static final String RETRIEVE_ALL = "SELECT * "+
                                               "FROM "+USER_TABLE+" AS U";

    private static final String RETRIEVE_ALL_BEGINNERS = "SELECT * "+
            "FROM "+USER_TABLE+" AS U, "+USER_ROLE_TABLE+" AS R "+
            "WHERE U.id = R.user_id AND R.role_name = \'ORDER_MANAGER\' AND U.id NOT IN " +
            "(SELECT man_user_id FROM manages) ;";

    private static final String RETRIEVE_MANAGED_SORTED = "SELECT man_user_id, COUNT(*) AS result " +
            "FROM "+MANAGES_TABLE+" GROUP BY man_user_id ORDER BY result";
    private static final String CHECK_USER = "SELECT U.username, U.password, R.role_name "+
                                             "FROM "+USER_TABLE+" AS U, "+USER_ROLE_TABLE+" AS R "+
                                             "WHERE U.id = R.user_id AND U.username = ? AND U.password = ? AND R.role_name = ?";

    public UserDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    @Override
    public void create(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating user failed, no rows affected");

            connection.commit();
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
    }

    @Override
    public void delete(User user) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Deleting user failed, no rows affected.");
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
    }

    @Override
    public User getUserById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User userBean = new User();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                userBean.setId(rs.getInt("id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setPassword(rs.getString("password"));
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

        return userBean;
    }

    @Override
    public User checkUsername(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User userBean = new User();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_BY_USERNAME);
            preparedStatement.setString(1, login);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                userBean.setId(rs.getInt("id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setPassword(rs.getString("password"));
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

        return userBean;
    }

    @Override
    public boolean checkUser(User user, String roleToLog) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if(user == null)
            return false;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, roleToLog);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // false if there are no rows in the result set
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

    @Override
    public Collection<User> getAllBeginnerOrderManagers() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<User> users = new LinkedList<User>();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_ALL_BEGINNERS);
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

    @Override
    public int getTargetOrderManagerId() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User user = new User();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_MANAGED_SORTED);
            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next())
                user.setId(rs.getInt("man_user_id")); // il gestore al quale assegnare il task
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
        return user.getId();
    }
}