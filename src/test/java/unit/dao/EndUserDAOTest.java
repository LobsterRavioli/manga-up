package unit.dao;

import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
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
import utils.Utils;

import javax.sql.DataSource;
import java.util.Date;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EndUserDAOTest {
    private static IDatabaseTester tester;
    private static EndUserDAO endUserDAO;

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
                .build(EndUserDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("enduser_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        endUserDAO = new EndUserDAO(ds);
    }


    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6})")
    @MethodSource("createTestFailProvider")
    void createTestFailInvalidInput(String testName, String name, String surname, String email, String phoneNumber, String password, Date birthDate) throws Exception {
        EndUser user = new EndUser(name, surname, email, phoneNumber, password, birthDate);
        Assert.assertThrows(IllegalArgumentException.class, () ->endUserDAO.create(user));
    }

    private static Stream<Arguments> createTestFailProvider() {

        return Stream.of(
                Arguments.of("Test case creazione utente fallita Nome non valido", "", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Nome non valido", null, "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Nome non valido", "awrkjhqrwokpjhfewiqfhoiwqhoqwpihoiqwhhoidfq", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Cognome non valido", "Tommaso", "", "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Cognome non valido", "Tommaso", "dfaslkndsalkjhfaklholikhadosiufphdsopfhjapophsdifhoies", "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Cognome non valido", "Tommaso", null, "tommyrock99@hotmail.it", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Email non valido", "Tommaso", "Sorrentino", "", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Email non valido", "Tommaso", "Sorrentino", "fasjklhkldahjfiaeshifkowaeuhiuweqsifoqwiuhfaeqwfhflekahkajshfalskjhasflkjhklsajfsahjkfhjksaljkfhasjkafhshjklsaflhafsjkjhksfjkhaddsdas", "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Email non valido", "Tommaso", "Sorrentino", null, "+393662968496", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Numero cellulare non valido", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Numero cellulare non valido", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", null, "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Numero cellulare non valido", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "sadkljhsalkhjaosiduopsijwopijqdopijwiwjodqwioj", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Numero cellulare non valido", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "3128978912739782317891232378918237912378979", "password!1", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Password non valida", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "dfaslkjdslkajhfpidkjshfpaldkshdslsadlkjaqljkdslkjsdljksadjklasdjkladskjhdsfdohidfsohipafioshp", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Password non valida", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", null, Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Password non valida", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "", Utils.parseDate("2023-01-01")),
                Arguments.of("Test case creazione utente fallita Data non valida", "Tommaso", "Sorrentino", "tommyrock99@hotmail.it", "+393662968496", "password!1",null)
        );
    }


    @Test
    void createTestPass() throws Exception {
        EndUser user = new EndUser("Tommaso", "Sorrentino", "tommy@hotmail.it", "+393662968496","napoli1!", Utils.parseDate("2021-12-01"));
        String cryptPassword = Utils.hash(user.getPassword());
        endUserDAO.create(user);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("enduser_dao/create_pass.xml"));
        String[] ignoreCol = new String[2];
        ignoreCol[0] = "usr_id";
        ignoreCol[1] = "usr_password";
        ITable expectedTable = expectedDataSet.getTable("end_user");
        ITable actualTable = tester.getConnection().createDataSet().getTable("end_user");
        Assertion.assertEqualsIgnoreCols(new SortedTable(expectedTable), new SortedTable(actualTable), ignoreCol);
        Assert.assertTrue(cryptPassword.equals(actualTable.getValue(0, "usr_password")));
        Assert.assertTrue(user.getId() == 1);
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1} )")
    @MethodSource("existEmailInvalidInputProvider")
    void existEmailInvalidInput(String testName, String email) throws Exception {
        Assert.assertThrows(IllegalArgumentException.class, () -> endUserDAO.existEmail(email));
    }

    private static Stream<Arguments> existEmailInvalidInputProvider() {
        return Stream.of(
                Arguments.of("Test case email non valida", ""),
                Arguments.of("Test case email non valida", null),
                Arguments.of("Test case lunghezza email non valida", "fkdsuheioquwfhewieuhuefiwhheiuwefiuhofehiouiquweho3iqeuhfgjkefslkajlea;ijwq;oilufoiwqjfopfiqwjopwqiqwhiofwo")
        );
    }

    @Test
    void existEmailTestInDB() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        Assert.assertTrue(endUserDAO.existEmail("tommyrock99@hotmail.it"));
    }

    @Test
    void existEmailTestNotInDB() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        Assert.assertFalse(endUserDAO.existEmail("lollo@hotmail.it"));
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2})")
    @MethodSource("loginInvalidInputProvider")
    void loginInvalidInput(String testName, String email, String password) throws Exception {
        Assert.assertThrows(IllegalArgumentException.class, () -> endUserDAO.login(email, password));
    }

    private static Stream<Arguments> loginInvalidInputProvider() {
        return Stream.of(
                Arguments.of("Test case email non valida", "", "password!1"),
                Arguments.of("Test case email non valida", null, "password!1"),
                Arguments.of("Test case password non valida", "tommyrock99@hotmail.it", ""),
                Arguments.of("Test case password non valida", "tommyrock99@hotmail,it", null)
        );
    }

    @Test
    void loginPass() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        Assert.assertTrue(endUserDAO.login("tommyrock99@hotmail.it", "password1!") != null);
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2})")
    @MethodSource("loginFailProvider")
    void loginFail(String testName, String email, String password) throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        Assert.assertNull(endUserDAO.login(email, password));
    }

    private static Stream<Arguments> loginFailProvider() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        return Stream.of(
                Arguments.of("Test case email esistente e password non corretta", "tommyrock99@hotmail.it", "password"),
                Arguments.of("Test case email non esistente e password non esistente", "lollo@hotmail.it", "password"),
                Arguments.of("Test case email non esistente e password esistente", "ciu@hotmail.it", "password1!")
        );
    }

    @Test
    void findByIdPass() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        Assert.assertNotNull(endUserDAO.findById(1));
    }

    @ParameterizedTest
    @MethodSource("findByIdFailProvider")
    void findByIdFail(String testName, int id) throws Exception {
        Assert.assertNull(endUserDAO.findById(id));
    }

    private static Stream<Arguments> findByIdFailProvider(){
        return Stream.of(
                Arguments.of("Test case id non esistente", 2),
                Arguments.of("Test case id non valido", 0)
        );
    }
}