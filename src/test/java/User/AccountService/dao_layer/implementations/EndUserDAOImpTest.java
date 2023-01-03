package User.AccountService.dao_layer.implementations;


import User.AccountService.beans.EndUser;
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
import utils.DAOUtil;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndUserDAOImpTest {
    private static IDatabaseTester tester;

    EndUserDAOSql dao;
    @Mock
    DataSource ds;

    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {

        // mem indica che il DB deve andare in memoria
        // test indica il nome del DB

        // DB_CLOSE_DELAY=-1 impone ad H2 di eliminare il DB solo quando il processo della JVM termina
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'",
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
                .build(EndUserDAOSql.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("enduser_dao/init.xml");
        when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        dao = new EndUserDAOSql(ds);
        // SETUP DAO
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }


    @Test
    public void TC1_1(){
        int id = 2;
        EndUser user = dao.find(id);
        assertEquals(user, null)
;    }
    @Test
    public void TC1_2(){
        int id = 1;
        EndUser expectedUser = new EndUser();
        expectedUser.setId(id);
        expectedUser.setEmail("tommyrock99@hotmail.it");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date expectedDate = DAOUtil.toSqlDate(new GregorianCalendar(2000, 0,1, 0,0 ).getTime());
        expectedUser.setBirthdate(expectedDate);
        expectedUser.setName("Tommaso");
        expectedUser.setSurname("Sorrentino");
        expectedUser.setPhoneNumber("393662968496");
        expectedUser.setPassword("napoli99");
        EndUser user = dao.find(id);
        assertEquals(expectedUser, user);
    }

}