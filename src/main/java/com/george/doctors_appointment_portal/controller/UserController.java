package com.george.doctors_appointment_portal.controller;

import com.george.doctors_appointment_portal.dao.UserDao;
import com.george.doctors_appointment_portal.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

@WebServlet({"/user_login", "/user_logout", "/user_register", "/user_authenticate", "/user_dashboard", "/book_appointment", "/user_edit", "/user_update", "/user_password"})
public class UserController extends HttpServlet {
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new_user":
                    new_user(request, response);
                    break;
                case "/user_logout":
                    userLogout(request, response);
                    break;
                case "/user_dashboard":
                    userDashboard(request, response);
                    break;
                case "/user_login":
                    userLogin(request, response);
                    break;
                case "/user_register":
                    userRegister(request, response);
                    break;
                case "/user_authenticate":
                    userAuthenticate(request, response);
                    break;
                case "/book_appointment":
                    userAppointment(request, response);
                    break;
                case "/user_edit":
                    editUser(request, response);
                    break;
                case "/user_update":
                    updateUser(request, response);
                    break;
                case "/user_password":
                    userPassword(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/user_login.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void new_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        String username = request.getParameter("username");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String postalAddress = request.getParameter("postalAddress");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User newUser = new User();
        newUser.setUserID(userID);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setOtherName(otherName);
        newUser.setUsername(username);
        newUser.setDob(dob);
        newUser.setContact(contact);
        newUser.setAddress(address);
        newUser.setPostalAddress(postalAddress);
        newUser.setEmail(email);
        newUser.setPassword(password);

        try {
            int result = userDao.registerUser(newUser);
            if (result == 1) {
                session.setAttribute("successMsg", "Registered Successfully");
                response.sendRedirect("");
            } else {
                session.setAttribute("errorMsg", "Registration Failed. Try Again");
            }
        } catch (Exception e) {
            //TODO Auto-genrated catch block
            e.printStackTrace();
        }
    }

    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("successMsg", "Successfully Logout");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user_login");
        dispatcher.forward(request, response);
    }

    private void userAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDao.validateUser(username, password);
            String destPage = "/user_login";
            HttpSession session = request.getSession();

            if (user != null) {
                session.setAttribute("user", user);
                destPage = "/user_dashboard";
            }else {
                session.setAttribute("errorMsg", "Invalid username or password");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }

    }

    private void userDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/user_login.jsp");
        dispatcher.forward(request, response);
    }

    private void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/user_register.jsp");
        dispatcher.forward(request, response);
    }

    private void userAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/book_appointment.jsp");
        dispatcher.forward(request, response);
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/edit_user.jsp");
        dispatcher.forward(request, response);
    }

    private void userPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/user/user_password.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String postalAddress = request.getParameter("postalAddress");
        String email = request.getParameter("email");

        User updateUser = new User(userID, firstName, lastName, otherName, dob, contact, address, postalAddress, email);
        System.out.println(updateUser.getUserID());
        boolean u = userDao.updateUser(updateUser);
        HttpSession session = request.getSession();

        if (u == true) {
            User updateUserObject = userDao.getUserByID(userID);
            session.setAttribute("successMsg", "Profile details updated successfully");
            session.setAttribute("user", updateUserObject);
            response.sendRedirect("user_edit");
        } else {
            session.setAttribute("errorMsg", "Profile details failed to update");
            response.sendRedirect("user_edit");
        }
    }

}
