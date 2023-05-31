<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 31/05/2023
  Time: 12:03
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
        <div class="container p-5">
            <p class="text-center fs-3">Dashboard</p>

            <div class="row">
                <div class="col-md-4 offset-md-2">
                    <div class="card register_card">
                        <div class="card-body text-center">
                            <i class="fa-solid fa-calendar-plus"></i>
                            <br/>
                            <p class="fs-4 text-center"> Book an Appointment</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card register_card">
                        <div class="card-body text-center">
                            <i class="fa-solid fa-calendar-check"></i>
                            <br/>
                            <p class="fs-4 text-center"> My Appointments</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <!-- ===== End Main Body ===== -->

    <!-- ===== Footer ===== -->
    <jsp:include page="../assets/footer/footer.jsp"></jsp:include>
    <!-- ===== End Footer ===== -->
</body>
</html>
