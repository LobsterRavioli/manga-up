package User.AccountService.service_layer;

import User.AccountService.beans.*;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private static final String EMAIL_ERROR = "Email già in uso";
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private EndUserDAO daoEndUser = factory.getEndUserDAO();
    private AddressDAO daoAddress = factory.getAddressDAO();
    private CreditCardDAO daoCreditCard = factory.getCreditCardDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        EndUser user = new EndUserBuilder()
                .setName(request.getParameter("name"))
                .setSurname(request.getParameter("surname"))
                .setEmail(request.getParameter("email"))
                .setPhoneNumber(request.getParameter("phone_number"))
                .setPassword(request.getParameter("password"))
                .setBirthdate(new Date(request.getParameter("birthdate")))
                .createEndUser();

        if(daoEndUser.existEmail(user.getEmail())){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/registration_end_user.jsp"));
            request.setAttribute("message", EMAIL_ERROR);
            dispatcher.forward(request, response);
            return;
        }

        Address address = new AddressBuilder()
                .setStreet(request.getParameter("street"))
                .setCity(request.getParameter("city"))
                .setCountry(request.getParameter("country"))
                .setPostalCode(request.getParameter("postal_code"))
                .setRegion(request.getParameter("region"))
                .createAddress();

        CreditCard card = new CreditCardBuilder()
                .setCardNumber(request.getParameter("card_number"))
                .setCardHolder(request.getParameter("card_holder"))
                .setExpirationDate(new Date(request.getParameter("expiration_date")))
                .setCvv(request.getParameter("cvv"))
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
