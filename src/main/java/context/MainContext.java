package context;


import Order.DispatchService.OrderSubmissionFacade;
import Order.DispatchService.OrderSubmissionFacadeImp;
import User.AccountService.UserFacade;
import User.AccountService.UserFacadeImp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@WebListener
public class MainContext implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) { 

		ServletContext context = event.getServletContext();
		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/manga-up");

			try {
				Connection con = ds.getConnection();
			} catch (SQLException e){
				System.out.println(e);
			}
			context.setAttribute(OrderSubmissionFacade.ORDER_SUBMISSION_FACADE,new OrderSubmissionFacadeImp(ds));
			context.setAttribute("DataSource", ds);
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
		context.setAttribute(OrderSubmissionFacade.ORDER_SUBMISSION_FACADE,new OrderSubmissionFacadeImp(ds));
		context.setAttribute(UserFacade.USER_FACADE,new UserFacadeImp(ds));
		context.setAttribute("DataSource", ds);
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		context.removeAttribute("DataSource");
	}
}
