<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 18/05/2023
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg_color">
    <div class="container-fluid">
        <a class="navbar-brand" href="/hello-servlet"><i class="fa-sharp fa-solid fa-hospital"></i> <strong>DPA </strong> &nbsp; Portal </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="">
                        <i class="fa-solid fa-user-doctor"></i>
                        Doctors
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="">
                        <i class="fa-solid fa-users-medical"></i>
                        Patient
                    </a>
                </li>
            </ul>

            <div class="dropdown">
                <button class="btn btn-light  dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-universal-access"></i> Admin
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="">Logout</a></li>

                </ul>
            </div>
        </div>
    </div>
</nav>
