package com.example.manga_up;

import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.EndUserDAO;

import java.io.*;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }
    EndUserDAO dao;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/index.jsp"));
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        System.out.println(ds);
        dao = new EndUserDAOImp(ds);
        System.out.println("hello");
        //dao.create(new EndUser("Francesco","Monzillo","fracmonz@gmail.com","3427797976","mannaggAMort",new Date(2001,07,19),null,null));
        System.out.println(dao.find(1));

    }

    public void destroy() {
    }
}