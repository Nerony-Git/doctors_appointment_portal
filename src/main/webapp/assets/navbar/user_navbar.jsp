<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 31/05/2023
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header id="header" class="d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
        <div class="logo">
            <h1 class="text-light">
                <a class="navbar-brand" href="<%= request.getContextPath() %>/">
                    <i class="fa-sharp fa-solid fa-hospital"></i>
                    <span><strong>DPA </strong> Portal</span>
                </a>
            </h1>
        </div>

        <nav id="navbar" class="navbar">
            <ul>
                <li class="dropdown"><a href="#"><i class="fa-solid fa-circle-user fa-2x"></i> <span> &nbsp; ${user.firstName} ${user.lastName} </span> <i class="bi bi-chevron-down"></i> </a>
                    <ul>
                        <li><a class="nav-link" href="<%=request.getContextPath()%>/user_edit"><i class="fa-solid fa-id-card"></i> &nbsp; View Profile </a> </li>
                        <li><a class="nav-link" href=""><i class="fa-solid fa-key"></i> &nbsp; Change Password </a> </li>
                        <li><a class="nav-link" href="<%= request.getContextPath() %>/user_logout"><i class="fas fa-sign-in-alt"></i> &nbsp; Logout </a> </li>
                    </ul>
                </li>
            </ul>
            <i class="bi bi-list mobile-nav-toggle"></i>
        </nav>
    </div>
</header>
<%--<nav class="navbar navbar-expand-lg navbar-dark bg_color">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/"><i class="fa-sharp fa-solid fa-hospital"></i> <strong>DPA </strong> &nbsp; Portal </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
    </div>

    <div class="collapse navbar-collapse" id="navbarContent">
      <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="">
            <i class="fas fa-sign-in-alt"></i>
            User Login
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="">
            <i class="fas fa-sign-in-alt"></i>
            Doctor Login
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="">
            <i class="fas fa-sign-in-alt"></i>
            Admin Login
          </a>
        </li>
      </ul>
    </div>
</nav>--%>
