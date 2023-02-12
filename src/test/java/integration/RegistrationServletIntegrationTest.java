package integration;

import Cart.CheckoutService.CartDAO;
import User.AccountService.*;
import User.ProfileView.RegistrationServlet;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.Connection;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationServletIntegrationTest {


    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CartDAO cartDAO;

    private CreditCardDAO creditCardDAO;
    private UserFacadeImp userFacadeImp;

    private EndUserDAO endUserDAO;
    private RegistrationServlet servlet;
    private static IDatabaseTester tester;
    private DataSource ds;

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
    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        servlet = Mockito.spy(new RegistrationServlet());
        Mockito.when(servlet.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        ds = mock(DataSource.class);
        refreshDataSet("registration_servlet_integration_testing/init.xml");
        doAnswer(new Answer()
        {
            @Override
            public Connection answer(InvocationOnMock invocationOnMock) throws Throwable {
                return tester.getConnection().getConnection();
            }
        }).when(ds).getConnection();

        endUserDAO = new EndUserDAO(ds);
        cartDAO = new CartDAO(ds);
        creditCardDAO =  new CreditCardDAO(ds);
        servlet.setCartDAO(cartDAO);
        servlet.setEndUserDAO(endUserDAO);
        servlet.setCreditCardDAO(creditCardDAO);
        userFacadeImp = mock(UserFacadeImp.class);
    }


    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(LoginEndUserIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}, {13}, {14}, {15}, {16}, {17})")
    @MethodSource("registrationFail")
    void registrationFail(String testName, String name, String surname, String email, String phone_number, String password,
                          String birth_date, String street, String city, String country, String postal_code, String region, String phone_number_address,
                          String card_number, String card_holder, String expirement_date, String cvv) throws Exception {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("surname")).thenReturn(surname);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("phone_number")).thenReturn(phone_number);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("birth_date")).thenReturn(birth_date);


        when(request.getParameter("street")).thenReturn(street);
        when(request.getParameter("city")).thenReturn(city);
        when(request.getParameter("country")).thenReturn(country);
        when(request.getParameter("postal_code")).thenReturn(postal_code);
        when(request.getParameter("region")).thenReturn(region);
        when(request.getParameter("phone_number_address")).thenReturn(phone_number_address);

        when(request.getParameter("card_number")).thenReturn(card_number);
        when(request.getParameter("card_holder")).thenReturn(card_holder);
        when(request.getParameter("expirement_date")).thenReturn(expirement_date);
        when(request.getParameter("cvv")).thenReturn(cvv);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);
        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));

    }


    private static Stream<Arguments> registrationFail() {

        return Stream.of(
                Arguments.of("Campo Nome non valido", "", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Cognome non valido", "Tommaso", "", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Email non valido", "Tommaso", "Sorrentino", "", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Telefono non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Password non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Data di nascita non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Via indirizzo non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Citta non valida", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Nazione non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Cap non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Regione non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Numero indirizzo non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo numero carta non valido ", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "", "Tommaso Sorrentino", "2030-01-01", "123" ),
                Arguments.of("Campo Proprietario non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "", "2030-01-01", "123" ),
                Arguments.of("Campo scadenza non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "", "123" ),
                Arguments.of("Campo cvc non valido", "Tommaso", "Sorrentino", "tommyos99@hotmail.it", "+393662968496", "password1!", "2000-01-01", "Via gunceri 35", "Poggiomarino", "Italia", "80040", "Campania", "+393662968496", "1111111111111", "Tommaso Sorrentino", "2030-01-01", "" )

        );
    }

    @Test
    void success() throws Exception {
        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmail.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"))).thenReturn(rD);
        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));

    }


    @Test
    void failByExistingUser() throws Exception {
        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("lollo@hotmail.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"));
        verify(request).setAttribute("error_email_message", RegistrationServlet.EMAIL_ERROR);
    }

    @Test

    void failByExistCreditCard() throws Exception {
        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmai.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("9999999999999");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"));
        verify(request).setAttribute("error_credit_card_message", RegistrationServlet.CARD_ERROR);
    }




}