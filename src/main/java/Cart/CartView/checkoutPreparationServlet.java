package Cart.CartView;

import User.AccountService.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "checkoutPreparationServlet", value = "/checkoutPreparationServlet")
public class checkoutPreparationServlet extends HttpServlet {

    private CreditCardDAO daoCC;
    private AddressDAO daoADD;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if(daoCC==null)
            daoCC = new CreditCardDAO(ds);
        if(daoADD==null)
            daoADD = new AddressDAO(ds);

        EndUser endUser = (EndUser) request.getSession(false).getAttribute("user");
        ArrayList<CreditCard> listaCarte = (ArrayList<CreditCard>) daoCC.findAssociatedCards(endUser);
        ArrayList<Address> listaAddresses = (ArrayList<Address>) daoADD.findAssociatedAddresses(endUser);


        if(listaCarte.size()==0 && listaAddresses.size()==0){
            request.setAttribute("errorCard","nessuna carta di pagamento associata all'account");
            request.setAttribute("errorAddress","nessun indirizzo associato all'account");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
            rd.forward(request, response);
            return;
        }else if(listaAddresses.size()==0){
            request.setAttribute("errorAddress","nessun indirizzo associato all'account");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
            rd.forward(request, response);
            return;
        } else if (listaCarte.size()==0) {
            request.setAttribute("errorCard","nessuna carta di pagamento associata all'account");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
            rd.forward(request, response);
            return;
        }


        request.setAttribute("cards",listaCarte);
        request.setAttribute("addresses",listaAddresses);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/checkout.jsp");
        rd.forward(request, response);
        return;

    }

    public void setDaoCC(CreditCardDAO c){
        daoCC=c;
    }

    public void setDaoADD(AddressDAO c){
        daoADD=c;
    }
}
