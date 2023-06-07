<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 06/06/2023
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - Add New Doctor </title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <script type="text/javascript" src="assets/js/view_password.js"></script>

</head>
<body style="min-height: 100vh; display: flex; flex-direction: column" class="appoint">
<c:if test="${empty admin}">
    <c:redirect url="/admin_login"></c:redirect>
</c:if>

<!-- ===== Header ===== -->
<jsp:include page="../../assets/navbar/admin_navbar.jsp"></jsp:include>
<!-- ===== End Header ===== -->

<!-- ===== Main Body ===== -->
<main id="main">
    <div class="container">
        <div class="row">
            <div class="breadcrumbs">
                <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><i class="fa-solid fa-gauge"></i> &nbsp;<a href="<%=request.getContextPath()%>/admin_dashboard">Home</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/doctors">Doctors List</a></li>
                        <li class="breadcrumb-item active" aria-current="page"> Add New Doctor </li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="container p-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card register_card">
                    <div class="card-header text-center text-white bg_color">
                        <p class="fs-4 text-center text-white mt-2">
                            <i class="fa-solid fa-user-doctor"></i> &nbsp; Add New Doctor
                        </p>
                    </div>
                    <div class="card-body">
                        <!-- Success Message -->
                        <c:if test="${not empty successMsg}">
                            <p class="text-center text-success fs-5">${successMsg}</p>
                            <c:remove var="successMsg" scope="session" />
                        </c:if>
                        <!-- End Success Message -->

                        <!-- Error Message -->
                        <c:if test="${not empty errorMsg}">
                            <p class="text-center text-danger fs-5">${errorMsg}</p>
                            <c:remove var="errorMsg" scope="session" />
                        </c:if>
                        <!-- End Error Message -->

                        <!-- Form -->
                        <form action="<%=request.getContextPath()%>/add_new_doctor" method="post">
                            <label class="form-label">First Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="fName"><i class="fa fa-user"></i></span>
                                <input type="text" name="firstName" class="form-control" placeholder="First Name" aria-label="First Name" aria-describedby="fName" required />
                            </div>
                            <input type="hidden" name="userID" class="form-control" />
                            <label class="form-label">Last Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="lName"><i class="fa fa-user"></i></span>
                                <input type="text" name="lastName" class="form-control" placeholder="Last Name" aria-label="Last Name" aria-describedby="lName" required />
                            </div>
                            <label class="form-label">Other Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="oName"><i class="fa fa-user"></i></span>
                                <input type="text" name="otherName" class="form-control" placeholder="Other Name" aria-label="Other Name" aria-describedby="oName" />
                            </div>
                            <label class="form-label">Username</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uName"><i class="fa fa-tags"></i></span>
                                <input type="text" name="username" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="uName" required />
                            </div>
                            <label class="form-label">Date of Birth</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uDob"><i class="fa-solid fa-calendar-days"></i></span>
                                <input type="date" name="dob" class="form-control" aria-label="Date of Birth" aria-describedby="uDob" required />
                            </div>
                            <label class="form-label">Email</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="eMail"><i class="fa-solid fa-envelope"></i></span>
                                <input type="email" name="email" class="form-control" placeholder="Email Address" aria-label="Email Address" aria-describedby="eMail" required />
                            </div>
                            <label class="form-label">Phone Number</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uContact"><i class="fa-solid fa-phone"></i></span>
                                <input type="number" name="contact" maxlength="11" class="form-control" placeholder="Contact" aria-label="Phone Number" aria-describedby="uContact" required />
                            </div>
                            <label class="form-label">Specialty</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uSpeciality"><i class="fa-solid fa-stethoscope"></i></span>
                                <select name="speciality" class="form-select" required>
                                    <option selected="selected" disabled="disabled">---Select Speciality---</option>
                                    <c:forEach var="speciality" items="${listSpeciality}">
                                        <option value="<c:out value="${speciality.sID}"/>"><c:out value="${speciality.specialityName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="form-label">Qualification</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uQualification"><i class="fa-solid fa-user-graduate"></i></span>
                                <input type="text" name="qualification" class="form-control" placeholder="Qualification" aria-label="Qualification" aria-describedby="uQualification" required />
                            </div>
                            <label class="form-label">Password</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="pass"><i class="fa-solid fa-lock"></i></span>
                                <input type="text" name="password" id="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="pass" required />
                                <i class=" view_password input-group-text bi bi-eye-slash" id="togglePassword" onclick="showPassword('password', 'togglePassword')"></i>
                            </div>

                            <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-user-plus"></i> &nbsp; Add New Doctor </button>
                        </form>
                        <!-- End Form -->
                        <br/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- ===== End Main Body ===== -->


<!-- ===== Footer ===== -->
<jsp:include page="../../assets/footer/footer.jsp"></jsp:include>
<!-- ===== End Footer ===== -->

<script type="text/javascript" src="assets/js/main.js"></script>

</body>
</html>
