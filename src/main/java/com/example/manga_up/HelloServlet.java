package com.example.manga_up;

import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.EndUserDAO;

import java.io.*;
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
        dao = new EndUserDAOImp(ds);
        System.out.println("hello");
        System.out.println(dao.find(1));
    }

    public void destroy() {
    }
}