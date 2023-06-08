<%--
  Created with IntelliJ IDEA.
  Author: George Amuzu
  User: gnamu
  Date: 07/06/2023
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

  <meta charset="ISO-8859-1">
  <title>DPA Portal - Assign Doctor </title>
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
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/new_appointments">New Appointments List</a></li>
            <li class="breadcrumb-item active" aria-current="page"> Assign Doctor </li>
          </ol>
        </nav>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="row">
      <div class="card register_card">
        <div class="card-header text-center text-white bg_color">
          <p class="fs-4 text-center text-white mt-2">
            <i class="fa-solid fa-calendar-week"></i> &nbsp; Assign A Doctor
          </p>
        </div>

        <div class="card-body">
          <c:if test="${not empty successMsg}">
            <p class="text-center text-success fs-5">${successMsg}</p>
            <c:remove var="successMsg" scope="session" />
          </c:if>

          <c:if test="${not empty errorMsg}">
            <p class="text-center text-danger fs-5">${errorMsg}</p>
            <c:remove var="errorMsg" scope="session" />
          </c:if>

          <form action="<%=request.getContextPath()%>" method="post">
          <div class="row">
            <div class="col-md-6">
              <label class="form-label">Appointment ID</label>
              <div class="input-group">
                <span class="input-group-text" id="aID"><i class="fa-solid fa-tags"></i></span>
                <input type="text" class="form-control" aria-label="Appointment" aria-describedby="aID" readonly value="DAPT/<c:out value="${appointment.appointmentID}"/>" />
              </div>
              <input type="hidden" name="appointmentID" value="<c:out value="${appointment.appointmentID}"/>">
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Customer ID</label>
              <div class="input-group">
                <span class="input-group-text" id="uID"><i class="fa-solid fa-user-tag"></i></span>
                <input type="text" name="userID" class="form-control" aria-label="User ID" aria-describedby="uID" readonly value="<c:out value="${appointment.userID}"/>" />
              </div>
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Speciality</label>
              <div class="input-group">
                <span class="input-group-text" id="sID"><i class="fa-solid fa-tag"></i></span>
                <input type="text" name="specialityID" class="form-control" aria-label="Speciality ID" aria-describedby="sID" readonly value="<c:out value="${appointment.specialtyName}"/>" />
              </div>
              <div class="input-group">&nbsp;</div>
            </div>
            <div class="col-md-6">
              <label class="form-label">Doctor</label>
              <div class="input-group">
                <span class="input-group-text" id="dID"><i class="fa-solid fa-user-doctor"></i></span>
                <select name="doctorID" class="form-select" aria-label="Doctor" aria-describedby="dID" required>
                <option selected="selected" disabled="disabled">---Select Doctor---</option>
                <!-- for (Speciality speciality: specialities) { -->
                <c:forEach var="speciality" items="${specialtyDoctors}">
                  <option value="<c:out value="${speciality.userID}"/>"><c:out value="${speciality.firstName} ${speciality.otherName} ${speciality.lastName}"/></option>
                </c:forEach>
              </select>
              </div>
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Appointment Date</label>
              <div class="input-group">
                <span class="input-group-text" id="aDate"><i class="fa-solid fa-calendar-days"></i></span>
                <input type="text" name="appointmentDate" class="form-control" aria-label="Appointment Date" aria-describedby="aDate" readonly value="<c:out value="${appointment.appointmentDate}"/>" />
              </div>
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Appointment Status</label>
              <div class="input-group">
                <span class="input-group-text" id="stat"><i class="fa-solid fa-spinner"></i></span>
                <input name="status" class="form-control" aria-label="Status" aria-describedby="stat" readonly value="<c:out value="${appointment.status}"/>">
              </div>
            </div>
            <div class="col-md-12">
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Reasons for Appointment</label>
              <div class="input-group">
                <span class="input-group-text" id="descrip"><i class="fa-solid fa-file-waveform"></i></span>
                <textarea name="description" class="form-control" rows="5" aria-label="Description" aria-describedby="descrip"  readonly><c:out value="${appointment.description}"/></textarea>
              </div>
              <div class="input-group">&nbsp;</div>
              <label class="form-label">Comment</label>
              <div class="input-group">
                <span class="input-group-text" id="res"><i class="fa-solid fa-file-medical"></i></span>
                <textarea name="response" class="form-control" rows="5" aria-label="Response" aria-describedby="res" readonly><c:out value="${appointment.response}"/></textarea>
              </div>
              <div class="input-group">&nbsp;</div>
              <div class="input-group">
                <button type="submit" class="btn bg_color text-white col-md-12"><i class="fa-solid fa-hospital-user"></i>&nbsp; Assign Doctor</button>
              </div>
              <div class="input-group">&nbsp;</div>
            </div>
          </div>
          </form>
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
