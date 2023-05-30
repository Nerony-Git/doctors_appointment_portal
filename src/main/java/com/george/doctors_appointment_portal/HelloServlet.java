package com.george.doctors_appointment_portal;

import java.io.*;
import java.sql.SQLException;

import com.george.doctors_appointment_portal.dao.UserDao;
import com.george.doctors_appointment_portal.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    //private String message;

    private UserDao userDao;

    public void init() {
        //message = "Hello World!";
        userDao = new UserDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("text/html");
        response.sendRedirect("pages/user_login.jsp");

        /*// Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");*/
    }


    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        try {
            User user = userDao.validateUser(username, password);

            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect("");
            } else {
                session.setAttribute("errorMsg", "Invalid username or password");
                response.sendRedirect("");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }

    public void destroy() {
    }
}