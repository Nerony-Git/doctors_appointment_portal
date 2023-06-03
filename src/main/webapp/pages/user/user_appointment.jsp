<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 02/06/2023
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - My Appointments</title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>

</head>
<body style="min-height: 100vh; display: flex; flex-direction: column" class="appoint">
<c:if test="${empty user}">
    <c:redirect url="/user_login"></c:redirect>
</c:if>
<!-- ===== Header ===== -->
<jsp:include page="../../assets/navbar/user_navbar.jsp"></jsp:include>
<!-- ===== End Header ===== -->

<!-- ===== Main Body ===== -->
<main id="main">
    <div class="container">
        <div class="row">
            <div class="breadcrumbs">
                <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><i class="fa-solid fa-gauge"></i> &nbsp;<a href="<%=request.getContextPath()%>/user_dashboard">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page"> My Appointments</li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="card register_card">
                <div class="card-body">
                    <p class="fw-bold text-center text-primary fs-4"> My Appointment List</p>
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
                            <th scope="col">Appointment Date</th>
                            <th scope="col">Speciality</th>
                            <th scope="col">Description</th>
                            <th scope="col">Status</th>
                            <th scope="col">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="appointment" items="${appointmentList}">
                            <tr>
                                <td>DAPT/<c:out value="${appointment.appointmentID}"/></td>
                                <td><c:out value="${appointment.appointmentDate}"/></td>
                                <td><c:out value="${appointment.specialityID}"/></td>
                                <td><c:out value="${appointment.description}"/></td>
                                <%--<td><c:out value="${appointment.status}"/></td>--%>
                                <td>
                                    <c:choose>
                                        <c:when test="${appointment.status == 'Pending'}">
                                            <span class="badge bg-primary">Pending</span>
                                        </c:when>
                                        <c:when test="${appointment.status == 'Completed'}">
                                            <span class="badge bg-success">Completed</span>
                                        </c:when>
                                        <c:when test="${appointment.status == 'Canceled'}">
                                            <span class="badge bg-danger">Canceled</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning">Awaiting</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="user_view_appointment?id=<c:out value="${appointment.appointmentID}"/>" class="btn btn-sm btn-primary"><i class="fa-solid fa-eye"></i>&nbsp; View</a> </td>
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

