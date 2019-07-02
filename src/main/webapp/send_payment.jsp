<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/didact-gothic.css">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png" />
    <link rel="stylesheet" href="assets/css/style.css">

    <title>Send payment | Payments system</title>
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/user">
            <img class="brand-image navbar-element" src="assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </a>
        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarDropdown" aria-controls="navbarDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/user">Home</a>
                </li>
                <li class="nav-item active">
                    <span class="nav-link">Send&nbsppayment</span>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuCards" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cards</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuCards">
                        <a class="dropdown-item" href="/cards/list">Show cards</a>
                        <a class="dropdown-item" href="/cards/add">Add card</a>
                        <a class="dropdown-item" href="/cards/remove">Remove card</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuAccount" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Account</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuAccount">
                        <a class="dropdown-item" href="/account/refill">Refill account</a>
                        <a class="dropdown-item" href="/account/block">Block account</a>
                    </div>
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
        <div class="base-form">
            <form method="post" action="/send_payment">
                <c:if test="${alert != null}">
                    <div class="alert alert-${alert.type}">${alert.message}</div>
                </c:if>
                <div class="form-group">
                    <label for="account_number">Account number</label>
                    <input name="account_number" type="text" class="form-control" id="account_number" placeholder="Enter account number" minlength="11" maxlength="11" onkeypress="return isNumber(event);" onpaste="return false;" required>
                </div>
                <div class="form-group">
                    <label for="payment_amount">Payment amount</label>
                    <input name="payment_amount" type="text" class="form-control" id="payment_amount" placeholder="Enter payment amount" onkeypress="return checkSumField(event);" onpaste="return false;" required>
                </div>
                <div class="form-group">
                    <label for="comment">Comment</label>
                    <input name="comment" type="text" class="form-control" id="comment" aria-describedby="commentHelp" placeholder="Enter payment comment" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success btn-block shadow rounded">Send payment</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="assets/js/utils.js"></script>
<script src="assets/js/jquery-3.4.1.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>
