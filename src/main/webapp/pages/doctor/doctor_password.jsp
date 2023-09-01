<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 04/06/2023
  Time: 00:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - Change Password</title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <script type="text/javascript" src="assets/js/view_password.js"></script>

</head>
<body style="min-height: 100vh; display: flex; flex-direction: column" class="appoint">
<c:if test="${empty doctor}">
    <c:redirect url="/doctor_login"></c:redirect>
</c:if>

<!-- ===== Header ===== -->
<jsp:include page="../../assets/navbar/doctor_navbar.jsp"></jsp:include>
<!-- ===== End Header ===== -->

<!-- ===== Main Body ===== -->
<main id="main">
    <div class="container">
        <div class="row">
            <div class="breadcrumbs">
                <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><i class="fa-solid fa-gauge"></i> &nbsp;<a href="<%=request.getContextPath()%>/doctor_dashboard">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Change Password</li>
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
                            <i class="fa-solid fa-user-pen"></i> &nbsp; Change Password
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
                        <form action="<%=request.getContextPath()%>/doctor_change" method="post">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uID"><i class="fa-solid fa-user-tag"></i></span>
                                <input type="text" class="form-control" aria-label="User ID" aria-describedby="uID" readonly value="<c:out value="${doctor.userID}" />" />
                            </div>
                            <input type="hidden" name="userID" value="<c:out value="${doctor.userID}"/>">
                            <div class="input-group mb-3">
                                &nbsp;
                            </div>
                            <label class="form-label">Old Password</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="pass"><i class="fa-solid fa-lock"></i></span>
                                <input type="password" name="password2" id="password2" class="form-control" placeholder="Enter your old password here..." aria-label="Old Password" aria-describedby="pass" required />
                                <i class=" view_password input-group-text bi bi-eye-slash" id="togglePassword2" onclick="showPassword('password2', 'togglePassword2')"></i>
                            </div>
                            <label class="form-label">New Password</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="passed"><i class="fa-solid fa-lock"></i></span>
                                <input type="password" name="password" id="password" class="form-control" placeholder="Enter your new password here..." aria-label="New Password" aria-describedby="passed" required />
                                <i class=" view_password input-group-text bi bi-eye-slash" id="togglePassword" onclick="showPassword('password', 'togglePassword')"></i>
                            </div>

                            <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-user-gear"></i> &nbsp; Update Password </button>
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
