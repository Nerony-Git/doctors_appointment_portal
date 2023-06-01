<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 18/05/2023
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>DPA Portal - Doctor Login</title>
        <jsp:include page="../../assets/head/head.jsp"></jsp:include>
        <link rel="stylesheet" href="../../assets/css/main.css"/>
        <script type="text/javascript" src="../../assets/js/view_password.js"></script>

    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="../../assets/header/header.jsp"></jsp:include>
        <!-- End Navbar -->

        <div class="container p-5">
            <div class="row">
                <div class="col-md-6 offset-md-3">
                    <div class="card register_card">
                        <div class="card-header text-center text-white bg_color">
                            <p class="fs-4 text-center text-white mt-2">
                                <i class="fa-solid fa-stethoscope"></i> &nbsp;
                                Doctors Login
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
                            <form action="" method="post">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="uName"><i class="fa fa-tags"></i></span>
                                    <input type="text" name="username" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="uName" required />
                                </div>
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="pass"><i class="fa-solid fa-lock"></i></span>
                                    <input type="password" name="password" id="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="pass" required />
                                    <i class=" view_password input-group-text bi bi-eye-slash" id="togglePassword"></i>
                                </div>

                                <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-right-to-bracket"></i> &nbsp; Login </button>
                            </form>
                            <!-- End Form -->
                            <br/>
                            <p>Not registered yet? <a href="" class="text-decoration-none">Register</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>