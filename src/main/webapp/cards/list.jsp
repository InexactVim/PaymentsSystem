<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="../assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/fonts/didact-gothic.css">
    <link rel="stylesheet" href="../assets/icons/material-icons.css">
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="shortcut icon" type="image/x-icon" href="../assets/images/logo.png">

    <title>List of cards | Payments System</title>
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/user">
            <img class="brand-image navbar-element" src="../assets/images/logo.png" alt="logo">
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
                <li class="nav-item">
                    <a class="nav-link" href="/send_payment">Send&nbsppayment</a>
                </li>
                <li class="nav-item dropdown active">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuCards" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cards</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuCards">
                        <span class="dropdown-item active">Cards list</span>
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
        <div class="col text-center">
            ${alert}
            <h2>My cards</h2>
            <table class="table table-hover">
                <thead class="bg-color-dark">
                <tr class="color-light table-column-header">
                    <td>Card number</td>
                    <td>Expiration date</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${creditCards}" var="creditCard">
                    <tr>
                        <td><c:out value="${creditCard.number}"/></td>
                        <td class="font-weight-bold"><c:out value="${creditCard.expirationDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!--
<div class="container">
  <div class="row">
    <div class="col-12 col-lg-6 cards-background shadow">

    </div>
    <div class="col-12 col-lg-6 cards-background shadow">
      asd
    </div>
    <div class="col-12 col-lg-6 cards-background shadow">
      asd
    </div>
    <div class="col-12 col-lg-6 cards-background shadow">
      asd
    </div>
    <div class="col-12 col-lg-6 cards-background shadow">
      asd
    </div>
  </div>
</div> -->

<script src="../assets/js/jquery-3.4.1.min.js"></script>
<script src="../assets/js/popper.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
</body>

</html>
