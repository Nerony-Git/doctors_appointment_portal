package com.george.doctors_appointment_portal.controller;

import com.george.doctors_appointment_portal.dao.AdminDao;
import com.george.doctors_appointment_portal.model.Admin;
import com.george.doctors_appointment_portal.model.Doctor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet({
        "/admin_login", "/admin_logout", "/admin_register", "/admin_authenticate", "/admin_dashboard",
        "/new_admin", "/admin_view", "/admin_edit", "/admin_password"
})
public class AdminController extends HttpServlet {
    private AdminDao adminDao;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void init(){
        adminDao = new AdminDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin_login":
                    adminLogin(request, response);
                    break;
                case "/admin_register":
                    adminRegister(request, response);
                    break;
                case "/admin_logout":
                    adminLogout(request, response);
                    break;
                case "/admin_authenticate":
                    adminAuthenticate(request, response);
                    break;
                case "/admin_dashboard":
                    adminDashboard(request, response);
                    break;
                case "/new_admin":
                    newAdmin(request, response);
                    break;
                case "/admin_view":
                    adminView(request, response);
                    break;
                case "/admin_edit":
                    adminEdit(request, response);
                    break;
                case "/admin_password":
                    adminPassword(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_login.jsp");
                    dispatcher.forward(request, response);

            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void adminLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("successMsg", "Successfully Logout");
        response.sendRedirect("pages/admin/admin_login.jsp");
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_login.jsp");
        dispatcher.forward(request, response);

    }

    private void adminRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_register.jsp");
        dispatcher.forward(request, response);

    }

    private void adminAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Admin admin = adminDao.validateAdmin(username, password);
            String destPage = "admin_login";
            HttpSession session = request.getSession();

            if (admin != null) {
                session.setAttribute("admin", admin);
                destPage = "admin_dashboard";
            }else {
                session.setAttribute("errorMsg", "Invalid username or password");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }

    }

    private void adminDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/dashboard.jsp");
        dispatcher.forward(request, response);

    }

    private void newAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        Admin newUser = new Admin();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setOtherName(otherName);
        newUser.setDob(dob);
        newUser.setContact(contact);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);

        try {
            int result = adminDao.registerAdmin(newUser);
            if (result == 1) {
                session.setAttribute("successMsg", "Registered Successfully");
                response.sendRedirect("admin_login");
            } else {
                session.setAttribute("errorMsg", "Registration Failed. Try Again");
                response.sendRedirect("admin_register");
            }
        } catch (Exception e) {
            //TODO Auto-genrated catch block
            e.printStackTrace();
        }
    }

    private void adminView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_view.jsp");
        dispatcher.forward(request, response);

    }

    private void adminEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_edit.jsp");
        dispatcher.forward(request, response);

    }

    private void adminPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/admin_password.jsp");
        dispatcher.forward(request, response);

    }

}
