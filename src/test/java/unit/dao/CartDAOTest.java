package unit.dao;

import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.*;
import User.AccountService.EndUser;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
        cD = new CartDAO(ds);

        given(ds.getConnection()).willAnswer(new Answer<Connection>() {
            public Connection answer(InvocationOnMock invocation) {
                try {
                    Connection c = tester.getConnection().getConnection();
                    return c;
                } catch (Exception e) {
                    return null;
                }
            }
        });

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
    void removeProductNotInCart() throws Exception{
        EndUser e_U = new EndUser(3);
        Manga m = new Manga(18);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.removeProduct(m,e_U)).getMessage());
    }

    @Test
    void addProductNotExistentUser() {
        EndUser e_U = new EndUser(2);
        Manga m = new Manga(20);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(m,1,e_U)).getMessage());
    }

    @Test
    void addProductNotExistentManga() {
        EndUser e_U = new EndUser(3);
        Manga m = new Manga(15);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(m,1,e_U)).getMessage());
    }
    @Test
    void addProductNonValidQuantity(){
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),18,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(manga,0,e_U)).getMessage());
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(manga,4,e_U)).getMessage());
    }

    @Test
    void addProductSuccess()throws Exception{
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),18,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        cD.addProduct(manga,2,e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getRowCount(),2);
    }

    @Test
    void addProductAlreadyExistent() throws Exception{
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),20,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        cD.addProduct(manga,2,e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getRowCount(),1);
    }


    @Test
    void updateProductNotExistentUser() {
        EndUser e_U = new EndUser(2);
        Manga m = new Manga(20);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(m,1,e_U)).getMessage());
    }

    @Test
    void updateProductNotExistentManga() {
        EndUser e_U = new EndUser(3);
        Manga m = new Manga(15);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.addProduct(m,1,e_U)).getMessage());
    }
    @Test
    void updateProductNonValidQuantity(){
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),18,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        System.out.println(Assert.assertThrows(Exception.class,()->cD.updateProduct(manga,0,e_U)).getMessage());
        System.out.println(Assert.assertThrows(Exception.class,()->cD.updateProduct(manga,4,e_U)).getMessage());
    }


    @Test
    void updateProductNonExistentInCart() throws Exception {
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),18,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        cD.updateProduct(manga,2,e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getRowCount(),2);
    }

    @Test
    void updateProductSuccess() throws Exception {
        EndUser e_U = new EndUser(3);
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date) Date.valueOf("2001-02-05"),20,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"), ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        cD.updateProduct(manga,1,e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getValue(0,"quantity"),1);
    }

    @Test
    void toEmptyCartNotExistingUser() throws Exception{
        EndUser e_U = new EndUser(1);
        System.out.println(Assert.assertThrows(Exception.class,()->cD.toEmptyCart(e_U)).getMessage());
    }

    @Test
    void toEmptyCartSuccess() throws Exception{
        EndUser e_U = new EndUser(3);
        cD.toEmptyCart(e_U);
        ITable actualTable = tester.getConnection().createDataSet().getTable("CART");
        Assert.assertEquals(actualTable.getRowCount(),0);
    }
}