<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 02/06/2023
  Time: 02:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title> DPA Portal - Edit Profile</title>
  <jsp:include page="../../assets/head/head.jsp"></jsp:include>
  <link rel="stylesheet" href="assets/css/main.css"/>
</head>

<body style="min-height: 100vh; display: flex; flex-direction: column">
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
              <i class="fa-solid fa-user-pen"></i> &nbsp; Edit Profile
            </p>
          </div>
          <div class="card-body">
            <!-- Success Message -->
            <c:if test="${not empty successMsg}">
              <p class="text-center text-success fs-3">${successMsg}</p>
              <c:remove var="successMsg" scope="session" />
            </c:if>
            <!-- End Success Message -->

            <!-- Error Message -->
            <c:if test="${not empty errorMsg}">
              <p class="text-center text-danger fs-3">${errorMsg}</p>
              <c:remove var="errorMsg" scope="session" />
            </c:if>
            <!-- End Error Message -->

            <!-- Form -->
            <form action="" method="post">
              <div class="input-group mb-3">
                <span class="input-group-text" id="uID"><i class="fa-solid fa-user-tag"></i></span>
                <input type="text" name="userID" class="form-control" aria-label="User ID" aria-describedby="uID" readonly value="<c:out value="${user.userID}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="fName"><i class="fa fa-user"></i></span>
                <input type="text" name="firstName" class="form-control" placeholder="First Name" aria-label="First Name" aria-describedby="fName" required value="<c:out value="${user.firstName}" />"/>
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="lName"><i class="fa fa-user"></i></span>
                <input type="text" name="lastName" class="form-control" placeholder="Last Name" aria-label="Last Name" aria-describedby="lName" required value="<c:out value="${user.lastName}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="oName"><i class="fa fa-user"></i></span>
                <input type="text" name="otherName" class="form-control" placeholder="Other Name" aria-label="Other Name" aria-describedby="oName" value="<c:out value="${user.otherName}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="uName"><i class="fa fa-tags"></i></span>
                <input type="text" name="username" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="uName" readonly value="<c:out value="${user.username}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="uDob"><i class="fa-solid fa-calendar-days"></i></span>
                <input type="date" name="dob" class="form-control" aria-label="Date of Birth" aria-describedby="uDob" required value="<c:out value="${user.dob}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="eMail"><i class="fa-solid fa-envelope"></i></span>
                <input type="email" name="email" class="form-control" placeholder="Email Address" aria-label="Email Address" aria-describedby="eMail" required value="<c:out value="${user.email}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="uContact"><i class="fa-solid fa-phone"></i></span>
                <input type="number" name="contact" maxlength="11" class="form-control" placeholder="Contact" aria-label="Phone Number" aria-describedby="uContact" required value="<c:out value="${user.contact}" />" />
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="uAddress"><i class="fa-solid fa-location-dot"></i></span>
                <input type="text" name="address" class="form-control" placeholder="Address" aria-label="Address" aria-describedby="uAddress" required  value="<c:out value="${user.address}" />"/>
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text" id="uPostal"><i class="fa-solid fa-map-location-dot"></i></span>
                <input type="text" name="postalAddress" class="form-control" placeholder="Postal Address" aria-label="Postal Address" aria-describedby="uAddress" required value="<c:out value="${user.postalAddress}" />" />
              </div>

              <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-user-gear"></i> &nbsp; Update Profile </button>
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
