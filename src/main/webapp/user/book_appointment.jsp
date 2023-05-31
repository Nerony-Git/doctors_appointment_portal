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
    <title>DPA Portal - Dashboard</title>
    <jsp:include page="../assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="../assets/css/main.css"/>

</head>
<body>
    <!-- ===== Header ===== -->
    <jsp:include page="../assets/navbar/user_navbar.jsp"></jsp:include>
    <!-- ===== End Header ===== -->

    <!-- ===== Main Body ===== -->
    <main id="main">
        <div class="container p-3">
            <p class="fs-2"></p>
            <div class="row">
                <div class="col-md-6 p-5">
                    <div class="card register_card">
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

                            <form class="row g-3" action="" method="post">
                                <input type="hidden" name="userID" value="${user.userID}">

                                <div class="col-md-6 input-group">
                                    <span class="input-group-text" id="appointID"><i class="fa-solid fa-tags"></i></span>
                                    <input type="text" name="appointmentID" class="form-control" aria-label="Appointment ID" aria-describedby="appointID" readonly />
                                </div>
                                <div class="col-md-6 input-group">
                                    <span class="input-group-text" id="fullName"><i class="fa-solid fa-user-tag"></i></span>
                                    <input type="text" name="fullname" class="form-control" aria-label="Full Name" aria-describedby="fullName" readonly />
                                </div>
                            </form>

                        </div>
                    </div>
                </div>

                <div class="col-md-6"></div>
            </div>
        </div>
    </main>
    <!-- ===== End Main Body ===== -->

    <!-- ===== Footer ===== -->
    <jsp:include page="../assets/footer/footer.jsp"></jsp:include>
    <!-- ===== End Footer ===== -->
</body>
</html>
