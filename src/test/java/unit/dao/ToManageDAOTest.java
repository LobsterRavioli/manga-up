package unit.dao;

import Order.DispatchService.Order;
import Order.DispatchService.OrderDAO;
import Order.DispatchService.ToManage;
import Order.DispatchService.ToManageDAO;
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
import utils.DAOException;

import javax.sql.DataSource;
import java.util.stream.Stream;


class ToManageDAOTest {

    private static IDatabaseTester tester;
    private static ToManageDAO toManageDAO;

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
        refreshDataSet("to_manage_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        toManageDAO = new ToManageDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void createPass() throws Exception {

        User orderManager = Mockito.mock(User.class);
        Mockito.when(orderManager.getUsername()).thenReturn("Sara");

        Order toManageOrder = Mockito.mock(Order.class);
        Mockito.when(toManageOrder.getId()).thenReturn(Long.valueOf(21));

        ToManage toManage = new ToManage(orderManager, toManageOrder);

        ITable expected = new FlatXmlDataSetBuilder().build(OrderDAOTest.class.getClassLoader().getResourceAsStream("to_manage_dao/to_manage_create_pass.xml"))
                .getTable("TO_MANAGE");

        toManageDAO.create(toManage);

        ITable actual = tester.getConnection().createDataSet().getTable("TO_MANAGE");

        Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual));
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2})")
    @MethodSource("createTestFailProvider")
    void createFail(String testName, String orderManageUserName, long orderId) {

        ToManage order = new ToManage();

        order.setUserName(orderManageUserName);
        order.setOrderId(orderId);

        Assert.assertThrows(IllegalArgumentException.class, () -> toManageDAO.create(order));
    }

    private static Stream<Arguments> createTestFailProvider() {
        return Stream.of(Arguments.of("Test case assegnamento task fallito, user name non valido", null, 21),
                Arguments.of("Test case assegnamento task fallito, user name non valido", " ", 21),
                Arguments.of("Test case assegnamento task fallito, order id non valido", "Sara", -3)
        );
    }

    @Test
    void deletePass() throws Exception {

        ITable expected = new FlatXmlDataSetBuilder().
                build(OrderDAO.class.getClassLoader().getResourceAsStream("to_manage_dao/delete_to_manage_pass.xml")).
                getTable("TO_MANAGE");

        User orderManager = Mockito.mock(User.class);
        Mockito.when(orderManager.getUsername()).thenReturn("Giovanni");

        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(Long.valueOf(75));

        ToManage toManage = new ToManage(orderManager, order);

        toManageDAO.delete(toManage);

        ITable actual = tester.getConnection().createDataSet().getTable("TO_MANAGE");
        Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual));
    }

    @Test
    void deleteFail() {

        User orderManager = Mockito.mock(User.class);
        Mockito.when(orderManager.getUsername()).thenReturn("Giovanni");

        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(Long.valueOf(1044));

        ToManage toManage = new ToManage(orderManager, order);

        Assert.assertThrows(DAOException.class, () -> toManageDAO.delete(toManage));
    }
}