package User.ProfileView;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/CreditCardDeleteServlet")
public class CreditCardDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        CreditCardDAO dao = new CreditCardDAO(ds);
        int id = Integer.valueOf(request.getParameter("credit_card_id"));
        CreditCard card = new CreditCard();
        card.setId(id);
        dao.delete(card);
        response.sendRedirect("CreditCardListServlet");
    }
}
