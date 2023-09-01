<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 31/05/2023
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    
    <meta charset="ISO-8859-1">
    <title>DPA Portal - Book Appointment</title>
    <jsp:include page="../../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>

</head>
<body>
    <c:if test="${empty user}">
        <c:redirect url="/user_login"></c:redirect>
    </c:if>
    <!-- ===== Header ===== -->
    <jsp:include page="../../assets/navbar/user_navbar.jsp"></jsp:include>
    <!-- ===== End Header ===== -->

    <!-- ===== Main Body ===== -->
    <main id="main" class="appoint">
        <div class="container">
            <div class="row">
                <div class="breadcrumbs">
                    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><i class="fa-solid fa-gauge"></i> &nbsp;<a href="<%=request.getContextPath()%>/user_dashboard">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Book Appointment</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
        <div class="container">
            <p class="fs-2"></p>
            <div class="row">
                <div class="col-md-6 d-flex">
                    <div class="card register_card col-md-12">
                        <div class="card-body">
                            <p class="text-center fs-3">Book an Appointment</p>

                            <c:if test="${not empty successMsg}">
                                <p class="text-center text-success fs-5">${successMsg}</p>
                                <c:remove var="successMsg" scope="session" />
                            </c:if>

                            <c:if test="${not empty errorMsg}">
                                <p class="text-center text-danger fs-5">${errorMsg}</p>
                                <c:remove var="errorMsg" scope="session" />
                            </c:if>

                            <form action="<%=request.getContextPath()%>/book" method="post">
                                <input type="hidden" name="userID" value="${user.userID}">


                                <label class="form-label">Appointment ID</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Appointment ID</label>--%>
                                    <span class="input-group-text" id="appointID"><i class="fa-solid fa-tags"></i></span>
                                    <input type="text" name="appointmentID" class="form-control" aria-label="Appointment ID" aria-describedby="appointID" readonly value="DAPT/000" />
                                </div>
                                <label class="form-label">ID</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Full Name</label>--%>
                                    <span class="input-group-text" id="uID"><i class="fa-solid fa-user-tag"></i></span>
                                    <input type="text" class="form-control" aria-label="User ID" aria-describedby="uID" readonly value="<c:out value="${user.userID}" />" />
                                </div>
                                <label class="form-label">Name</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Full Name</label>--%>
                                    <span class="input-group-text" id="fullName"><i class="fa-solid fa-user"></i></span>
                                    <input type="text" name="fullname" class="form-control" aria-label="Full Name" aria-describedby="fullName" readonly value="<c:out value="${user.firstName} ${user.otherName} ${user.lastName}" />" />
                                </div>
                                <label class="form-label">Specialty</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Speciality</label>--%>
                                    <span class="input-group-text" id="uSpeciality"><i class="fa-solid fa-stethoscope"></i></span>
                                    <select name="specialityID" class="form-select" required>
                                        <option selected="selected" disabled="disabled">---Select Specialty---</option>
                                        <!-- for (Speciality speciality: specialities) { -->
                                        <c:forEach var="speciality" items="${listSpeciality}">
                                            <option value="<c:out value="${speciality.sID}"/>"><c:out value="${speciality.specialityName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label class="form-label">Appointment Date</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Appointment Date</label>--%>
                                    <span class="input-group-text" id="uDob"><i class="fa-solid fa-calendar-days"></i></span>
                                    <input type="date" name="appointmentDate" class="form-control" aria-label="Appointment Date" aria-describedby="uDob" placeholder="Appointment Date" required />
                                </div>
                                <label class="form-label">Purpose of Appointment</label>
                                <div class="input-group mb-3">
                                    <%--<label class="form-label">Reason for Appointment</label>--%>
                                    <span class="input-group-text" id="rAppoint"><i class="fa-solid fa-file-pen"></i></span>
                                    <textarea name="description" class="form-control" rows="5" required placeholder="Type the purpose for the appointment here..."></textarea>
                                </div>
                                <input type="hidden" name="status" value="Awaiting">
                                <div class="col-md-12">
                                    <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-paper-plane"></i>&nbsp; Book</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>

                <div class="col-md-6 d-flex">
                    <img src="assets/img/miscellaneous/appointment.png" class="img-fluid" alt="Female Doctor">
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
