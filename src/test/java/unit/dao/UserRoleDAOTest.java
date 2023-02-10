package unit.dao;

import User.AccountService.CreditCardDAO;
import User.AccountService.EndUserDAO;
import User.AccountService.User;
import User.AccountService.UserRoleDAO;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utils.DAOException;

import javax.sql.DataSource;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRoleDAOTest {


    private static IDatabaseTester tester;
    private UserRoleDAO userRoleDAO;

    @BeforeAll
    void setUpAll() throws ClassNotFoundException {
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
                .build(UserRoleDAO.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("user_role_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        userRoleDAO = new UserRoleDAO(ds);
    }

    @Test
    void getRolesExistsUser() {
        Assert.assertTrue(userRoleDAO.getRoles(new User("Tommaso")).contains("ORDER_MANAGER"));
    }

    @Test
    void getRolesNotExistingUser() {
        Assert.assertNull(userRoleDAO.getRoles(new User("Mario")));
    }

    @ParameterizedTest
    @MethodSource("getRolesInvalidExistedUserProvider")
    void getRolesInvalidExistedUser(String test, User user) {

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userRoleDAO.getRoles(user);
        });
    }
    private static Stream<Arguments> getRolesInvalidExistedUserProvider() {
        return Stream.of(
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido", new User()),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",null)
        );
    }


    @Test
    void setRolesNotExistingUser() throws Exception {
        Assert.assertThrows(DAOException.class, () ->{userRoleDAO.setRoles(new User("Mario"), new String[]{"ORDER_MANAGER"});});
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("user_role_dao/expected.xml"));

        ITable actualTable = tester.getConnection().createDataSet().getTable("US_ERS");
        ITable expectedTable = expectedDataSet.getTable("US_ERS");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
        actualTable = tester.getConnection().createDataSet().getTable("RO_LES");
        expectedTable = expectedDataSet.getTable("RO_LES");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }

    @Test
    void setRolesNot() {
        userRoleDAO.setRoles(new User("Mario"), new String[]{"ORDER_MANAGER"});
    }

    @ParameterizedTest
    @MethodSource("setRolesInvalidParametersProvider")
    void setRolesInvalidParameters(String testName, User user, String[] role) {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userRoleDAO.setRoles(user, role);
        });
    }

    private static Stream<Arguments> setRolesInvalidParametersProvider() {
        return Stream.of(
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido", new User(), new String[]{"ORDER_MANAGER"}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",null, new String[]{"ORDER_MANAGER"}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",new User("Tommaso"), new String[]{}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",new User("Tommaso"), new String[]{""}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",new User("Tommaso"), new String[]{"ORDER_MANAGER", ""}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",new User("Tommaso"), new String[]{"ORDER_MANAGER", null}),
                Arguments.of("Test case getRolesInvalidExistedUser fallita Use non valido",new User("Tommaso"), null)
        );
    }

}