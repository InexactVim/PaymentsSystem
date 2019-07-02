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

    <title>Unblock account | Payments System</title>
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-color-dark">
        <a class="navbar-brand text-center" href="/admin">
            <img class="brand-image navbar-element" src="../assets/images/logo.png" alt="logo">
            <span class="brand-font navbar-element">&nbspPayments System</span>
        </a>
        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarDropdown" aria-controls="navbarDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarDropdown">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/admin">Home</a>
                </li>
                <li class="nav-item active">
                    <span class="nav-link">Unblock&nbspaccount</span>
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
        <c:if test="${alert != null}">
            <div class="alert alert-${alert.type}">${alert.message}</div>
        </c:if>
        <c:if test="${blockedAccounts != null}">
            <form class="base-form" method="post" action="/admin/unblock_account">
                <div class="form-group">
                    <label for="account_number">Account number</label>
                    <input name="account_number" type="text" class="form-control" id="account_number" placeholder="Enter account number" minlength="11" maxlength="11" onkeypress="return isNumber(event);" onpaste="return false;" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-warning btn-block shadow rounded">Unblock account</button>
                </div>
            </form>
            <div class="col-12 text-center">
                <p>
                <h2>Blocked accounts</h2>
                <table class="table table-hover">
                    <thead class="bg-color-table">
                    <tr class="color-dark table-column-header">
                        <td>Holder</td>
                        <td>Number</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${blockedAccounts}" var="account">
                        <tr>
                            <td><c:out value="${account.holder}"/></td>
                            <td class="color-blue"><c:out value="${account.number}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<script src="../assets/js/utils.js"></script>
<script src="../assets/js/jquery-3.4.1.min.js"></script>
<script src="../assets/js/popper.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
</body>

</html>
