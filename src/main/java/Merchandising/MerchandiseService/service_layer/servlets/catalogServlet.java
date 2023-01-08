package Merchandising.MerchandiseService.service_layer.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "catalogServlet", value = "/catalogServlet")
public class catalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("productsSupply");
        String type = request.getParameter("type");
        ArrayList list = new ArrayList();
        if(type == null){

        }else{

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
