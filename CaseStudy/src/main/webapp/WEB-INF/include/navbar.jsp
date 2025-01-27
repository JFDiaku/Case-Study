<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<head>
    <link rel="stylesheet" href="../../pub/css/global.css" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="../../pub/js/anim.js"></script>
    <title><%= pageTitle %></title>
    <!-- Page-specific CSS (optional, dynamically included) -->
    <% if (customCss != null) { %>
    <link rel="stylesheet" href="<%= customCss %>">
    <% } %>


</head>
<body>
    <div class="top-nav">

        <a href="" class="nav-logo">
            GOPLAY
        </a>


        <div class="user-box" id="user-box">
            <img src="<sec:authentication property="principal.image" />" alt="" onerror="this.src='../../pub/images/profile.png';" class="user-img">
            <div class="user-welcome">
                <p>Hi, <sec:authentication property="principal.firstName" /></p>
                <p id="current-date">Sunday, June 25, 2024</p>
            </div>
        </div>

        <div class="search-box" id="search-box">
            <img src="../../pub/images/search.png" alt="">
            <input type="text" class="search-bar" placeholder="Search for users, sports or locations">
        </div>


        <div class="top-nav-btns">

            <div class="toggle-dropdown" >
                <p><sec:authentication property="principal.fullName" /></p>
                <button class="drop-button" id="drop-button" onclick="toggleDropdown()">
                    <img src="../../pub/images/down.png" alt="">
                </button>
            </div>

            <div class="dropdown-menu" id="dropdown">

                <a href="/account" class="top-nav-btn">
                    Account
                    <img src="../../pub/images/account.png" alt="">
                </a>


                <a href="/logout" class="top-nav-btn">
                    Logout
                    <img src="../../pub/images/logout.png" alt="">
                </a>
            </div>


        </div>





    </div>
    <script>
        var currentDate = new Date();

       var daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
       var monthsOfYear = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];


       var dayOfWeek = daysOfWeek[currentDate.getDay()];
       var month = monthsOfYear[currentDate.getMonth()];
       var day = currentDate.getDate();
       var year = currentDate.getFullYear();

       var formattedDate = dayOfWeek + ", " + month + " " + day + ", " + year;

       $('#current-date').text(formattedDate);


    $(document).ready(function() {
    // Get the current URL path
    var currentPath = window.location.pathname;
    console.log(currentPath);

    if(currentPath == "/search"){
        $("#search-box").hide();
        $("#user-box").css({
            'margin-right': '40%',
        });
    };


 });

    </script>



