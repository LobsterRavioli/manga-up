package Merchandising.MerchandiseService;

import User.AccountService.EndUserDAO;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MangaDAOTest {


    private static IDatabaseTester tester;

    MangaDAO dao;
    @Mock
    DataSource ds;

    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {

        // mem indica che il DB deve andare in memoria
        // test indica il nome del DB

        // DB_CLOSE_DELAY=-1 impone ad H2 di eliminare il DB solo quando il processo della JVM termina
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
        //BHO
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("Merchandising/manga_dao/init.xml");
        when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        dao = new MangaDAO(ds);
        // SETUP DAO
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }



    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void retrieveById() {
    }

    @Test
    void filterForUsers() {
    }

    @Test
    void retrieveAll() {
    }

    @Test
    void filterForEndUsers() {
    }

    @Test
    void updateQuantity() {
    }
}