<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 04/06/2023
  Time: 00:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - View Profile</title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>

</head>
<body style="min-height: 100vh; display: flex; flex-direction: column" class="appoint">
<c:if test="${empty doctor}">
    <c:redirect url="/doctor_login"></c:redirect>
</c:if>

<!-- ===== Header ===== -->
<jsp:include page="../../assets/navbar/doctor_navbar.jsp"></jsp:include>
<!-- ===== End Header ===== -->

<!-- ===== Main Body ===== -->
<main id="main" class="appoint">
    <div class="container">
        <div class="row">
            <div class="breadcrumbs">
                <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><i class="fa-solid fa-gauge"></i> &nbsp;<a href="<%=request.getContextPath()%>/doctor_dashboard">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">View Profile</li>
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
                            <i class="fa-solid fa-user-pen"></i> &nbsp; View Profile
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

                            <label class="form-label">ID</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uID"><i class="fa-solid fa-user-tag"></i></span>
                                <input type="text" class="form-control" aria-label="User ID" aria-describedby="uID" readonly value="<c:out value="${doctor.userID}" />" />
                            </div>
                            <input type="hidden" name="userID" value="<c:out value="${doctor.userID}"/>">
                            <label class="form-label">First Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="fName"><i class="fa fa-user"></i></span>
                                <input type="text" name="firstName" class="form-control" placeholder="First Name" aria-label="First Name" aria-describedby="fName" readonly value="<c:out value="${doctor.firstName}" />"/>
                            </div>
                            <label class="form-label">Last Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="lName"><i class="fa fa-user"></i></span>
                                <input type="text" name="lastName" class="form-control" placeholder="Last Name" aria-label="Last Name" aria-describedby="lName" readonly value="<c:out value="${doctor.lastName}" />" />
                            </div>
                            <label class="form-label">Other Name</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="oName"><i class="fa fa-user"></i></span>
                                <input type="text" name="otherName" class="form-control" placeholder="Other Name" aria-label="Other Name" aria-describedby="oName" readonly value="<c:out value="${doctor.otherName}" />" />
                            </div>
                            <label class="form-label">Username</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uName"><i class="fa fa-tags"></i></span>
                                <input type="text" name="username" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="uName" readonly value="<c:out value="${doctor.username}" />" />
                            </div>
                            <label class="form-label">Date of Birth</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uDob"><i class="fa-solid fa-calendar-days"></i></span>
                                <input type="date" name="dob" class="form-control" aria-label="Date of Birth" aria-describedby="uDob" readonly value="<c:out value="${doctor.dob}" />" />
                            </div>
                            <label class="form-label">Email</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="eMail"><i class="fa-solid fa-envelope"></i></span>
                                <input type="email" name="email" class="form-control" placeholder="Email Address" aria-label="Email Address" aria-describedby="eMail" readonly value="<c:out value="${doctor.email}" />" />
                            </div>
                            <label class="form-label">Phone Number</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uContact"><i class="fa-solid fa-phone"></i></span>
                                <input type="number" name="contact" maxlength="11" class="form-control" placeholder="Contact" aria-label="Phone Number" aria-describedby="uContact" readonly value="<c:out value="${doctor.contact}" />" />
                            </div>
                            <label class="form-label">Specialty</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uAddress"><i class="fa-solid fa-stethoscope"></i></span>
                                <input type="text" name="address" class="form-control" placeholder="Address" aria-label="Address" aria-describedby="uAddress" readonly  value="<c:out value="${doctor.speciality}" />"/>
                            </div>
                            <label class="form-label">Qualification</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uPostal"><i class="fa-solid fa-user-graduate"></i></span>
                                <input type="text" name="postalAddress" class="form-control" placeholder="Postal Address" aria-label="Postal Address" aria-describedby="uAddress" readonly value="<c:out value="${doctor.qualification}" />" />
                            </div>

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
