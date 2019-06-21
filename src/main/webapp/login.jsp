<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Didact+Gothic&display=swap">
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/logo.png" />
    <link rel="stylesheet" href="assets/css/style.css">

    <title>Payments system | Login page</title>
</head>

<body>

<header>
    <nav class="navbar navbar-dark fixed-top" id="bg-color-dark">
        <div class="navbar-brand text-center" id="logo-font-size">
            <img src="assets/images/logo.png" class="d-inline-block align-top" alt="Logo icon"><span>&nbspPayments System</span>
        </div>
    </nav>
</header>

<div class="container">
    <div class="row justify-content-center">
        <div class="login-form">
            <form method="post" action="login">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input name="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter your email" required>
                    <span class="form-text" id="color-red">${incorrectEmail}</span>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input name="password" type="password" class="form-control" id="password" placeholder="Enter your password" required>
                    <span class="form-text" id="color-red">${incorrectPassword}</span>
                    <a class="form-text text-muted underlineHover" href="/resetPassword">Forgot password?</a>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-block font-weight-bold shadow rounded" id="bg-color-light-green">Log In</button>
                </div>
                <div>
                    <a role="button" class="btn btn-block font-weight-bold shadow rounded" href="/register" id="bg-color-aqua">Register</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>

</html>
