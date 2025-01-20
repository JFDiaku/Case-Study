<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>REGISTER</title>
    <link rel="stylesheet" href="../../pub/css/global.css">
    <link rel="stylesheet" href="../../pub/css/register.css">

</head>
<body>

<div class="container">
    <div class="nav">
        <a href="" class="logo">
            GOPLAY
        </a>

        <a href="/login" >Home</a>

        <a href="/login" >Log-in</a>
    </div>

    <div class="form-box">
        <p>START FOR FREE</p>

        <p class="createAccount">
            Create new account<span>.</span>
        </p>

        <p>Already A Member?<span> <a href="../login">Log in</a> </span></p>

        <form class="registerForm" action="/register/submit" method="post">

            <div class="form-top">

                <div class="inputBox">
                    <label class="formLabel" >First name</label>
                    <input class="formInput sm" name="firstName" placeholder="Micheal" required >
                    <c:if test="${bindingResult.hasFieldErrors('firstName')}">
                        <c:forEach var="error" items="${bindingResult.getFieldErrors('firstName')}">
                            <p class="error" >${error.getDefaultMessage()}</p>
                        </c:forEach>
                    </c:if>
                </div>

                <div class="inputBox">
                    <label class="formLabel" >last name</label>
                    <input class="formInput sm" name="lastName" placeholder="Johnson" required >
                    <c:if test="${bindingResult.hasFieldErrors('lastName')}">
                        <c:forEach var="error" items="${bindingResult.getFieldErrors('lastName')}">
                            <p class="error" >${error.getDefaultMessage()}</p>
                        </c:forEach>
                    </c:if>
                </div>

            </div>

            <div class="inputBox">
                <label class="formLabel" >Email</label>
                <input class="formInput" name="email" placeholder="MichealJohnson@gmail.com" required >
                <c:if test="${bindingResult.hasFieldErrors('email')}">
                    <c:forEach var="error" items="${bindingResult.getFieldErrors('email')}">
                        <p class="error" >${error.getDefaultMessage()}</p>
                    </c:forEach>
                </c:if>
            </div>

            <div class="inputBox">
                <label class="formLabel" >Password</label>
                <input class="formInput" name="password" type="password" placeholder="*******" required >
                <c:if test="${bindingResult.hasFieldErrors('password')}">
                    <c:forEach var="error" items="${bindingResult.getFieldErrors('password')}">
                        <p class="error" >${error.getDefaultMessage()}</p>
                    </c:forEach>
                </c:if>
            </div>

            <div class="inputBox">
                <label class="formLabel" >Confirm Password</label>
                <input class="formInput" type="password" name="confirmPassword"  placeholder="*******" required >
                <c:if test="${bindingResult.hasFieldErrors('confirmPassword')}">
                    <c:forEach var="error" items="${bindingResult.getFieldErrors('confirmPassword')}">
                        <p class="error" >${error.getDefaultMessage()}</p>
                    </c:forEach>
                </c:if>
            </div>

            <div class="inputBox terms" >
                <input type="checkbox" name="terms" required>
                <label class="formLabel" ><span class="show-terms">!</span> Terms and Conditions</label>
                <p class="conditions">
                    By using GoPlay's services, you agree to grant GoPlay permission to collect, store, and use your personal information, including but not limited to your name and location. This information will be used to enhance your experience, and improve the platform.
                    <br>
                    By continuing to use GoPlay, you acknowledge and accept these terms.
                </p>
                <c:if test="${bindingResult.hasFieldErrors('terms')}">
                    <c:forEach var="error" items="${bindingResult.getFieldErrors('terms')}">
                        <p class="error" >${error.getDefaultMessage()}</p>
                    </c:forEach>
                </c:if>
            </div>

            <c:if test="${bindingResult.hasGlobalErrors()}">
                <c:forEach var="error" items="${bindingResult.globalErrors}">
                    <p class="error">${error.defaultMessage}</p>
                </c:forEach>
            </c:if>

            <button type="submit">
                Create
            </button>

        </form>




    </div>

</div>

</body>


</html>
