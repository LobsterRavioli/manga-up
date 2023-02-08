package unit.facade;

import User.AccountService.*;
import User.ProfileView.LoginEndUserServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

class UserFacadeImpTest {

    private DataSource ds;
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;
    private EndUserDAO endUserDAO;
    private UserFacadeImp userFacadeImp;

    @BeforeEach
    void setUp() throws Exception {
        ds = mock(DataSource.class);
        userFacadeImp = new UserFacadeImp(ds);
    }



    @Test
    void registrationPass() {
        EndUser user = new EndUser();
        user.setName("Tommaso");
        user.setSurname("Sorrentino");
        user.setEmail("tommyrock99@hotmail.it");
        user.setPassword("password!1");
    }


    @Test
    void registrationFail() {

    }


}