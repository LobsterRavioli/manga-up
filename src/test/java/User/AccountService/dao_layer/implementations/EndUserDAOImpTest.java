package User.AccountService.dao_layer.implementations;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.SQLException;


import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class EndUserDAOImpTest {
    private static IDatabaseTester tester;

    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {

        // mem indica che il DB deve andare in memoria
        // test indica il nome del DB

        // DB_CLOSE_DELAY=-1 impone ad H2 di eliminare il DB solo quando il processo della JVM termina
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:test/resources/schema.sql'",
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
        refreshDataSet("test/resources/initAccountDAOTest.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        // SETUP DAO
        // accountDAO = new AccountDAO(ds);
        // accountDAOStub = new AccountDAOStub(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }


    public void registrazione_success(){}

    public void registrazione_fail(){}

    public void existEmailSuccess(){}

    public void existEmailFail(){}


}