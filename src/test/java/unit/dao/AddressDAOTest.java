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
class AddressDAOTest {
    private static IDatabaseTester tester;
    private static AddressDAO addressDAO;

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
                .build(AddressDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("address_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        addressDAO = new AddressDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6})")
    @MethodSource("createTestFailProvider")
    void createTestFailInvalidInput(String testName, String country, String city, String street, String postalCode, String phoneNumber, String region) throws Exception {
        refreshDataSet("address_dao/populated.xml");
        EndUser endUser = new EndUser(1);
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setPhoneNumber(phoneNumber);
        address.setRegion(region);
        address.setEndUser(endUser);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressDAO.create(address));
        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        Assert.assertTrue(actualTable.getRowCount() == 1);
    }

    private static Stream<Arguments> createTestFailProvider() {
        return Stream.of(
                Arguments.of("Test case creazione address fallita Nome non valido", "", "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", null, "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", null, "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "123", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "Napoli", "", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "Napoli", null, "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via Roma 35", "0010092813982", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", "abc", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", "1", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", null, "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", "abc", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", "", "Campania"),
                Arguments.of("Test case creazione address fallita Numero carta non valido", "Italia", "Napoli", "Via roma 35", "00100", "2187361892736218697", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", null, "Campania"),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", "123"),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", ""),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", null)
        );
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1})")
    @MethodSource("deleteInvalidProvider")
    void deleteInvalid(String testName, Address address) throws Exception {
        refreshDataSet("address_dao/populated.xml");
        Assert.assertThrows(IllegalArgumentException.class, () -> addressDAO.delete(address));
        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        Assert.assertTrue(actualTable.getRowCount() == 1);
    }

    private static Stream<Arguments> deleteInvalidProvider() {
        return Stream.of(
                Arguments.of("Indirizzo non valido", new Address(0)),
                Arguments.of("Indirizzo non valido", new Address(-1)),
                Arguments.of( "Indirizzo non valido",null)
        );
    }
    @Test
    void findAssociatedAddressesExistingUser() throws Exception {
        EndUser user = new EndUser(1);
        refreshDataSet("address_dao/populated.xml");
        Address address = new Address();
        address.setId(24);
        address.setCountry("Italia");
        address.setCity("Napoli");
        address.setRegion("Campania");
        address.setStreet("Via Roma 24");
        address.setPostalCode("80100");
        address.setPhoneNumber("+393662968496");
        address.setEndUser(user);
        Assert.assertEquals(address, addressDAO.findAssociatedAddresses(user).iterator().next());
    }

    @Test
    void findAssociatedAddressesNotExistingUser() throws Exception {
        EndUser user = new EndUser(4);
        refreshDataSet("address_dao/populated.xml");
        Assert.assertNull(addressDAO.findAssociatedAddresses(user));
    }

    @Test
    void findByIdValidInputExistingUser() throws Exception {
        refreshDataSet("address_dao/populated.xml");
        EndUser user = new EndUser(1);
        Address address = new Address();
        address.setId(24);
        address.setCountry("Italia");
        address.setCity("Napoli");
        address.setRegion("Campania");
        address.setStreet("Via Roma 24");
        address.setPostalCode("80100");
        address.setPhoneNumber("+393662968496");
        address.setEndUser(user);
        Assert.assertTrue(addressDAO.findById(1).equals(address));
    }

    @Test
    void findByIdValidInputNotExistingAddress() throws Exception {
        refreshDataSet("address_dao/populated.xml");
        Assert.assertNull(addressDAO.findById(50));
    }

    @Test
    void findByIdInvalidInput() {
        Assert.assertThrows(IllegalArgumentException.class, () -> addressDAO.findById(-1));
    }

    @Test
    void createTestPass() throws Exception {
        refreshDataSet("address_dao/actual.xml");
        Address address = new Address();
        address.setCountry("Italia");
        address.setCity("Napoli");
        address.setRegion("Campania");
        address.setStreet("Via Roma 24");
        address.setPostalCode("80100");
        address.setPhoneNumber("+393662968496");
        address.setEndUser(new EndUser(1));
        addressDAO.create(address);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("address_dao/expected.xml"));
        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        Assertion.assertEquals(expectedDataSet.getTable("address"), actualTable);

    }
}