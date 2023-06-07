package com.george.doctors_appointment_portal.controller;

import com.george.doctors_appointment_portal.dao.AdminDao;
import com.george.doctors_appointment_portal.dao.AppointmentDao;
import com.george.doctors_appointment_portal.dao.DoctorDao;
import com.george.doctors_appointment_portal.dao.SpecialityDao;
import com.george.doctors_appointment_portal.dao.UserDao;
import com.george.doctors_appointment_portal.model.Admin;
import com.george.doctors_appointment_portal.model.Appointment;
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
        "/admin_login", "/admin_logout", "/admin_register", "/admin_authenticate", "/admin_dashboard",
        "/new_admin", "/admin_view", "/admin_edit", "/admin_password", "/admin_change", "/admin_update",
        "/doctors", "/users", "/specialties", "/new_appointments", "/view_appointments", "/add_user",
        "/add_doctor", "/add_specialty", "/add_new_user", "/edit_user", "/view_user", "/update_user"
})
public class AdminController extends HttpServlet {
    private AdminDao adminDao = new AdminDao();
    private DoctorDao doctorDao = new DoctorDao();
    private UserDao userDao = new UserDao();
    private SpecialityDao specialityDao = new SpecialityDao();
    private AppointmentDao appointmentDao = new AppointmentDao();

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
                case "/admin_change":
                    adminChangePass(request, response);
                    break;
                case "/admin_update":
                    updateAdmin(request, response);
                    break;
                case "/doctors":
                    getDoctors(request, response);
                    break;
                case "/users":
                    getUsers(request, response);
                    break;
                case "/specialties":
                    getSpecialties(request, response);
                    break;
                case "/new_appointments":
                    getNewAppointments(request, response);
                    break;
                case "/view_appointments":
                    getAppointments(request, response);
                    break;
                case "/add_user":
                    addNewUser(request, response);
                    break;
                case "/add_doctor":
                    addNewDoctor(request, response);
                    break;
                case "/add_specialty":
                    addNewSpecialty(request, response);
                    break;
                case "/add_new_user":
                    addUser(request, response);
                    break;
                case "/edit_user":
                    editUser(request, response);
                    break;
                case "/view_user":
                    viewUser(request, response);
                    break;
                case "/update_user":
                    updateUser(request, response);
                    break;
                case "/add_new_doctor":
                    addDoctor(request, response);
                    break;
                case "/edit_doctor":
                    editDoctor(request, response);
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
        session.removeAttribute("admin");
        session.setAttribute("successMsg", "Successfully Logout");
        response.sendRedirect("admin_login");
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

    private void adminChangePass(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String newPassword = request.getParameter("password");
        String oldPassword = request.getParameter("password2");

        try {
            HttpSession session = request.getSession();

            if (adminDao.validateAdminOldPassword(userID, oldPassword)) {

                if (adminDao.changeAdminPassword(userID, newPassword)) {

                    session.setAttribute("successMsg", "Password successfully changed.");
                    session.removeAttribute("admin");
                    response.sendRedirect("admin_login");

                } else {
                    session.setAttribute("errorMsg", "Password change failed.");
                    response.sendRedirect("admin_password");
                }

            } else {
                session.setAttribute("errorMsg", "Previous password does not match.");
                response.sendRedirect("admin_password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");

        Admin updateUser = new Admin(userID, firstName, lastName, otherName, dob, contact, email);
        System.out.println(updateUser.getUserID());
        boolean u = adminDao.updateAdmin(updateUser);
        HttpSession session = request.getSession();

        if (u == true) {
            Admin updateUserObject = adminDao.getAdminByID(userID);
            session.setAttribute("successMsg", "Profile details updated successfully");
            session.setAttribute("admin", updateUserObject);
            response.sendRedirect("admin_edit");
        } else {
            session.setAttribute("errorMsg", "Profile details failed to update");
            response.sendRedirect("admin_edit");
        }
    }

    private void getDoctors(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Doctor> doctorList = doctorDao.getAllDoctors();
        request.setAttribute("doctorList", doctorList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/doctors.jsp");
        dispatcher.forward(request, response);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<User> userList = userDao.getAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/users.jsp");
        dispatcher.forward(request, response);
    }

    private void getSpecialties(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Speciality> specialityList = specialityDao.getAllSpeciality();
        request.setAttribute("specialityList", specialityList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/specialties.jsp");
        dispatcher.forward(request, response);
    }

    private void getNewAppointments(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Appointment> newAppointments = appointmentDao.selectAllNewAppointments();
        request.setAttribute("newAppointments", newAppointments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/new_appointments.jsp");
        dispatcher.forward(request, response);
    }

    private void getAppointments(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Appointment> appointments = appointmentDao.selectAllAppointments();
        request.setAttribute("appointments", appointments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/appointments.jsp");
        dispatcher.forward(request, response);
    }

    private void addNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/add_user.jsp");
        dispatcher.forward(request, response);
    }

    private void addNewDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Speciality> listSpeciality = specialityDao.getAllSpeciality();
        request.setAttribute("listSpeciality", listSpeciality);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/add_doctor.jsp");
        dispatcher.forward(request, response);
    }

    private void addNewSpecialty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/add_specialty.jsp");
        dispatcher.forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
        String username = request.getParameter("username");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"), df);
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String postalAddress = request.getParameter("postalAddress");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User newUser = new User();
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
                session.setAttribute("successMsg", "Customer successfully added");
                response.sendRedirect("users");
            } else {
                session.setAttribute("errorMsg", "Failed to add customer. Try Again");
                response.sendRedirect("add_user");
            }
        } catch (Exception e) {
            //TODO Auto-genrated catch block
            e.printStackTrace();
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String userID = request.getParameter("id");
        User user = userDao.getUserByID(userID);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/edit_user.jsp");
        dispatcher.forward(request, response);
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String userID = request.getParameter("id");
        User user = userDao.getUserByID(userID);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view_user.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String otherName = request.getParameter("otherName");
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
            response.sendRedirect("users");
        } else {
            session.setAttribute("errorMsg", "Profile details failed to update");
            response.sendRedirect("edit_user");
        }
    }

    private void addDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                session.setAttribute("successMsg", "Doctor added successfully.");
                response.sendRedirect("doctors");
            } else {
                session.setAttribute("errorMsg", "Failed to add Doctor. Try Again");
                response.sendRedirect("add_doctor");
            }
        } catch (Exception e) {
            //TODO Auto-genrated catch block
            e.printStackTrace();
        }
    }

    private void editDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String userID = request.getParameter("id");
        List<Speciality> listSpeciality = specialityDao.getAllSpeciality();
        request.setAttribute("listSpeciality", listSpeciality);
        Doctor doctor = doctorDao.getDoctorByID(userID);
        request.setAttribute("doctor", doctor);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/edit_doctor.jsp");
        dispatcher.forward(request, response);
    }

}
