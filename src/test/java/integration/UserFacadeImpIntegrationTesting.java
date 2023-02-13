package integration;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import utils.Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserFacadeImpIntegrationTesting {

    private DataSource ds;
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;
    private EndUserDAO endUserDAO;
    private UserFacadeImp userFacadeImp;
    private static IDatabaseTester tester;


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
                .build(LoginEndUserIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    void setUp() throws Exception {
        ds = Mockito.mock(DataSource.class);
        when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        userDAO = new UserDAO(ds);
        addressDAO = new AddressDAO(ds);
        creditCardDAO = new CreditCardDAO(ds);
        endUserDAO = new EndUserDAO(ds);
        userFacadeImp = new UserFacadeImp(userDAO, addressDAO, creditCardDAO, endUserDAO);
        refreshDataSet("user_facade/registration_facade_init.xml");
        Mockito.spy(userFacadeImp);

    }

    @ParameterizedTest(name = "registrationPass: {0}")
    @MethodSource("registrationFail")
    void registrationFail(String testName, Address address, CreditCard card, EndUser user) throws Exception {
        user.addAddress(address);
        user.addCard(card);
        Assert.assertThrows(Exception.class, () -> userFacadeImp.registration(user));
    }

    private static Stream<Arguments> registrationFail() {

        Address validAddress = new Address("Italia","Campania","Napoli","Via guinceri 31","80040");
        Address a1 = new Address("","Campania","Napoli","Via guinceri 31","80040");
        Address a2 = new Address("Italia","","Napoli","Via guinceri 31","80040");
        Address a3 = new Address("Italia","Campania","","Via guinceri 31","80040");
        Address a4 = new Address("Italia","Campania","Napoli","","80040");
        Address a5 = new Address("Italia","Campania","Napoli","Via guinceri 31","");

        EndUser validUser = new EndUser("Tommaso", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e1 = new EndUser("", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e2 = new EndUser("", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e3 = new EndUser("Mario", "", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e4 = new EndUser("Mario", "", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e5 = new EndUser("Mario", "Rossi", "","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e6 = new EndUser("Mario", "Rossi", "","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e7 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e8 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e9 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "", Utils.parseDate("1999-01-01"));
        EndUser e10 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e11 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", null);

        CreditCard validCard = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c1 = new CreditCard(1, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c2 = new CreditCard(0, "", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c3 = new CreditCard(0, null, new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c4 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c5 = new CreditCard(0, "123", new EndUser(1), "", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c6 = new CreditCard(0, "123", new EndUser(1), null, "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c7 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c8 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "",Utils.parseDate("2030-01-01"));
        CreditCard c9 = new CreditCard(0, "123", new EndUser(1), "1111111111111", null,Utils.parseDate("2030-01-01"));
        CreditCard c10 = new CreditCard(0, "123", new EndUser(1), "1111111111111", null,Utils.parseDate("2000-01-01"));
        CreditCard c11 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",null);



        return Stream.of(
                Arguments.of("Indirizzo non valido", a1, validCard, validUser),
                Arguments.of("Indirizzo non valido", a2, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a3, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a4, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a5, validCard, validUser),
                Arguments.of( "Utente non valido", validAddress, validCard, e1),
                Arguments.of( "Utente non valido", validAddress, validCard, e2),
                Arguments.of( "Utente non valido", validAddress, validCard, e3),
                Arguments.of( "Utente non valido", validAddress, validCard, e4),
                Arguments.of( "Utente non valido", validAddress, validCard, e5),
                Arguments.of( "Utente non valido", validAddress, validCard, e6),
                Arguments.of( "Utente non valido", validAddress, validCard, e7),
                Arguments.of( "Utente non valido", validAddress, validCard, e8),
                Arguments.of( "Utente non valido", validAddress, validCard, e9),
                Arguments.of( "Utente non valido", validAddress, validCard, e10),
                Arguments.of( "Utente non valido", validAddress, validCard, e11),
                Arguments.of( "Carta non valida", validAddress, c1, validUser),
                Arguments.of( "Carta non valida", validAddress, c2, validUser),
                Arguments.of( "Carta non valida", validAddress, c3, validUser),
                Arguments.of( "Carta non valida", validAddress, c4, validUser),
                Arguments.of( "Carta non valida", validAddress, c5, validUser),
                Arguments.of( "Carta non valida", validAddress, c5, validUser),
                Arguments.of( "Carta non valida", validAddress, c6, validUser),
                Arguments.of( "Carta non valida", validAddress, c7, validUser),
                Arguments.of( "Carta non valida", validAddress, c8, validUser),
                Arguments.of( "Carta non valida", validAddress, c9, validUser),
                Arguments.of( "Carta non valida", validAddress, c10, validUser),
                Arguments.of( "Carta non valida", validAddress, c11, validUser)

        );
    }

    @Test
    void registrationPass() throws Exception {
        doAnswer(new Answer()
        {
            @Override
            public Connection answer(InvocationOnMock invocationOnMock) throws Throwable {
                return tester.getConnection().getConnection();
            }
        }).when(ds).getConnection();

        EndUser validUser = new EndUser("Tommaso", "Sorrentino", "tommy@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        CreditCard validCard =  new CreditCard(0, "123", null, "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        Address validAddress = new Address();
        validAddress.setCountry("Italia");
        validAddress.setCity("Napoli");
        validAddress.setStreet("Via Roma 24");
        validAddress.setPostalCode("80100");
        validAddress.setRegion("Campania");
        validAddress.setPhoneNumber("+393662968496");

        validUser.addCard(validCard);
        validUser.addAddress(validAddress);
        userFacadeImp.registration(validUser);

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("user_facade/registration_facade_expected.xml"));

        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        Assertion.assertEquals(new SortedTable(expectedDataSet.getTable("address")), new SortedTable(actualTable));

        actualTable = tester.getConnection().createDataSet().getTable("credit_card");
        Assertion.assertEquals(new SortedTable(expectedDataSet.getTable("credit_card")), new SortedTable(actualTable));

        actualTable = tester.getConnection().createDataSet().getTable("end_user");
        Assertion.assertEquals(new SortedTable(expectedDataSet.getTable("end_user")), new SortedTable(actualTable));

    }



}