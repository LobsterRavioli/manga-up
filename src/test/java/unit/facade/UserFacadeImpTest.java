package unit.facade;

import User.AccountService.*;
import User.ProfileView.AddressCreateServlet;
import User.ProfileView.LoginEndUserServlet;
import com.beust.ah.A;
import org.checkerframework.checker.units.qual.C;
import org.h2.command.ddl.CreateDomain;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserFacadeImpTest {

    private DataSource ds;
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;
    private EndUserDAO endUserDAO;
    private UserFacadeImp userFacadeImp;

    @BeforeEach
    void setUp() throws Exception {
        ds = Mockito.mock(DataSource.class);
        userDAO = Mockito.mock(UserDAO.class);
        addressDAO = Mockito.mock(AddressDAO.class);
        creditCardDAO = Mockito.mock(CreditCardDAO.class);
        endUserDAO = Mockito.mock(EndUserDAO.class);
        userFacadeImp = new UserFacadeImp(userDAO, addressDAO, creditCardDAO, endUserDAO);
        Mockito.spy(userFacadeImp);
    }

    @ParameterizedTest(name = "registrationPass: {0}")
    @MethodSource("registrationFail")
    void registrationFail(String testName, Address address, CreditCard card, EndUser user) throws Exception {
        user.addAddress(address);
        user.addCard(card);
        Assert.assertThrows(Exception.class, () -> userFacadeImp.registration(user));
        verify(endUserDAO, Mockito.never()).create(any(EndUser.class));
        verify(addressDAO, Mockito.never()).create(any(Address.class));
        verify(creditCardDAO, Mockito.never()).create(any(CreditCard.class));
    }

    private static Stream<Arguments> registrationFail() {

        Address validAddress = new Address("Italia","Campania","Napoli","Via guinceri 31","80040");
        Address a1 = new Address(null,"Campania","Napoli","Via guinceri 31","80040");
        Address a2 = new Address("Italia",null,"Napoli","Via guinceri 31","80040");
        Address a3 = new Address("Italia","Campania",null,"Via guinceri 31","80040");
        Address a4 = new Address("Italia","Campania","Napoli",null,"80040");
        Address a5 = new Address("Italia","Campania","Napoli","Via guinceri 31",null);

        EndUser validUser = new EndUser("Tommaso", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e1 = new EndUser(null, "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e2 = new EndUser("", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e3 = new EndUser("Mario", null, "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e4 = new EndUser("Mario", "", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e5 = new EndUser("Mario", "Rossi", "","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e6 = new EndUser("Mario", "Rossi", null,"+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e7 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e8 = new EndUser("Mario", "Rossi", "lollo@hotmail.it",null, "password1!", Utils.parseDate("1999-01-01"));
        EndUser e9 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "", Utils.parseDate("1999-01-01"));
        EndUser e10 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        EndUser e11 = new EndUser("Mario", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", null);

        CreditCard validCard = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c1 = new CreditCard(1, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c2 = new CreditCard(0, "", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c3 = new CreditCard(0, null, new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c4 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c5 = new CreditCard(0, "123", new EndUser(1), "", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c6 = new CreditCard(0, "123", new EndUser(1), null, "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c7 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        CreditCard c8 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "",Utils.parseDate("2030-01-01"));
        CreditCard c9 = new CreditCard(0, "123", new EndUser(1), "1111111111111", null,Utils.parseDate("2030-01-01"));
        CreditCard c10 = new CreditCard(0, "123", new EndUser(1), "1111111111111", null,Utils.parseDate("2000-01-01"));
        CreditCard c11 = new CreditCard(0, "123", new EndUser(1), "1111111111111", "Tommaso Sorrentino",null);



        return Stream.of(
                Arguments.of("Indirizzo non valido", a1, validCard, validUser),
                Arguments.of("Indirizzo non valido", a2, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a3, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a4, validCard, validUser),
                Arguments.of( "Indirizzo non valido", a5, validCard, validUser),
                Arguments.of( "Utente non valido", validAddress, validCard, e1),
                Arguments.of( "Utente non valido", validAddress, validCard, e2),
                Arguments.of( "Utente non valido", validAddress, validCard, e3),
                Arguments.of( "Utente non valido", validAddress, validCard, e4),
                Arguments.of( "Utente non valido", validAddress, validCard, e5),
                Arguments.of( "Utente non valido", validAddress, validCard, e6),
                Arguments.of( "Utente non valido", validAddress, validCard, e7),
                Arguments.of( "Utente non valido", validAddress, validCard, e8),
                Arguments.of( "Utente non valido", validAddress, validCard, e9),
                Arguments.of( "Utente non valido", validAddress, validCard, e10),
                Arguments.of( "Utente non valido", validAddress, validCard, e11),
                Arguments.of( "Carta non valida", validAddress, c1, validUser),
                Arguments.of( "Carta non valida", validAddress, c2, validUser),
                Arguments.of( "Carta non valida", validAddress, c3, validUser),
                Arguments.of( "Carta non valida", validAddress, c4, validUser),
                Arguments.of( "Carta non valida", validAddress, c5, validUser),
                Arguments.of( "Carta non valida", validAddress, c5, validUser),
                Arguments.of( "Carta non valida", validAddress, c6, validUser),
                Arguments.of( "Carta non valida", validAddress, c7, validUser),
                Arguments.of( "Carta non valida", validAddress, c8, validUser),
                Arguments.of( "Carta non valida", validAddress, c9, validUser),
                Arguments.of( "Carta non valida", validAddress, c10, validUser),
                Arguments.of( "Carta non valida", validAddress, c11, validUser)

        );
    }

    @Test
    void registrationPass() throws Exception {
        EndUser validUser = new EndUser("Tommaso", "Rossi", "lollo@hotmail.it","+393662968496", "password1!", Utils.parseDate("1999-01-01"));
        CreditCard validCard =  new CreditCard(0, "123", null, "1111111111111", "Tommaso Sorrentino",Utils.parseDate("2030-01-01"));
        Address validAddress = new Address();
        validAddress.setCountry("Italia");
        validAddress.setCity("Napoli");
        validAddress.setStreet("Via guinceri 31");
        validAddress.setPostalCode("80040");
        validAddress.setRegion("Campania");
        validAddress.setPhoneNumber("+393662968496");

        validUser.addCard(validCard);
        validUser.addAddress(validAddress);
        userFacadeImp.registration(validUser);
        verify(endUserDAO).create(validUser);
        verify(creditCardDAO).create(validCard);
        verify(addressDAO).create(validAddress);

    }

}