<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/didact-gothic.css">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png" />
    <link rel="stylesheet" href="assets/css/style.css">

    <title>Log In | Payments system</title>
</head>

<body>

<header>
    <nav class="navbar navbar-dark fixed-top bg-color-dark">
        <div class="navbar-brand text-center">
            <img class="brand-image navbar-element" src="assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </div>
    </nav>
</header>

<div class="container">
    <div class="row justify-content-center">
        <form class="base-form" action="/login" method="post">
            <c:if test="${alert != null}">
                <div class="alert alert-${alert.type}">${alert.message}</div>
            </c:if>
            <div class="form-group">
                <label for="email">Email address</label>
                <input name="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter your email" maxlength="45" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input name="password" type="password" class="form-control" id="password" placeholder="Enter your password" required>
                <a class="form-text text-muted underlineHover" href="/reset_password">Forgot password?</a>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-block font-weight-bold shadow rounded bg-color-light-green" onclick="onClick()">Log In</button>
            </div>
            <div>
                <a role="button" class="btn btn-block font-weight-bold shadow rounded bg-color-aqua" href="/register">Register</a>
            </div>
        </form>
    </div>
</div>

<script src="assets/js/jquery-3.4.1.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>
