package User.AccountService;

import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


public class UserDAO {

    private DataSource ds;

    private static final String USER_TABLE = "users";
    private static final String USER_ROLE_TABLE = "user_roles";

    private static final String ROLE_TABLE = "roles";
    private static final String MANAGES_TABLE = "manages";

    private static final String CREATE = "INSERT INTO "+USER_TABLE+
            " (username, password)"+
            " VALUES (?, ?) ;";
    private static final String DELETE = "DELETE FROM "+USER_TABLE+" WHERE user_name = ? ;";
    private static final String RETRIEVE = "SELECT * FROM "+USER_TABLE+" WHERE user_name = ? ;";

    private static final String RETRIEVE_ALL = "SELECT * "+
                                               "FROM "+USER_TABLE+" AS U";

    private static final String RETRIEVE_ALL_BEGINNERS = "SELECT * "+
            "FROM "+USER_TABLE+" AS U, "+USER_ROLE_TABLE+" AS R "+
            "WHERE U.user_name = R.user_name AND R.role_name = \'ORDER_MANAGER\' AND U.name NOT IN " +
            "(SELECT man_user_name FROM manages) ;";

    private static final String RETRIEVE_MANAGED_SORTED = "SELECT man_user_name, COUNT(*) AS result " +
            "FROM "+MANAGES_TABLE+" GROUP BY man_user_name ORDER BY result";
    private static final String CHECK_USER = "SELECT U.user_name, U.password, R.role_name "+
                                             "FROM "+USER_TABLE+" AS U, "+USER_ROLE_TABLE+" AS R "+
                                             "WHERE U.user_name = R.user_name AND U.user_name = ? AND U.password = ? AND R.role_name = ?";

    private static final String SELECT_ROLE = "SELECT R.role_name "+
                                              "FROM "+ROLE_TABLE+" AS R, "+USER_ROLE_TABLE+" AS U1, "+USER_TABLE+" AS U2 "+
                                              "WHERE R.role_name=U1.role_name AND U1.user_name=U2.user_name AND U2.user_name=?";

    public UserDAO(DataSource ds)
    {
        this.ds = ds;
    }

    public void createUser(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

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
    public void removeUserByUserName(String username) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, username);

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

    public Collection<User> getAllUsers(String role_name) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<User> users = new LinkedList<User>();

        String selectQuery = RETRIEVE_ALL;

        if(role_name != null && !role_name.equals(""))
        {
            selectQuery += ", "+USER_ROLE_TABLE+" AS R WHERE U.user_name=R.user_name AND R.role_name= ?";
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

                userBean.setUsername(rs.getString("username"));
                userBean.setPassword(rs.getString("password"));

                users.add(userBean);
            }
            if(users.size()==0){
                return null;
            }else{
                return users;
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
    }

    public String getTargetOrderManagerUserName() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User user = new User();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_MANAGED_SORTED);
            ResultSet rs = preparedStatement.executeQuery();


            if(rs.next())
                user.setUsername(rs.getString("man_user_name")); // il gestore al quale assegnare il task

            return user.getUsername();
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

    public Collection<String> getRoles(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<String> roles = new LinkedList<String>();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROLE);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
                roles.add(rs.getString("role_name"));
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
        return roles;
    }
    public User getUserByUsername(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User userBean = new User();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
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

    /*
     * Controlla se un user con una certa login e una certa password può accedere al sistema.
     * se la coppia (login, password) è presente nel DB, allora le credenziali di user sono corrette ed ha accesso al sistema
     */
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
}