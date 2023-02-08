package unit.dao;

import Merchandising.MerchandiseService.Manga;
import Order.DispatchService.Order;
import Order.DispatchService.OrderRow;
import Order.DispatchService.OrderRowDAO;
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

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;


class OrderRowDAOTest {

    private static IDatabaseTester tester;
    private static OrderRowDAO orderRowDAO;

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
                .build(OrderRowDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo  stato iniziale di default
        refreshDataSet("order_row_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        orderRowDAO = new OrderRowDAO(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void createPass() throws Exception {

        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(Long.valueOf(29));

        EndUser endUser = Mockito.mock(EndUser.class);
        Mockito.when(endUser.getId()).thenReturn(Integer.valueOf(136));

        Manga manga = Mockito.mock(Manga.class);
        Mockito.when(manga.getName()).thenReturn("Slam Dunk");
        Mockito.when(manga.getPrice()).thenReturn(Double.valueOf(4.99));

        OrderRow orderRow = new OrderRow(order, endUser, manga, 5);

        ITable expected = new FlatXmlDataSetBuilder().build(OrderRowDAOTest.class.getClassLoader().getResourceAsStream("order_row_dao/order_row_create_pass.xml"))
                .getTable("ORDER_ROW");

        orderRowDAO.create(orderRow);

        ITable actual = tester.getConnection().createDataSet().getTable("ORDER_ROW");

        String[] ignoreCol = new String[1];
        ignoreCol[0] = "id";
        Assertion.assertEqualsIgnoreCols(new SortedTable(expected), new SortedTable(actual), ignoreCol);
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5})")
    @MethodSource("createTestFailProvider")
    void createFail(String testName, long ordId, int endUserId, String mangaName, double mangaPrice, int quantity) {

        OrderRow orderRow = new OrderRow();
        orderRow.setOrderId(ordId);
        orderRow.setUserId(endUserId);
        orderRow.setMangaName(mangaName);
        orderRow.setMangaPrice(mangaPrice);
        orderRow.setQuantity(quantity);

        Assert.assertThrows(IllegalArgumentException.class, () -> orderRowDAO.create(orderRow));
    }

    private static Stream<Arguments> createTestFailProvider() {
        return Stream.of(Arguments.of("Test case creazione riga ordine fallita, id ordine non valido", -5, 136, "Naruto", 4.99, 2),
                Arguments.of("Test case creazione riga ordine fallita, end user id non valido", 29, -3, "Naruto", 4.99, 2),
                Arguments.of("Test case creazione riga ordine fallita, nome manga non valido", 29, 136, null, 4.99, 2),
                Arguments.of("Test case creazione riga ordine fallita, nome manga non valido", 29, 136, " ", 4.99, 2),
                Arguments.of("Test case creazione riga ordine fallita, prezzo manga non valido", 29, 136, "Naruto", 0.00, 2),
                Arguments.of("Test case creazione riga ordine fallita, quantita manga non valida", 29, 136, "Naruto", 4.99, -5)
        );
    }

    @Test
    void retrieveOrderRowAssociatedToOrder() throws SQLException {

        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(Long.valueOf(65));

        Collection<OrderRow> orderRows = new LinkedList<>();

        OrderRow row1 = new OrderRow();
        OrderRow row2 = new OrderRow();

        row1.setOrderId(65);
        row1.setUserId(44);
        row1.setMangaName("Dragon Quest");
        row1.setMangaPrice(4.99);
        row1.setQuantity(6);

        row2.setOrderId(65);
        row2.setUserId(44);
        row2.setMangaName("Bleach");
        row2.setMangaPrice(4.99);
        row2.setQuantity(3);

        orderRows.add(row1);
        orderRows.add(row2);

        Collection<OrderRow> actual = orderRowDAO.retrieveOrderRowAssociatedToOrder(order);
        Assert.assertEquals(orderRows, actual);
    }
}