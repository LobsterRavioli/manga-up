package unit.dao;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utils.DAOException;
import utils.Utils;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditCardDAOTest {
    private static IDatabaseTester tester;
    private static CreditCardDAO creditCardDAO;

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
                .build(CreditCardDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("enduser_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        creditCardDAO = new CreditCardDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4})")
    @MethodSource("invalidParameterProvider")
    void createInvalidCard(String test, String number, String cvv, Date expirationDate, EndUser endUser) throws Exception {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(number);
        creditCard.setCvv(cvv);
        creditCard.setExpirementDate(expirationDate);
        creditCard.setEndUser(endUser);
        Assert.assertThrows(IllegalArgumentException.class, () -> creditCardDAO.create(creditCard));
        ITable actualTable = tester.getConnection().createDataSet().getTable("CREDIT_CARD");
        Assert.assertTrue(actualTable.getRowCount() == 0);
    }

    private static Stream<Arguments> invalidParameterProvider() {
        return Stream.of(
                Arguments.of("Test case lunghezza numero carta non valida", "1", "123", Utils.parseDate("2030-11-16"), new EndUser(1)),
                Arguments.of("Test case formato numero carta non valido ", "abc", "123", Utils.parseDate("2030-11-16"), new EndUser(1)),
                Arguments.of("Test case cvc formato non valido", "1111111111111", "abc", Utils.parseDate("2030-11-16"), new EndUser(1)),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", Utils.parseDate("2030-11-16"), new EndUser(1)),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", Utils.parseDate("2030-11-16"), new EndUser(1)),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", null, new EndUser(1)),
                Arguments.of("Test case ", "1111111111111", "111", Utils.parseDate("2030-11-16"), new EndUser(0)),
                Arguments.of("Test case ", "1111111111111", "111", Utils.parseDate("2030-11-16"), new EndUser(0)),
                Arguments.of("Test case ", "1111111111111", "111", null, new EndUser())
        );
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1})")
    @MethodSource("deleteInvalidProvider")
    void deleteInvalid(String testName, CreditCard card) throws Exception {
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertThrows(IllegalArgumentException.class, () -> creditCardDAO.delete(card));
        ITable actualTable = tester.getConnection().createDataSet().getTable("CREDIT_CARD");
        Assert.assertTrue(actualTable.getRowCount() == 1);
    }


    private static Stream<Arguments> deleteInvalidProvider() {
        return Stream.of(
                Arguments.of("Credit card non valida", new CreditCard(0)),
                Arguments.of("Credit card non valida", new CreditCard(-1)),
                Arguments.of( "Credit card non valida",null)
        );
    }


    @Test
    void deleteFails() throws Exception {
        CreditCard creditCard = new CreditCard(2);
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertThrows(DAOException.class, () -> creditCardDAO.delete(creditCard));
        ITable actualTable = tester.getConnection().createDataSet().getTable("CREDIT_CARD");
        Assert.assertTrue(actualTable.getRowCount() == 1);
    }

    @Test
    void deleteTestPass() throws Exception {
        CreditCard creditCard = new CreditCard(1);
        refreshDataSet("credit_card_dao/populated.xml");
        creditCardDAO.delete(creditCard);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CREDIT_CARD");
        Assert.assertTrue(actualTable.getRowCount() == 0);
    }
    @ParameterizedTest(name = "{index} - {0} (parametri: {1})")
    @MethodSource("invalidFindByIdProvider")
    void findByIdInvalidTest(String testName, int id) throws Exception {
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertThrows(IllegalArgumentException.class, () -> creditCardDAO.findById(id));
    }

    private static Stream<Arguments> invalidFindByIdProvider() {
        return Stream.of(
                Arguments.of("Test case lunghezza numero carta non valida", -1),
                Arguments.of("Test case lunghezza numero carta non valida", 0)
        );
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1})")
    @MethodSource("invalidIdFindAssociatedCards")
    void findAssociatedCardsInvalidInput(String testName, EndUser user) throws Exception {
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertThrows(IllegalArgumentException.class,() -> creditCardDAO.findAssociatedCards(user));
    }

    private static Stream<Arguments> invalidIdFindAssociatedCards() {
        return Stream.of(
                Arguments.of("Test case lunghezza numero carta non valida", null),
                Arguments.of("Test case lunghezza numero carta non valida", new EndUser(0))
        );
    }


    @Test
    void findAssociatedCardsNotExistingUser() throws Exception {
        EndUser user = new EndUser(4);
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertNull(creditCardDAO.findAssociatedCards(user));

    }

    @Test
    void findAssociatedCardsExistingUser() throws Exception {
        EndUser user = new EndUser(1);
        refreshDataSet("credit_card_dao/populated.xml");
        List<CreditCard> cards = creditCardDAO.findAssociatedCards(user);
        CreditCard card = new CreditCard();
        card.setId(1);
        card.setCardHolder("Tommaso Sorrentino");
        card.setExpirementDate(Utils.parseDate("2030-11-16"));
        card.setEndUser(user);
        card.setCvv("123");
        card.setCardNumber("1234567890123456");
        Assert.assertTrue(cards.size() == 1);
        Assert.assertEquals(card, cards.get(0));

    }


    @Test
    void existsCreditCardNumber() throws Exception {
        EndUser user = new EndUser(1);
        refreshDataSet("credit_card_dao/populated.xml");
        List<CreditCard> cards = creditCardDAO.findAssociatedCards(user);
        CreditCard card = new CreditCard();
        card.setId(1);
        card.setCardHolder("Tommaso Sorrentino");
        card.setExpirementDate(Utils.parseDate("2030-11-16"));
        card.setEndUser(user);
        card.setCvv("123");
        card.setCardNumber("1234567890123456");
        Assert.assertTrue(cards.size() == 1);
        Assert.assertEquals(card, cards.get(0));
    }

    @Test
    void notExistsCreditCardNumberNotExistingCard() throws Exception {
        EndUser user = new EndUser(1);
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertTrue(creditCardDAO.existsCreditCardNumber("1234567890123456"));
    }

    @Test
    void notExistsCreditCardExistingCard() throws Exception {
        EndUser user = new EndUser(1);
        refreshDataSet("credit_card_dao/populated.xml");
        Assert.assertFalse(creditCardDAO.existsCreditCardNumber("1234567890123457"));
    }
}