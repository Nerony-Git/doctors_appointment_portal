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
                            <li class="breadcrumb-item active" aria-current="page"><i class="fa-solid fa-gauge"></i> &nbsp;Home</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
        <div class="container p-5">
            <%--<p class="text-center fs-3">Dashboard</p>--%>
            <div class="row dash">
                <div class="col-md-4 offset-md-2">
                    <a href="<%=request.getContextPath()%>/book_appointment">
                        <div class="card register_card">
                            <div class="card-body text-center">
                                <br/>
                                <i class="fa-solid fa-calendar-plus fa-3x"></i>
                                <br/>
                                <br/>
                                <p class="fs-4 text-center"> Book <br/> Appointment</p>
                            </div>
                        </div>
                    </a>
                </div>

                <div class="col-md-4">
                    <a href="">
                        <div class="card register_card">
                            <div class="card-body text-center">
                                <br/>
                                <i class="fa-solid fa-calendar-check fa-3x"></i>
                                <br/>
                                <br/>
                                <p class="fs-4 text-center"> My <br/> Appointments</p>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </main>
    <!-- ===== End Main Body ===== -->

    <!-- ===== Footer ===== -->
    <jsp:include page="../../assets/footer/footer.jsp"></jsp:include>
    <!-- ===== End Footer ===== -->
</body>
</html>
