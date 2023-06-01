package com.george.doctors_appointment_portal.controller;

import com.george.doctors_appointment_portal.dao.DoctorDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/doctor_login", "/doctor_logout", "/doctor_register" })
public class DoctorController extends HttpServlet {
    private DoctorDao doctorDao;

    public void init() {
        doctorDao = new DoctorDao();
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
                case "/doctor_login":
                    doctorLogin(request, response);
                    break;
                case "/doctor_register":
                    doctorRegister(request, response);
                    break;
                case "doctor_logout":
                    doctorLogout(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_login.jsp");
                    dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void doctorLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("successMsg", "Successfully Logout");
        response.sendRedirect("pages/doctor/doctor_login.jsp");
    }

    private void doctorLogin(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_login.jsp");
        dispatcher.forward(request, response);
    }

    private void doctorRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_register.jsp");
        dispatcher.forward(request, response);
    }
}
