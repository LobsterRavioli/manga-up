package unit.dao;

import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.AccountService.EndUser;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.sql.DataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressDAOTest {
    private static IDatabaseTester tester;
    private static AddressDAO addressDAO;

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
    }
    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(AddressDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("enduser_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        addressDAO = new AddressDAO(ds);
    }


    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void createPass() throws Exception {
        Address address = new Address();
        address.setCity("Roma");
        address.setStreet("Via Roma 24");
        address.setPostalCode("00100");
        address.setCountry("Italia");
        address.setRegion("Campania");
        address.setPhoneNumber("3333333333");
        EndUser user = new EndUser();
        user.setId(1);
        address.setEndUser(user);
        addressDAO.create(address);
        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(AddressDAOTest.class.getClassLoader().getResourceAsStream("enduser_dao/create_pass.xml"));
        String[] ignoreCol = new String[1];
        ignoreCol[0] = "addr_id";
        ITable expectedTable = expectedDataSet.getTable("address");
        Assertion.assertEqualsIgnoreCols(new SortedTable(expectedTable), new SortedTable(actualTable), ignoreCol);
    }

    @Test
    void createFail1() throws Exception {
        Address address = new Address();
        address.setCity(null);
        address.setStreet("Via Roma 24");
        address.setPostalCode("00100");
        address.setCountry("Italia");
        address.setRegion("Campania");
        address.setPhoneNumber("3333333333");
        EndUser user = new EndUser();
        user.setId(1);
        address.setEndUser(user);
        addressDAO.create(address);
        ITable actualTable = tester.getConnection().createDataSet().getTable("address");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(AddressDAOTest.class.getClassLoader().getResourceAsStream("enduser_dao/create_pass.xml"));
        String[] ignoreCol = new String[1];
        ignoreCol[0] = "addr_id";
        ITable expectedTable = expectedDataSet.getTable("address");
        Assertion.assertEqualsIgnoreCols(new SortedTable(expectedTable), new SortedTable(actualTable), ignoreCol);
    }



    @Test
    void createSuccess() {
        Address address = new Address();
        address.setCity("Roma");
        address.setStreet("Via Roma 24");
        address.setPostalCode("00100");
        address.setCountry("Italia");
        address.setRegion("Campania");
        address.setPhoneNumber("3333333333");
        EndUser user = new EndUser(1);
        address.setEndUser(user);
        addressDAO.create(address);

    }

    @Test
    void delete() {
    }

    @Test
    void findAssociatedAddresses() {
    }

    @Test
    void findById() {
    }
}