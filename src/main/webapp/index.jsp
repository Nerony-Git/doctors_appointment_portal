<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 18/05/2023
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>DPA Portal - User Login</title>
    <jsp:include page="assets/head/head.jsp"></jsp:include>
    <link rel="stylesheet" href="assets/css/main.css"/>

</head>
<body>

<!-- ===== Slider Section ===== -->
<section id="hero">
    <div class="hero-container">
        <div id="carouselExampleDark" class="carousel slide" data-bs-interval="5000" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="3" aria-label="Slide 4"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="4" aria-label="Slide 5"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/img/slide/slide1.jpg" class="d-block w-100" alt="Slide 1">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2 class="animate__animated animate__fadeInDown">
                                Welcome to the <strong>DPA Portal.</strong>
                            </h2>
                            <p class="animate__animated animate__fadeInUp">Some representative placeholder content for the first slide.</p>
                            <a href="#services" class="btn-started animate__animated animate__fadeInUp scrollto">Get Started</a>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="assets/img/slide/slide2.jpg" class="d-block w-100" alt="Slide 2">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2 class="animate__animated animate__fadeInDown">
                                Welcome to the <strong>DPA Portal.</strong>
                            </h2>
                            <p class="animate__animated animate__fadeInUp">Some representative placeholder content for the first slide.</p>
                            <a href="#services" class="btn-started animate__animated animate__fadeInUp scrollto">Get Started</a>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="assets/img/slide/slide3.jpg" class="d-block w-100" alt="Slide 3">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2 class="animate__animated animate__fadeInDown">
                                Welcome to the <strong>DPA Portal.</strong>
                            </h2>
                            <p class="animate__animated animate__fadeInUp">Some representative placeholder content for the first slide.</p>
                            <a href="#services" class="btn-started animate__animated animate__fadeInUp scrollto">Get Started</a>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="assets/img/slide/slide4.jpg" class="d-block w-100" alt="Slide 4">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2 class="animate__animated animate__fadeInDown">
                                Welcome to the <strong>DPA Portal.</strong>
                            </h2>
                            <p class="animate__animated animate__fadeInUp">Some representative placeholder content for the first slide.</p>
                            <a href="#services" class="btn-started animate__animated animate__fadeInUp scrollto">Get Started</a>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="assets/img/slide/slide5.jpg" class="d-block w-100" alt="Slide 5">
                    <div class="carousel-container">
                        <div class="carousel-content">
                            <h2 class="animate__animated animate__fadeInDown">
                                Welcome to the <strong>DPA Portal.</strong>
                            </h2>
                            <p class="animate__animated animate__fadeInUp">Some representative placeholder content for the first slide.</p>
                            <a href="#services" class="btn-started animate__animated animate__fadeInUp scrollto">Get Started</a>
                        </div>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                <span class="carousel-control-prev-icon bi bi-chevron-double-left" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                <span class="carousel-control-next-icon bi bi-chevron-double-right" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
</section>
<!-- ===== End Slider Section ===== -->

<!-- ===== Header ===== -->
<jsp:include page="assets/header/header.jsp"></jsp:include>
<!-- ===== End Header ===== -->

<!-- ===== Main Body ===== -->
<main id="main">
    <!-- Features Section -->
    <section id="about" class="about">
        <div class="container">
            <div class="section-title">
                <h2>Features</h2>
                <p> The DPA Portal has enumerous features.</p>
            </div>

            <div class="row">
                <div class="col-lg-6">
                    <img src="assets/img/slide/slide3.jpg" class="img-fluid" alt="">
                </div>
                <div class="col-lg-6 pt-4 pt-lg-0 content">
                    <h3>Key Features:</h3>
                    <p class="fst-italic">
                        The Key features include;
                    </p>
                    <ul>
                        <li><i class="bi bi-check-circle"></i> Key Feature 1 </li>
                        <li><i class="bi bi-check-circle"></i> Key Feature 2 </li>
                        <li><i class="bi bi-check-circle"></i> Key Feature 3 </li>
                        <li><i class="bi bi-check-circle"></i> Key Feature 4 </li>
                    </ul>
                    <p class="fst-italic">And also,</p>
                </div>
            </div>
        </div>
    </section>
    <!-- End Features Section -->

    <!-- Counts Section -->
    <section class="counts section-bg">
        <div class="container">

            <div class="row no-gutters">

                <div class="col-lg-3 col-md-6 d-md-flex align-items-md-stretch">
                    <div class="count-box register_card">
                        <i class="bi bi-emoji-smile"></i>
                        <span data-purecounter-start="0" data-purecounter-end="232" data-purecounter-duration="1" class="purecounter"></span>
                        <p><strong>Happy Clients</strong> consequuntur quae qui deca rode</p>
                        <a href="#">Find out more &raquo;</a>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 d-md-flex align-items-md-stretch">
                    <div class="count-box register_card">
                        <i class="bi bi-journal-richtext"></i>
                        <span data-purecounter-start="0" data-purecounter-end="521" data-purecounter-duration="1" class="purecounter"></span>
                        <p><strong>Projects</strong> adipisci atque cum quia aut numquam delectus</p>
                        <a href="#">Find out more &raquo;</a>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 d-md-flex align-items-md-stretch">
                    <div class="count-box">
                        <i class="bi bi-headset"></i>
                        <span data-purecounter-start="0" data-purecounter-end="1463" data-purecounter-duration="1" class="purecounter"></span>
                        <p><strong>Hours Of Support</strong> aut commodi quaerat. Aliquam ratione</p>
                        <a href="#">Find out more &raquo;</a>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 d-md-flex align-items-md-stretch">
                    <div class="count-box">
                        <i class="bi bi-people"></i>
                        <span data-purecounter-start="0" data-purecounter-end="15" data-purecounter-duration="1" class="purecounter"></span>
                        <p><strong>Hard Workers</strong> rerum asperiores dolor molestiae doloribu</p>
                        <a href="#">Find out more &raquo;</a>
                    </div>
                </div>

            </div>

        </div>
    </section>

    <!-- End Counts Section -->

</main>

<!-- ===== End Main Body ===== -->

<!-- Footer -->
<jsp:include page="assets/footer/footer.jsp"></jsp:include>
<!-- End Footer -->

<script type="text/javascript" src="assets/js/main.js"></script>
<script type="text/javascript">

    /**
     * Initiate Pure Counter
     */
    new PureCounter();

</script>

</body>
</html>