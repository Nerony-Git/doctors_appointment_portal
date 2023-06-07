<%--
  Created with IntelliJ IDEA.
  Author: George Amuzu
  User: gnamu
  Date: 06/06/2023
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="ISO-8859-1">
    <title>DPA Portal - Edit Specialty </title>
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/specialties">Specialty List</a></li>
                        <li class="breadcrumb-item active" aria-current="page"> Edit Specialty </li>
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
                            <i class="fa-solid fa-user-doctor"></i> &nbsp; Edit Specialty
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
                        <form action="<%=request.getContextPath()%>/" method="post">
                            <label class="form-label">Specialty ID</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="lName"><i class="fa fa-tags"></i></span>
                                <input type="text" name="sID" class="form-control" placeholder="Generated Automatically" aria-label="Last Name" aria-describedby="lName" readonly />
                            </div>
                            <div class="input-group mb-3">&nbsp;</div>
                            <label class="form-label">Specialty</label>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="uName"><i class="fa-solid fa-suitcase-medical"></i></span>
                                <input type="text" name="specialtyName" class="form-control" placeholder="Enter Specialty Name" aria-label="Username" aria-describedby="uName" required />
                            </div>

                            <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-notes-medical"></i> &nbsp; Add New Specialty </button>
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
