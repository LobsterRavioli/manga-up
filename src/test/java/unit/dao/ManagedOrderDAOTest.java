package unit.dao;

import Order.DispatchService.ManagedOrder;
import Order.DispatchService.ManagedOrderDAO;
import User.AccountService.Address;
import User.AccountService.CreditCard;
import User.AccountService.EndUser;
import User.AccountService.User;
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

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.stream.Stream;


class ManagedOrderDAOTest {

    private static IDatabaseTester tester;
    private static ManagedOrderDAO managedOrderDAO;

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
                .build(ManagedOrderDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo  stato iniziale di default
        refreshDataSet("managedorder_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        managedOrderDAO = new ManagedOrderDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }


    @Test
    void createPass() throws Exception {

        EndUser endUser = Mockito.mock(EndUser.class);
        Mockito.when(endUser.getId()).thenReturn(136);

        Address endUserAddress = Mockito.mock(Address.class);
        Mockito.when(endUserAddress.toString()).thenReturn("Address info fittizie");

        CreditCard endUserCreditCard = Mockito.mock(CreditCard.class);
        Mockito.when(endUserCreditCard.toString()).thenReturn("Credit card info fittizie");

        User manager = Mockito.mock(User.class);
        Mockito.when(manager.getUsername()).thenReturn("Giovanni");

        ManagedOrder managedOrder = new ManagedOrder(endUser, endUserAddress, endUserCreditCard, manager,
                Date.valueOf("2018-05-07"), "ABC123KL", "BRT", Date.valueOf("2018-05-10"));

        managedOrder.setId(37);
        managedOrder.setOrderDate(Date.valueOf("2018-05-07"));
        managedOrder.setTotalPrice(9.99);

        ITable expected = new FlatXmlDataSetBuilder().build(OrderDAOTest.class.getClassLoader()
                        .getResourceAsStream("managedorder_dao/managed_order_create_pass.xml"))
                        .getTable("manages");

        managedOrderDAO.create(managedOrder);
        ITable actual = tester.getConnection().createDataSet().getTable("manages");
        Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual));
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6})")
    @MethodSource("createTestFailProvider")
    void createTestFail(String testName, String orderManagerName, long orderId, Date shipmentDate, String trackNumber, String courier, Date deliveryDate) {

        ManagedOrder managedOrder = new ManagedOrder();
        managedOrder.setUserName(orderManagerName);
        managedOrder.setId(orderId);
        managedOrder.setShipmentDate(shipmentDate);
        managedOrder.setTrackNumber(trackNumber);
        managedOrder.setCourierName(courier);
        managedOrder.setDeliveryDate(deliveryDate);

        Assert.assertThrows(IllegalArgumentException.class, () -> managedOrderDAO.create(managedOrder));
    }

    private static Stream<Arguments> createTestFailProvider() {
        return Stream.of(Arguments.of("Test case gestione ordine fallita, username non valido", null, 37, Date.valueOf("2018-05-07"), "ABC123KL", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, username non valido", " ", 37, Date.valueOf("2018-05-07"), "ABC123KL", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, id end user non valido", "Giovanni", 0, Date.valueOf("2018-05-07"), "ABC123KL", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, data di spedizione non valida", "Giovanni", 37, null, "ABC123KL", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, data di spedizione non valida", "Giovanni", 37, Date.valueOf("2018-02-04"), "ABC123KL", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, tracking number non valido", "Giovanni", 37, Date.valueOf("2018-05-07"), null, "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, tracking number non valido", "Giovanni", 37, Date.valueOf("2018-05-07"), " ", "BRT", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, nome corriere non valido", "Giovanni", 37, Date.valueOf("2018-05-07"), "ABC123KL", null, Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, nome corriere non valido", "Giovanni", 37, Date.valueOf("2018-05-07"), "ABC123KL", " ", Date.valueOf("2018-05-10")),
                Arguments.of("Test case gestione ordine fallita, data di consegna non valida", "Giovanni", 37, Date.valueOf("2018-05-07"), "ABC123KL", "BRT", null),
                Arguments.of("Test case gestione ordine fallita, data di consegna non valida", "Giovanni", 37, Date.valueOf("2018-05-07"), "ABC123KL", "BRT", Date.valueOf("2018-05-03"))
        );
    }

    @Test
    void retrieveByOrderPass() throws Exception {

        ManagedOrder managedOrder = new ManagedOrder();

        managedOrder.setId(21);
        managedOrder.setOrderDate(Date.valueOf("2018-10-07"));
        managedOrder.setTotalPrice(4.50);
        managedOrder.setEndUserID(45);
        managedOrder.setAddressEndUserInfo("Address info fittizie");
        managedOrder.setCreditCardEndUserInfo("Credit card info fittizie");
        managedOrder.setUserName("Sara");
        managedOrder.setShipmentDate(Date.valueOf("2018-10-09"));
        managedOrder.setTrackNumber("AFJ573LU");
        managedOrder.setCourierName("DHL");
        managedOrder.setDeliveryDate(Date.valueOf("2018-10-15"));

        ManagedOrder actual = managedOrderDAO.retrieveByOrder(21);

        Assert.assertEquals(managedOrder, actual);
    }

    @Test
    void retrieveByOrderFail() throws SQLException {

        ManagedOrder actual = managedOrderDAO.retrieveByOrder(1000);
        Assert.assertEquals(null, actual);
    }
}