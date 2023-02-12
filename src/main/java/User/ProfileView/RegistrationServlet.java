package User.ProfileView;

import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.*;
import org.apache.taglibs.standard.tag.el.core.IfTag;
import utils.Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private CreditCardDAO creditCardDAO;
    private EndUserDAO endUserDAO;
    private CartDAO cartDAO;


    public static final String EMAIL_ERROR = "Email già in uso";
    public static final String CARD_ERROR = "Numero carta di credito già in uso";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{


        boolean error = false;

        if (endUserDAO == null || creditCardDAO == null || cartDAO == null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            endUserDAO = new EndUserDAO(ds);
            creditCardDAO = new CreditCardDAO(ds);
            cartDAO = new CartDAO(ds);
        }

        EndUser user = new EndUser();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(request.getParameter("email"));
        user.setPhoneNumber(request.getParameter("phone_number"));
        user.setPassword(request.getParameter("password"));
        user.setBirthdate(Utils.parseDate(request.getParameter("birth_date")));

        Address address = new Address();
        address.setStreet(request.getParameter("street"));
        address.setCity(request.getParameter("city"));
        address.setCountry(request.getParameter("country"));
        address.setPostalCode(request.getParameter("postal_code"));
        address.setRegion(request.getParameter("region"));
        address.setPhoneNumber(request.getParameter("phone_number_address"));

        CreditCard card = new CreditCard();
        card.setCardNumber(request.getParameter("card_number"));
        card.setCardHolder(request.getParameter("card_holder"));
        card.setExpirementDate(Utils.parseDate(request.getParameter("expirement_date")));
        card.setCvv(request.getParameter("cvc"));


        if(endUserDAO.existEmail(user.getEmail())){
            error = true;
            request.setAttribute("error_email_message", EMAIL_ERROR);
        }

        if(creditCardDAO.existsCreditCardNumber(card.getCardNumber())){
            error = true;
            request.setAttribute("error_credit_card_message", CARD_ERROR);
        }

        if(error){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"));
            dispatcher.forward(request, response);
            return;
        }
        user.addAddress(address);
        user.addCard(card);
        UserFacade facadeUser = (UserFacade) getServletContext().getAttribute(UserFacade.USER_FACADE);

        facadeUser.registration(user);


        request.getSession().setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));
        request.getSession().setAttribute("user", user);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));
        dispatcher.forward(request, response);

        }catch (Exception e){
        response.setStatus(500);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
        dispatcher.forward(request, response);
        return;
        }

    }

    public void setCreditCardDAO(CreditCardDAO creditCardDAO) {
        this.creditCardDAO = creditCardDAO;
    }

    public void setEndUserDAO(EndUserDAO endUserDAO) {
        this.endUserDAO = endUserDAO;
    }

    public void setCartDAO(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }


}
