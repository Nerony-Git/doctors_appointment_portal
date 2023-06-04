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
        "/doctor_authenticate", "/doctor_change", "/doctor_update", "/doctor_view", "/doctor_edit",
        "/doctor_password"
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
                case "/doctor_change":
                    doctorChangePass(request, response);
                    break;
                case "/doctor_update":
                    updateDoctor(request, response);
                    break;
                case "/doctor_view":
                    doctorView(request, response);
                    break;
                case "/doctor_edit":
                    doctorEdit(request, response);
                    break;
                case "/doctor_password":
                    doctorPassword(request, response);
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
        session.removeAttribute("doctor");
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

    private void doctorView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_view.jsp");
        dispatcher.forward(request, response);
    }

    private void doctorEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_edit.jsp");
        dispatcher.forward(request, response);
    }

    private void doctorPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/doctor/doctor_password.jsp");
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

    private void doctorChangePass(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String newPassword = request.getParameter("password");
        String oldPassword = request.getParameter("password2");

        try {
            HttpSession session = request.getSession();

            if (doctorDao.validateDoctorOldPassword(userID, oldPassword)) {

                if (doctorDao.changeDoctorPassword(userID, newPassword)) {

                    session.setAttribute("successMsg", "Password successfully changed.");
                    session.removeAttribute("doctor");
                    response.sendRedirect("doctor_login");

                } else {
                    session.setAttribute("errorMsg", "Password change failed.");
                    response.sendRedirect("doctor_password");
                }

            } else {
                session.setAttribute("errorMsg", "Previous password does not match.");
                response.sendRedirect("doctor_password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateDoctor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String qualification = request.getParameter("qualification");
        String email = request.getParameter("email");

        Doctor updateUser = new Doctor(userID, firstName, lastName, otherName, dob, contact, qualification, email);
        System.out.println(updateUser.getUserID());
        boolean u = doctorDao.updateDoctor(updateUser);
        HttpSession session = request.getSession();

        if (u == true) {
            Doctor updateUserObject = doctorDao.getDoctorByID(userID);
            session.setAttribute("successMsg", "Profile details updated successfully");
            session.setAttribute("user", updateUserObject);
            response.sendRedirect("user_edit");
        } else {
            session.setAttribute("errorMsg", "Profile details failed to update");
            response.sendRedirect("user_edit");
        }
    }

}
