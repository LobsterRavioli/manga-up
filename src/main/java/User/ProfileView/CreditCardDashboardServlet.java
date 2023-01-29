package User.ProfileView;

import User.AccountService.ConcreteCardBuilder;
import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/CreditCardDashboardServlet")
public class CreditCardDashboardServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        CreditCardDAO dao = new CreditCardDAO(ds);
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");

        ArrayList<CreditCard> cards = (ArrayList<CreditCard>)  dao.findAssociatedCards(user);
        for(CreditCard c : cards){
            System.out.println(c);
        }
        request.setAttribute("cards", cards);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_carte_di_credito.jsp"));
        dispatcher.forward(request, response);
    }
}
