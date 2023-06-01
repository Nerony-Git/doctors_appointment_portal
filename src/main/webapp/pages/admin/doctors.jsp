<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 18/05/2023
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>DPA Portal - List of Doctors</title>
        <jsp:include page="../../assets/head/head.jsp"></jsp:include>
        <link rel="stylesheet" href="../../assets/css/main.css"/>
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="../../assets/navbar/admin_navbar.jsp"></jsp:include>
        <!-- End Navbar -->

        <div class="container-fluid p-3">
            <div class="card register_card">
                <div class="card-body">
                    <p class="fs-3 text-center">Doctors</p>
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

                    <!-- Doctors List -->
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr class="table-info">
                                <th scope="col">ID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Contact</th>
                                <th scope="col">Specialist</th>
                                <th scope="col">Qualification</th>
                                <th colspan="2" class="text-center" scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <!-- End Doctors List -->
                </div>
            </div>
        </div>

    </body>
</html>
