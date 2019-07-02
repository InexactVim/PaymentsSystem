<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="assets/fonts/didact-gothic.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png" />

    <title>Admin | Payments system</title>
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/admin">
            <img class="brand-image navbar-element" src="assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </a>
        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarDropdown" aria-controls="navbarDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <span class="nav-link">Home</span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/unblock_account">Unblock&nbspaccount</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="log-out-btn" href="/logout">Log&nbspout</a>
                </li>
            </ul>
        </div>
    </nav>
</header>

<div class="container">
    <div class="row justify-content-center">
        <div class="col text-center">
            <h2>Administrator page</h2>
            <img src="assets/images/admin-logo.png" width="150" height="auto" alt="admin logo">
            <p>
            <h2><c:out value="${user.name}"/> <c:out value="${user.surname}"/></h2>
            <p>
            <div class="text-muted"><c:out value="${user.email}"/></div>
        </div>
    </div>
</div>

<script src="assets/js/jquery-3.4.1.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>
