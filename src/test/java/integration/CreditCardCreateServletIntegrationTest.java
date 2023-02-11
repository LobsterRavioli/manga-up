package integration;

import Merchandising.ProductsView.processProductInsertion;
import User.AccountService.CreditCardDAO;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import User.ProfileView.AddressCreateServlet;
import User.ProfileView.CreditCardCreateServlet;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utils.Utils;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreditCardCreateServletIntegrationTest {

    private processProductInsertion servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private CreditCardCreateServlet spy;
    private CreditCardDAO creditCardDAO;
    private Context context;
    private static IDatabaseTester tester;
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
        spy = Mockito.spy(new CreditCardCreateServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("credit_card_dao/init_integration_test.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        creditCardDAO = new CreditCardDAO(ds);
    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(LoginEndUserIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5})")
    @MethodSource("testCreateAddressInvalidParametersProvider")
    void testCreateAddressInvalidParameters(String testName, String number, String cvv, String expirationDate, EndUser endUser, String cardholder) throws Exception {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        Mockito.when(request.getParameter("card_number")).thenReturn(number);
        Mockito.when(request.getParameter("expirement_date")).thenReturn(expirationDate);
        Mockito.when(request.getParameter("cvc")).thenReturn(cvv);
        Mockito.when(request.getParameter("card_holder")).thenReturn(cardholder);
        Mockito.when(session.getAttribute("user")).thenReturn(endUser);

        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));

    }

    private static Stream<Arguments> testCreateAddressInvalidParametersProvider(){
        return Stream.of(
                Arguments.of("Test case lunghezza numero carta non valida", "1", "123", "2030-11-16", new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case formato numero carta non valido ", "abc", "123", "2030-11-16", new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case cvc formato non valido", "1111111111111", "abc", "2030-11-16", new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", "2030-11-16", new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", "2030-11-16", new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case cvc lunghezza non valida", "1111111111111", "11111111", null, new EndUser(1), "Mario Rossi"),
                Arguments.of("Test case ", "1111111111111", "111", "2000-11-16", new EndUser(0), "Mario Rossi"),
                Arguments.of("Test case ", "1111111111111", "111", "2030-11-16", null, "Mario Rossi"),
                Arguments.of("Test case ", "1111111111111", "111", "2030-11-16", new EndUser(1), null),
                Arguments.of("Test case ", "1111111111111", "111", "2030-11-16", new EndUser(1), ""),
                Arguments.of("Test case ", "1111111111111", "111", "2030-11-16", new EndUser(1), "123")
        );
    }
    @Test
    void success() throws Exception {
        Mockito.when(request.getParameter("card_number")).thenReturn("11111111111111");
        Mockito.when(request.getParameter("expirement_date")).thenReturn("2030-11-16");
        Mockito.when(request.getParameter("cvc")).thenReturn("123");
        Mockito.when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(request.getSession().getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(request.getSession(false)).thenReturn(session);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"))).thenReturn(rD);
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"));
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("credit_card_dao/create_expected_integration_test.xml"));

        ITable actualTable = tester.getConnection().createDataSet().getTable("credit_card");
        String[] ignoreCol = new String[1];
        ignoreCol[0] = "crd_id";
        Assertion.assertEqualsIgnoreCols(new SortedTable(expectedDataSet.getTable("credit_card")), new SortedTable(actualTable), ignoreCol);

    }

    @Test
    void SessionInvalid() throws ServletException, IOException {
        Mockito.when(request.getSession(false)).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void SessionInvalidParameter() throws ServletException, IOException {
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }


    @Test
    void createNotExistsUser() throws Exception {
        Mockito.when(request.getParameter("card_number")).thenReturn("11111111111111");
        Mockito.when(request.getParameter("expirement_date")).thenReturn("2030-11-16");
        Mockito.when(request.getParameter("cvc")).thenReturn("123");
        Mockito.when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1000));
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1000));
        Mockito.when(request.getSession().getAttribute("user")).thenReturn(new EndUser(1000));
        Mockito.when(request.getSession(false)).thenReturn(session);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"))).thenReturn(rD);
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"));
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(EndUserDAO.class.getClassLoader().getResourceAsStream("credit_card_dao/create_expected_integration_test.xml"));
        String[] ignoreCol = new String[1];
        ignoreCol[0] = "crd_id";
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));

    }
}
