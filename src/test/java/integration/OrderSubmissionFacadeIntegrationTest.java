package integration;

import Merchandising.MerchandiseService.Manga;
import Order.DispatchService.*;
import User.AccountService.Address;
import User.AccountService.CreditCard;
import User.AccountService.User;
import User.AccountService.UserDAO;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderSubmissionFacadeIntegrationTest {

    private static IDatabaseTester tester;
    private static OrderSubmissionFacadeImp orderSubmissionFacadeImp;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO uD;
    @Mock
    private ToManageDAO assignDAO;
    @Mock
    private ManagedOrderDAO managedOrderDAO;

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
                .build(OrderSubmissionFacadeIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo  stato iniziale di default
        refreshDataSet("init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        orderSubmissionFacadeImp = new OrderSubmissionFacadeImp(ds);
    }

    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    void createOrderSuccess() {

    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3})")
    @MethodSource("createInvalidParameter")
    void createOrderInvalidParameter(String testName, Order order, ArrayList<Manga> products, User selectedManager) {

        assertThrows(IllegalArgumentException.class, () -> orderSubmissionFacadeImp.createOrder(order, products, selectedManager));
    }


    private static Stream<Arguments> createInvalidParameter() {
        // Per ogni order, SOLO la order date e il total price sono calcolati dal facade.
        // Il facade recupera da un ordine passatogli come parametro il resto dei dati
        // PRIMA DI CHIAMARE IL FACADE SI PRESUPPONE CHE NEL DB SIA STATO RECUPERATO UN ORDINE "BEN FORMATO"
        // CON ADDRESS, CREDIT CARD ecc GIÀ NEL GIUSTO FORMATO.

        Order order = new Order();
        order.setId(65);
        order.setEndUserAddress(new Address("Italia", "Campania", "Salerno", "Via Garibaldi", "8400"));
        order.setEndUserCard(new CreditCard("14562222339984544", "116", "Giovanni Vincenzi", Date.valueOf("2025-10-02")));
        order.setEndUserID(43);
        order.setState(Order.TO_SENT);

        Order order1 = new Order();
        order1.setId(-3); // id ordine non valido
        order1.setEndUserAddress(new Address("Italia", "Campania", "Salerno", "Via Garibaldi", "84003"));
        order1.setEndUserCard(new CreditCard("1111222233334444", "326", "Giovanni Vincenzi", Date.valueOf("2025-10-02")));
        order1.setEndUserID(56);
        order1.setState(Order.TO_SENT);

        /*
        * Ad Order va bene SIA UN OGGETTO Address CHE UNA STRINGA.
        * Alla fine lui vede una stringa: utilizzerà il toString sull'oggetto Address oppure una qualsiasi
        * altra stringa passatagli come parametro, preoccupandosi semplicemente che essa non sia nulla o vuota.
        *
        * setAddressEndUserInfo("...") è equivalente a setEndUserAddress(new Address(...)) dal punto di vista di Order
        *
        * Lo stesso discorso vale per le CreditCard
        */
        Order order2 = new Order();
        order2.setId(9);
        order2.setAddressEndUserInfo(" "); // indirizzo vuoto
        order2.setCreditCardEndUserInfo("Informazioni credit card fittizie");
        order2.setEndUserID(54);
        order2.setState(Order.TO_SENT);

        Order order3 = new Order();
        order3.setId(10);
        // order3.setAddressEndUserInfo("..."); ADDRESS NON IMPOSTATO = ADDRESS = null
        order3.setCreditCardEndUserInfo("Informazioni credit card fittizie");
        order3.setEndUserID(54);
        order3.setState(Order.TO_SENT);

        Order order4 = new Order();
        order4.setId(10);
        order4.setAddressEndUserInfo("Informazioni adress fittizie");
        order4.setCreditCardEndUserInfo(" "); // credit card info non valide -> stringa vuota
        order4.setEndUserID(22);
        order4.setState(Order.TO_SENT);

        Order order5 = new Order();
        order5.setId(19);
        order5.setAddressEndUserInfo("Informazioni adress fittizie");
        // order5.setCreditCardEndUserInfo("..."); // credit card info non valide -> NULL
        order5.setEndUserID(72);
        order5.setState(Order.TO_SENT);

        Order order6 = new Order();
        order6.setId(7);
        order6.setAddressEndUserInfo("Informazioni adress fittizie");
        order6.setCreditCardEndUserInfo("Informazioni credit card fittizie");
        order6.setEndUserID(-9); // id end user non valido
        order6.setState(Order.TO_SENT);

        Order order7 = new Order();
        order7.setId(7);
        order7.setAddressEndUserInfo("Informazioni adress fittizie");
        order7.setCreditCardEndUserInfo("Informazioni credit card fittizie");
        order7.setEndUserID(34);
        // order7.setState(Order.TO_SENT); // stato ordine = null


        ArrayList<Manga> products = new ArrayList<>();
        products.add(new Manga("Slam Dunk", 4.99, 2));
        products.add(new Manga("Ale il magnifico", 5.00, 5));
        products.add(new Manga("Dragon Quest", 7.50, 6));

        ArrayList<Manga> products1 = new ArrayList<Manga>();
        products1.add(new Manga("", 4.99, 1)); // nome manga vuoto
        products1.add(new Manga("Slam Dunk", 4.99, 2));

        ArrayList<Manga> products2 = new ArrayList<Manga>();
        products2.add(new Manga("Ale il magnifico", 5.00, 5));
        products2.add(new Manga(null, 4.00, 3)); // nome manga nullo

        ArrayList<Manga> products3 = new ArrayList<Manga>();
        products3.add(new Manga("Dragonzozz Zuper Blue and Purple", -3.50, 6)); // prezzo non valido
        products3.add(new Manga("Dragon Quest", 7.50, 6));

        ArrayList<Manga> products4 = new ArrayList<Manga>();
        products4.add(new Manga("Detective Conan", 5.00, -3)); // quantity non valida
        products4.add(new Manga("Naruto", 4.99, 7));

        User orderManager = new User("Gino", "NientCHIU");

        User orderManager1 = new User("Tommaso", null); // password null
        User orderManager2 = new User("Francesco", " "); // password vuota
        User orderManager3 = new User(null, "complexPassword!003"); // username null
        User orderManager4 = new User(" ", "ILoveSoftwareEng <3 <3 <3"); // username null

        return Stream.of(
                Arguments.of("Ordine null", null, products, orderManager),
                Arguments.of("Lista manga nulla", order, null, orderManager),
                Arguments.of("Order manager nullo", order, products, null),
                Arguments.of("Order id non valido", order1, products, orderManager),
                Arguments.of("Order non valido, info indirizzo vuote", order2, products, orderManager),
                Arguments.of("Order non valido, indirizzo nullo", order3, products, orderManager),
                Arguments.of("Order non valido, credit card info vuote", order4, products, orderManager),
                Arguments.of("Order non valido, credit card nulla", order5, products, orderManager),
                Arguments.of("Order non valido, end user id minore o uguale a 0", order6, products, orderManager),
                Arguments.of("Order non valido, stato dell'ordine non definito (nullo)", order7, products, orderManager),
                Arguments.of("Lista manga non valida, trovato manga con nome vuoto", order, products1, orderManager),
                Arguments.of("Lista manga non valida, trovato manga con nome nullo", order, products2, orderManager),
                Arguments.of("Lista manga non valida, trovato manga con prezzo minore o uguale a 0", order, products3, orderManager),
                Arguments.of("Lista manga non valida, trovato manga con quantita minore o uguale a zero", order, products4, orderManager),
                Arguments.of("Order manager non valido, password nulla", order, products, orderManager1),
                Arguments.of("Order manager non valido, password vuota", order, products, orderManager2),
                Arguments.of("Order manager non valido, username nullo", order, products, orderManager3),
                Arguments.of("Order manager non valido, username vuoto", order, products, orderManager4)
        );
    }

    @Test
    void executeTask() {

    }

}