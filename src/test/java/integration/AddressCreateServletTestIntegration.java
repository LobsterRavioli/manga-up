package integration;


import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.processProductInsertion;
import User.AccountService.AddressDAO;
import User.ProfileView.AddressCreateServlet;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressCreateServletTestIntegration {
    private processProductInsertion servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private AddressCreateServlet spy;
    private AddressDAO addressDAO;
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
        spy = Mockito.spy(new AddressCreateServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("address_dao/init_integration.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        addressDAO = new AddressDAO(ds);

    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(LoginEndUserIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @ParameterizedTest(name = "Test - {0} with [{arguments}]")
    @MethodSource("testCreateAddressInvalidParametersProvider")
    void testCreateAddressInvalidParameters(String testName, String country, String city, String street, String postalCode, String phone_number, String region) throws ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        Mockito.when(request.getParameter("street")).thenReturn(street);
        Mockito.when(request.getParameter("city")).thenReturn(city);
        Mockito.when(request.getParameter("country")).thenReturn(country);
        Mockito.when(request.getParameter("postal_code")).thenReturn(postalCode);
        Mockito.when(request.getParameter("region")).thenReturn(region);
        Mockito.when(request.getParameter("phone_number")).thenReturn(phone_number);
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));

    }

    private static Stream<Arguments> testCreateAddressInvalidParametersProvider(){
        return Stream.of(
                Arguments.of("Test case creazione address fallita Nome non valido", "", "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", null, "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Napoli", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", null, "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "123", "Via Roma 35", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "Napoli", "", "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Nome non valido", "Italia", "Napoli", null, "00100", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via Roma 35", "0010092813982", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", "abc", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", "1", "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita CAP non valido", "Italia", "Napoli", "Via roma 35", null, "+393662968496", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", "abc", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", "", "Campania"),
                Arguments.of("Test case creazione address fallita Numero carta non valido", "Italia", "Napoli", "Via roma 35", "00100", "2187361892736218697", "Campania"),
                Arguments.of("Test case creazione address fallita Numero non valido", "Italia", "Napoli", "Via roma 35", "00100", null, "Campania"),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", "123"),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", ""),
                Arguments.of("Test case creazione address fallita Regione non valido", "Italia", "Napoli", "Via roma 35", "00100", "+393662968496", null)
        );
    }


}
