package unit.facade;

import Merchandising.MerchandiseService.Manga;
import Order.DispatchService.*;
import User.AccountService.*;
import User.ProfileView.LoginEndUserServlet;
import com.beust.ah.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderSubmissionFacadeImpTest {

    @Mock
    private DataSource ds;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO uD;
    @Mock
    private ToManageDAO assignDAO;
    @Mock
    private ManagedOrderDAO managedOrderDAO;

    @Mock
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        when(ds.getConnection()).thenReturn(mock(java.sql.Connection.class));
        OrderSubmissionFacadeImp orderSubmissionFacadeImp = new OrderSubmissionFacadeImp(ds);
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1})")
    @MethodSource("createInvalidParameter")
    void createOrderInvalidParameter(String testName, Order order, ArrayList<Manga> products, User selectedManager) {
        OrderSubmissionFacadeImp orderSubmissionFacadeImp = new OrderSubmissionFacadeImp(ds);
        assertThrows(IllegalArgumentException.class, () -> orderSubmissionFacadeImp.createOrder(order, products, selectedManager));
    }


    private static Stream<Arguments> createInvalidParameter() {
        ArrayList<Manga> products1 = new ArrayList<Manga>();
        products1.add(new Manga("", 1.0, 1));

        ArrayList<Manga> products2 = new ArrayList<Manga>();
        products2.add(new Manga("Ale il magnifico", 1.0, 1));

        ArrayList<Manga> products3 = new ArrayList<Manga>();
        products3.add(new Manga("Ale il magnifico", 1.0, 1));

        ArrayList<Manga> products4 = new ArrayList<Manga>();
        products4.add(new Manga("Ale il magnifico", 1.0, 1));

        ArrayList<Manga> products5 = new ArrayList<Manga>();
        products5.add(new Manga("Ale il magnifico", 1.0, 1));

        Manga manga = new Manga();
        manga.setName("Ale il magnifico Vol. 100");
        manga.setPrice(900.0);
        manga.setQuantity(1);
        Order order = new Order();
        order.setEndUserID(1);
        Address address = new Address();
        address.setCity("Roma");
        address.setCountry("Italia");
        address.setStreet("Via Roma 56");
        address.setPostalCode("00100");
        address.setPhoneNumber("+393333333333");
        address.setRegion("Lazio");
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("1234567890123456");
        creditCard.setCvv("123");
        creditCard.setExpirementDate(Utils.parseDate("2020-12-12"));
        creditCard.setCardHolder("Mario Rossi");


        return Stream.of(
                Arguments.of("Credit card non valida",null,products, new User("Tommaso")),
                Arguments.of("Credit card non valida",order,new ArrayList<Manga>(), new User("Tommaso")),
                Arguments.of("Credit card non valida",order,null, new User("Tommaso")),
                Arguments.of("Credit card non valida",order,products, new User()),
                Arguments.of("Credit card non valida",order,products, null)
        );
    }

    @Test
    void createOrderInvalidParameterNegativeId() {
        ArrayList<Manga> products = new ArrayList<Manga>();
        Manga manga = new Manga();
        manga.setName("Ale il magnifico Vol. 100");
        manga.setPrice(900.0);
        products.add(manga);
        Order order = new Order();
        order.setEndUserID(0);
        Address address = new Address();
        address.setCity("Roma");
        address.setCountry("Italia");
        address.setStreet("Via Roma 56");
        address.setPostalCode("00100");
        address.setPhoneNumber("+393333333333");
        address.setRegion("Lazio");
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("1234567890123456");
        creditCard.setCvv("123");
        creditCard.setExpirementDate(Utils.parseDate("2020-12-12"));
        creditCard.setCardHolder("Mario Rossi");
        OrderSubmissionFacadeImp orderSubmissionFacadeImp = new OrderSubmissionFacadeImp(ds);
        assertThrows(IllegalArgumentException.class, () -> orderSubmissionFacadeImp.createOrder(order, products, new User("Tommaso")));
    }



    @Test
    void executeTask() {

    }

}\