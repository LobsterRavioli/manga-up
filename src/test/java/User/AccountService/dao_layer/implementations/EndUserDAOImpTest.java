package User.AccountService.dao_layer.implementations;


import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndUserDAOImpTest {
    private static IDatabaseTester tester;

    EndUserDAOImp dao;
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
                .build(EndUserDAOImp.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("enduser_dao/init.xml");
        when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        dao = new EndUserDAOImp(ds);
        // SETUP DAO
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    public void find_idNotInDB_False(){

    }
    @Test
    public void find_idInDb_False(){

    }

    @Test
    public void create_idValid_InDB_Pass() throws Exception{

    }

    @Test
    public void create_idValid_InDB_Fail(){
        dao.create(null);
    }

    @Test
    public void create_idNotValid_NotInDB_Fail(){
        dao.create(null);
    }



}