<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 06/06/2023
  Time: 09:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - New Appointments </title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>

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
                        <li class="breadcrumb-item active" aria-current="page"> New Appointments List</li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="card register_card">
                <div class="card-body">
                    <p class="fw-bold text-center text-primary fs-4"> New Appointments List</p>
                    <c:if test="${not empty successMsg}">
                        <p class="text-center text-success fs-5">${successMsg}</p>
                        <c:remove var="successMsg" scope="session" />
                    </c:if>

                    <c:if test="${not empty errorMsg}">
                        <p class="text-center text-danger fs-5">${errorMsg}</p>
                        <c:remove var="errorMsg" scope="session" />
                    </c:if>

                    <table class="table table-striped table-hover">
                        <thead>
                        <tr class="bg_color text-white">
                            <th scope="col">Appointment ID</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Specialty</th>
                            <th scope="col" style="text-align: center">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="appointment" items="${newAppointments}">
                            <tr>
                                <td>DPA-APT-<c:out value="${appointment.appointmentID}"/></td>
                                <td><c:out value="${appointment.userID}"/></td>
                                <td><c:out value="${appointment.specialtyName}"/></td>
                                <td style="text-align: center">
                                    <div class="btn-group">
                                        <a href="assign_doctor?id=<c:out value="${appointment.appointmentID}"/>" class="btn btn-sm btn-warning"><i class="fa-solid fa-hospital-user"></i>&nbsp; Assign </a> &nbsp;
                                        <a href="cancel_appointment?id=<c:out value="${appointment.appointmentID}"/>" class="btn btn-sm btn-danger"><i class="fa-solid fa-trash-can"></i> &nbsp; Delete </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

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
