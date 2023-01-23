package Order.OrderView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "manageServlet", value = "/manageServlet")
public class ManagedOrderServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ord_id = request.getParameter("manage");

        if(ord_id != null)
        {
            getServletContext().getRequestDispatcher("/OrderView/manage_order.jsp").forward(request, response);
        }
        else
        {
            response.sendRedirect(request.getServletContext().getContextPath()+"/orderServlet");
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
