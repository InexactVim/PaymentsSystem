<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="assets/fonts/didact-gothic.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png"/>

    <title>Payments system</title>
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/user">
            <img class="brand-image navbar-element" src="assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </a>
        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarDropdown"
                aria-controls="navbarDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/user">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/send_payment">Send&nbsppayment</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuCards" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cards</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuCards">
                        <a class="dropdown-item" href="/cards/list">Show cards</a>
                        <a class="dropdown-item" href="/cards/add">Add card</a>
                        <a class="dropdown-item" href="/cards/remove">Remove card</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuAccount" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Account</a>
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
        <div class="col-12 text-center">
            <c:if test="${alert != null}">
                <div class="alert alert-${alert.type}">${alert.message}</div>
            </c:if>
        </div>
        <div class="col-12 col-md-4 text-center">
            <c:if test="${user != null}">
                <img src="assets/images/user-logo.png" width="150" height="auto" alt="user logo">
                <p>
                <h2><c:out value="${user.name}"/> <c:out value="${user.surname}"/></h2>
                </p>
                <div class="text-muted"><c:out value="${user.email}"/></div>
                <p>
                <h3>Balance: <span class="color-orange"><c:out value="${accountDisplay.balance}"/>$</span></h3>
                <p>
                <h3>Account number: <span class="color-blue"><c:out value="${accountDisplay.number}"/></span></h3>
            </c:if>
        </div>
        <div class="col-12 col-md-8 text-center">
            <c:if test="${payments != null}">
                <p>
                <h2>Payments History</h2>
                <table class="table table-hover table-responsive-sm">
                    <thead class="bg-color-table">
                    <tr class="color-dark table-column-header">
                        <th>Date</th>
                        <th>Info</th>
                        <th>Sum</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${payments}" var="payment">
                        <tr>
                            <td><span class="color-aqua">${payment.date}</span></td>
                            <td>${payment.info}<br>
                                <span class="text-muted" style="font-size: 16px; text">${payment.comment}</span>
                            </td>
                            <td>${payment.total}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

<script src="assets/js/jquery-3.4.1.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>
