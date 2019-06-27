<%--@elvariable id="alert" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="assets/fonts/didact-gothic.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png" />

    <title>Reset password | Payments system</title>
</head>

<body>

<header>
    <nav class="navbar navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/login">
            <img class="brand-image navbar-element" src="assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </a>
    </nav>
</header>

<div class="container">
    <div class="row justify-content-center">
        <div class="reset-password-form">
            ${alert}
            <form method="post" action="/reset_password">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter your email" maxlength="45" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-block font-weight-bold shadow rounded bg-color-light-green">Reset password</button>
                </div>
                <a role="button" class="btn btn-block nav-back-btn shadow rounded bg-color-orange" href="/login">Cancel</a>
            </form>
        </div>
    </div>
</div>

<script src="assets/js/jquery-3.4.1.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>
