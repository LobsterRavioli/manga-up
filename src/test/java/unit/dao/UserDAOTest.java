package unit.dao;

import User.AccountService.*;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utils.Utils;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private static IDatabaseTester tester;

    private static UserDAO userDAO;
    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema_db/schema.sql'",
                "prova",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB mantenendo lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(UserDAO.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("user_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        userDAO = new UserDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }


    @ParameterizedTest(name = "Test {index}: {0}, {1}, {2}")
    @MethodSource("createInvalidInputProvider")
    void createInvalidInputUser(String testName, String username, String password) throws Exception {
        refreshDataSet("user_dao/invalid.xml");
        assertThrows(IllegalArgumentException.class, () -> userDAO.createUser(new User(username, password)));
        ITable actualTable = tester.getConnection().createDataSet().getTable("US_ERS");
        Assert.assertTrue(actualTable.getRowCount() == 1);
    }



    private static Stream<Arguments> createInvalidInputProvider() {
        return Stream.of(
                Arguments.of("Test case user non valido", null, "password!1"),
                Arguments.of("Test case user non valido", "", "password!1"),
                Arguments.of("Test case user non valido", "12345678901", "password!1"),
                Arguments.of("Test case user non valido", "tommasosorr", "password"),
                Arguments.of("Test case user non valido", "tommaso1", null),
                Arguments.of("Test case user non valido", "tommaso1", "")
        );
    }

    @Test
    void createUserPass() throws Exception {
        User user = new User();
        user.setUsername("tommasos");
        user.setPassword("password!1");
        userDAO.createUser(user);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(UserDAO.class.getClassLoader().getResourceAsStream("user_dao/create_user_pass.xml"));
        ITable actualTable = tester.getConnection().createDataSet().getTable("US_ERS");

        Assertion.assertEquals(new SortedTable(expectedDataSet.getTable("US_ERS")), new SortedTable(actualTable));

    }

    @Test
    void createUserFailAlreadyExisted(){
        Assert.assertThrows(SQLException.class, ()->userDAO.createUser(new User("Giulia", "password!1")));
    }

    @Test
    void removeUserByUserName() {

    }

    @Test
    void getAllUsers() {

    }

    @Test
    void getAllBeginnerOrderManagersPass() throws SQLException {

        Collection<User> managers = new LinkedList<>();

        User user1 = new User("Tommaso", "password1");
        User user2 = new User("Giacomo", "password2");
        User user3 = new User("Sara", "password3");

        managers.add(user1);
        managers.add(user2);
        managers.add(user3);

        Collection<User> actual = userDAO.getAllBeginnerOrderManagers();
        Assert.assertEquals(managers, actual);
    }

    @Test
    void getTargetOrderManagerUserName() throws SQLException {

        String orderManagerUserName = "Tommaso";

        String actual = userDAO.getTargetOrderManagerUserName();
        Assert.assertEquals(orderManagerUserName, actual);
    }

    @Test
    void getRoles() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void checkUser() {
    }

    @Test
    void existsUsername() {
    }
}