package User.ProfileView;

import User.AccountService.*;
import utils.Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private static final String EMAIL_ERROR = "Email già in uso";
    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private EndUserDAO daoEndUser = new EndUserDAO(ds);
    private AddressDAO daoAddress = new AddressDAO(ds);
    private CreditCardDAO daoCreditCard = new CreditCardDAO(ds);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getParameter("surname"));

        EndUser user = new ConcreteEndUserBuilder()
                .setName(request.getParameter("name"))
                .setSurname(request.getParameter("surname"))
                .setEmail(request.getParameter("email"))
                .setPhoneNumber(request.getParameter("phone_number"))
                .setPassword(request.getParameter("password"))
                .setBirthdate(Utils.parseDate(request.getParameter("birth_date")))
                .createEndUser();

        if(daoEndUser.existEmail(user.getEmail())){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"));
            request.setAttribute("error_message", EMAIL_ERROR);
            dispatcher.forward(request, response);
            return;
        }

        Address address = new ConcreteAddressBuilder()
                .setStreet(request.getParameter("street"))
                .setCity(request.getParameter("city"))
                .setCountry(request.getParameter("country"))
                .setPostalCode(request.getParameter("postal_code"))
                .setRegion(request.getParameter("region"))
                .setPhoneNumber(request.getParameter("phone_number_address"))
                .createAddress();

        CreditCard card = new ConcreteCardBuilder()
                .setCardNumber(request.getParameter("card_number"))
                .setCardHolder(request.getParameter("card_holder"))
                .setExpirationDate(Utils.parseDate(request.getParameter("expirement_date")))
                .setCvv(request.getParameter("cvc"))
                .createCreditCard();



        address.setEndUser(user);
        card.setEndUser(user);
        daoEndUser.create(user);
        daoAddress.create(address);
        daoCreditCard.create(card);

        request.getSession().setAttribute("user", user);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/MerchandisingView/homepage.jsp"));
        dispatcher.forward(request, response);
    }
}
