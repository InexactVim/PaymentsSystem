<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Refill account | Payments System</title>
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
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuCards" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cards</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuCards">
                        <a class="dropdown-item" href="/cards/list">Cards list</a>
                        <a class="dropdown-item" href="/cards/add">Add card</a>
                        <a class="dropdown-item active" href="/cards/remove">Remove card</a>
                    </div>
                </li>
                <li class="nav-item dropdown active">
                    <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuAccount" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Account</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuAccount">
                        <span class="dropdown-item active">Refill account</span>
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
        <form class="base-form" method="post" action="/account/refill">
            <c:if test="${alert != null}">
                <div class="alert alert-${alert.type}">${alert.message}</div>
            </c:if>
            <c:if test="${creditCards != null}">
                <div class="form-group">
                    <label for="cards_list">Choose a credit card to refill account</label>
                    <select name="card_info" class="form-control" id="cards_list">
                        <c:forEach items="${creditCards}" var="creditCard">
                            <option><c:out value="${creditCard.number}"/>, <c:out
                                    value="${creditCard.expirationDate}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="refill_amount">Refill amount</label>
                    <input name="refill_amount" type="text" class="form-control" id="refill_amount" placeholder="Enter refill amount" onkeypress="return checkSumField(event);" onpaste="return false;" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-block btn-info shadow rounded">Refill account</button>
                </div>
            </c:if>
        </form>
    </div>
</div>

<script src="../assets/js/utils.js"></script>
<script src="../assets/js/jquery-3.4.1.min.js"></script>
<script src="../assets/js/popper.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
</body>

</html>
