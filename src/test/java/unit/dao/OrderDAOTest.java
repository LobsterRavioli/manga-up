package unit.dao;

import Order.DispatchService.Order;
import Order.DispatchService.OrderDAO;
import User.AccountService.Address;
import User.AccountService.CreditCard;
import User.AccountService.EndUser;
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
import utils.DAOException;

import javax.sql.DataSource;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

class OrderDAOTest {

    private static IDatabaseTester tester;
    private static OrderDAO orderDAO;

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
                .build(OrderDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo  stato iniziale di default
        refreshDataSet("order_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        orderDAO = new OrderDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void createTestPass() throws Exception {

        EndUser endUser = Mockito.mock(EndUser.class);
        Mockito.when(endUser.getId()).thenReturn(101);

        Address endUserAddress = Mockito.mock(Address.class);
        Mockito.when(endUserAddress.toString()).thenReturn("Address info fittizie");

        CreditCard endUserCreditCard = Mockito.mock(CreditCard.class);
        Mockito.when(endUserCreditCard.toString()).thenReturn("Credit card info fittizie");

        Order order = new Order(endUser, endUserAddress, endUserCreditCard);

        order.setOrderDate(Date.valueOf("2018-05-06"));
        order.setTotalPrice(10.50);

        ITable expected = new FlatXmlDataSetBuilder().build(OrderDAOTest.class.getClassLoader().getResourceAsStream("order_dao/order_create_pass.xml"))
                        .getTable("Orders");

        orderDAO.create(order);

        ITable actual = tester.getConnection().createDataSet().getTable("Orders");

        String[] ignoreCol = new String[1];
        ignoreCol[0] = "ord_id";
        Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6})")
    @MethodSource("createTestFailProvider")
    void createTestFail(String testName, Date orderDate, String state, double totalPrice, int endUserId, String addressInfo, String creditCardInfo) throws Exception {

        Order order = new Order();

        order.setOrderDate(orderDate);
        order.setState(state);
        order.setTotalPrice(totalPrice);
        order.setEndUserID(endUserId);
        order.setAddressEndUserInfo(addressInfo);
        order.setCreditCardEndUserInfo(creditCardInfo);

        Assert.assertThrows(IllegalArgumentException.class, () -> orderDAO.create(order));
    }

    private static Stream<Arguments> createTestFailProvider() {
        return Stream.of(Arguments.of("Test case creazione ordine fallita, data ordine non valida", null, "TO_SEND", 50.00, 25, "Address info", "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, stato ordine non valido", Date.valueOf("2019-07-03"), "SENT", 50.00, 25, "Address info", "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, prezzo totale non valido", Date.valueOf("2019-07-03"), "TO_SEND", 0.00, 25, "Address info", "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, prezzo totale non valido", Date.valueOf("2019-07-03"), "TO_SEND", -37.99, 25, "Address info", "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, end user id non valido", Date.valueOf("2019-07-03"), "TO_SEND", 50.00, -1, "Address info", "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, address info non valide", Date.valueOf("2019-07-03"), "TO_SEND", 50.00, 25, null, "Credit card info"),
                         Arguments.of("Test case creazione ordine fallita, credit card info non valide", Date.valueOf("2019-07-03"), "TO_SEND", 50.00, 25, "Address info", null)
        );
    }

    @Test
    void deleteExistingOrder() throws Exception {

        ITable expected = new FlatXmlDataSetBuilder().
                build(OrderDAO.class.getClassLoader().getResourceAsStream("order_dao/delete_order_pass.xml")).
                getTable("Orders");

        Order order = new Order();
        order.setId(29);

        orderDAO.delete(order);

        ITable actual = tester.getConnection().createDataSet().getTable("Orders");
        Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual));
    }

    @Test
    void deleteNotExistingOrder() throws Exception {

        Order order = new Order();
        order.setId(100);

        Assert.assertThrows(DAOException.class, () -> orderDAO.delete(order));
    }

    @Test
    void updateExistingOrder() throws Exception{

        ITable expected = new FlatXmlDataSetBuilder().
                build(OrderDAO.class.getClassLoader().getResourceAsStream("order_dao/update_order_pass.xml")).
                getTable("Orders");

        Order order = new Order();
        order.setId(29);
        order.setState("SENT");

        orderDAO.update(order);

        ITable actual = tester.getConnection().createDataSet().getTable("Orders");
        Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual));
    }

    @Test
    void updateNotExistingOrder() {

        Order order = new Order();
        order.setId(100);

        Assert.assertThrows(DAOException.class, () -> orderDAO.update(order));
    }


    @Test
    void retrieveOrderPass() throws SQLException {

        Order order = new Order();
        order.setId(33);

        Order actual = orderDAO.retrieve(33);
        Assert.assertEquals(order.getId(), actual.getId());
    }

    @Test
    void retrieveOrderFail() throws SQLException {
        Order actual = orderDAO.retrieve(1000);
        Assert.assertEquals(null, actual);
    }

    @Test
    void doRetrieveAll() throws SQLException {

        Collection<Order> orders = new LinkedList<>();

        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();

        o1.setId(29);
        o1.setOrderDate(Date.valueOf("2018-05-06"));
        o1.setState("TO_SEND");
        o1.setTotalPrice(10.50);
        o1.setEndUserID(136);
        o1.setAddressEndUserInfo("Address info fittizie");
        o1.setCreditCardEndUserInfo("Credit card info fittizie");

        o2.setId(33);
        o2.setOrderDate(Date.valueOf("2019-05-06"));
        o2.setState("TO_SEND");
        o2.setTotalPrice(10.50);
        o2.setEndUserID(203);
        o2.setAddressEndUserInfo("Address info fittizie");
        o2.setCreditCardEndUserInfo("Credit card info fittizie");

        o3.setId(37);
        o3.setOrderDate(Date.valueOf("2018-05-07"));
        o3.setState("TO_SEND");
        o3.setTotalPrice(9.99);
        o3.setEndUserID(136);
        o3.setAddressEndUserInfo("Address info fittizie");
        o3.setCreditCardEndUserInfo("Credit card info fittizie");

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        Collection<Order> actual = orderDAO.doRetrieveAll("");
        Assert.assertEquals(orders, actual);
    }

    @Test
    void doRetrieveAllForSpecificUser() {
        // TO DO
    }

    @Test
    void retrieveOrdersAssociatedToUsers() throws SQLException {

        EndUser endUser = Mockito.mock(EndUser.class);
        Mockito.when(endUser.getId()).thenReturn(136);

        Collection<Order> orders = new LinkedList<>();

        Order o1 = new Order();
        Order o2 = new Order();

        o1.setId(29);
        o1.setOrderDate(Date.valueOf("2018-05-06"));
        o1.setState("TO_SEND");
        o1.setTotalPrice(10.50);
        o1.setEndUserID(136);
        o1.setAddressEndUserInfo("Address info fittizie");
        o1.setCreditCardEndUserInfo("Credit card info fittizie");

        o2.setId(37);
        o2.setOrderDate(Date.valueOf("2018-05-07"));
        o2.setState("TO_SEND");
        o2.setTotalPrice(9.99);
        o2.setEndUserID(136);
        o2.setAddressEndUserInfo("Address info fittizie");
        o2.setCreditCardEndUserInfo("Credit card info fittizie");

        orders.add(o1);
        orders.add(o2);

       Collection<Order> actual = orderDAO.retrieveOrdersAssociatedToUsers(endUser);
       Assert.assertEquals(orders, actual);
    }
}