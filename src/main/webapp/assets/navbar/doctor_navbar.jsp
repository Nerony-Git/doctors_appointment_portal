<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 04/06/2023
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="header" class="d-flex align-items-center">
  <div class="container d-flex align-items-center justify-content-between">
    <div class="logo">
      <h1 class="text-light">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/doctor_dashboard">
          <i class="fa-sharp fa-solid fa-hospital"></i>
          <span><strong>DPA </strong> Portal</span>
        </a>
      </h1>
    </div>

    <nav id="navbar" class="navbar">
      <ul>
        <li class="dropdown"><a href="#"><i class="fa-solid fa-circle-user fa-2x"></i> <span> &nbsp; ${doctor.firstName} ${doctor.lastName} </span> <i class="bi bi-chevron-down"></i> </a>
          <ul>
            <li><a class="nav-link" href="<%=request.getContextPath()%>/doctor_view"><i class="fa-solid fa-id-card"></i> &nbsp; View Profile </a> </li>
            <li><a class="nav-link" href="<%=request.getContextPath()%>/doctor_edit"><i class="fa-solid fa-user-pen"></i> &nbsp; Edit Profile </a> </li>
            <li><a class="nav-link" href="<%=request.getContextPath()%>/doctor_password"><i class="fa-solid fa-key"></i> &nbsp; Change Password </a> </li>
            <li><a class="nav-link" href="<%= request.getContextPath() %>/doctor_logout"><i class="fas fa-sign-in-alt"></i> &nbsp; Logout </a> </li>
          </ul>
        </li>
      </ul>
      <i class="bi bi-list mobile-nav-toggle"></i>
    </nav>
  </div>
</header>
