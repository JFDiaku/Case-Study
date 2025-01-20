<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LOGIN</title>
    <link rel="stylesheet" href="../../pub/css/global.css">
    <link rel="stylesheet" href="../../pub/css/register.css">
</head>
<body>
    <div class="container">
    <div class="nav">
        <a href="/login" class="logo">
            GOPLAY
        </a>

        <a href="" >Home</a>

        <a href="/register" >Join</a>
    </div>




    <div class="form-box">
        <p></p>

        <p class="createAccount">
            Welcome Back<span>.</span>
        </p>

        <p>Dont have an account?<span> <a href="/register">Create account</a> </span></p>


        <c:if test="${param.error eq ''}">
            <div class="message error" id="message">
                Invalid username or password
            </div>
        </c:if>

        <form class="registerForm" id="login-form" action="/login/submit" method="post">

            <div class="inputBox">
                <label class="formLabel" >Email</label>
                <input class="formInput" id="username" name="username" placeholder="MichealJohnson@gmail.com" >
            </div>

            <div class="inputBox">
                <label class="formLabel" >Password</label>
                <input class="formInput" type="password" id="password" name="password" placeholder="*******" >
            </div>

            <button type="submit">
                Login
            </button>

        </form>




    </div>

</div>
</body>
</html>
