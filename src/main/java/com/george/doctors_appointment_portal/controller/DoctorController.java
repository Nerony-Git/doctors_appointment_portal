package com.george.doctors_appointment_portal.controller;

import com.george.doctors_appointment_portal.dao.DoctorDao;
import com.george.doctors_appointment_portal.dao.SpecialityDao;
import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.model.Speciality;
import com.george.doctors_appointment_portal.model.User;
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
import java.util.List;

@WebServlet({
        "/doctor_login", "/doctor_logout", "/doctor_register", "/new_doctor", "/doctor_dashboard",
        "/doctor_authenticate"
})
public class DoctorController extends HttpServlet {
    private DoctorDao doctorDao;
    private SpecialityDao specialityDao = new SpecialityDao();
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
                case "/doctor_authenticate":
                    doctorAuthenticate(request, response);
                    break;
                case "/doctor_register":
                    doctorRegister(request, response);
                    break;
                case "/doctor_logout":
                    doctorLogout(request, response);
                    break;
                case "/new_doctor":
                    new_doctor(request, response);
                    break;
                case "/doctor_dashboard":
                    doctorDashboard(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_login.jsp");
                    dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void new_doctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        String username = request.getParameter("username");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String specialty = request.getParameter("speciality");
        String qualification = request.getParameter("qualification");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        Doctor newUser = new Doctor();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setOtherName(otherName);
        newUser.setUsername(username);
        newUser.setDob(dob);
        newUser.setContact(contact);
        newUser.setSpeciality(specialty);
        newUser.setQualification(qualification);
        newUser.setEmail(email);
        newUser.setPassword(password);

        try {
            int result = doctorDao.registerDoctor(newUser);
            if (result == 1) {
                session.setAttribute("successMsg", "Registered Successfully");
                response.sendRedirect("doctor_login");
            } else {
                session.setAttribute("errorMsg", "Registration Failed. Try Again");
            }
        } catch (Exception e) {
            //TODO Auto-genrated catch block
            e.printStackTrace();
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

    private void doctorAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Doctor doctor = doctorDao.validateDoctor(username, password);
            String destPage = "/doctor_login";
            HttpSession session = request.getSession();

            if (doctor != null) {
                session.setAttribute("doctor", doctor);
                destPage = "/doctor_dashboard";
            }else {
                session.setAttribute("errorMsg", "Invalid username or password");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }

    }

    private void doctorDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void doctorRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listSpeciality(request, response);
    }

    private void listSpeciality(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Speciality> listSpeciality = specialityDao.getAllSpeciality();
        request.setAttribute("listSpeciality", listSpeciality);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_register.jsp");
        dispatcher.forward(request, response);
    }

}
