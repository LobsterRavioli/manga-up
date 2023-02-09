package unit.dao;

import User.AccountService.EndUserDAO;
import User.AccountService.UserDAO;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.sql.DataSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private static IDatabaseTester tester;

    private UserDAO userDAO;
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

    @Test
    @ParameterizedTest(name = "Test {index}: {0}")
    @MethodSource("createInvalidInputProvider")
    void createInvalidInputUser() {


    }

    private static Stream<Arguments> createInvalidInputProvider() {
        return Stream.of(
                Arguments.of("User1", "Pa"));



    }




    @Test
    void removeUserByUserName() {

    }

    @Test
    void getAllUsers() {

    }

    @Test
    void getAllBeginnerOrderManagers() {
    }

    @Test
    void getTargetOrderManagerUserName() {
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