package Cart.CheckoutService;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import User.AccountService.EndUser;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartDAOTest {


    private static IDatabaseTester tester;

    private CartDAO cD;

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
        try{
            tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(CartDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("Cart/cart_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        cD = new CartDAO(ds);
    }


    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void retrieveByUserNonExistentUser() {
        EndUser e_U = new EndUser(2);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.retrieveByUser(e_U)).getMessage());
    }

    @Test
    void retrieveByUserSuccess() throws Exception{
        EndUser e_U = new EndUser(3);
        Assert.assertEquals(cD.retrieveByUser(e_U).size(),1);
    }



    @Test
    void removeProductNotExistentUser() {
        EndUser e_U = new EndUser(2);
        Manga m = new Manga(20);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.removeProduct(m,e_U)).getMessage());
    }

    @Test
    void removeProductNotExistentManga() {
        EndUser e_U = new EndUser(3);
        Manga m = new Manga(15);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.removeProduct(m,e_U)).getMessage());
    }

    @Test
    void removeProductSuccess() throws Exception{
        EndUser e_U = new EndUser(3);
        Manga m = new Manga(20);
        cD.removeProduct(m,e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getRowCount(),0);
    }

    @Test
    void addProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void toEmptyCart() {
    }
}