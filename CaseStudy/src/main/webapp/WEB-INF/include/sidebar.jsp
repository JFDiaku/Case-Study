<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/8/2025
  Time: 9:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="nav-btns">

  <p>MENU</p>


  <div class="nav-btn " id="dashboardLink">
    <img class="dash-icon "  src="../../pub/images/dashboard.png" alt="">
    <a class="nav-link" href="/dashboard">Dashboard</a>
  </div>

  <div class="nav-btn " id="searchLink">
    <img class="dash-icon "   src="../../pub/images/search.png" alt="">
    <a class="nav-link"  href="/search">Search</a>
  </div>

  <div class="nav-btn "  id="activitiesLink">
    <img class="dash-icon "   src="../../pub/images/running.png" alt="">
    <a class="nav-link "  href="/activities">Activities</a>
  </div>

  <div class="nav-btn "  id="messagesLink">
    <img class="dash-icon "   src="../../pub/images/chat.png" alt="">
    <a class="nav-link"  href="/messages">Messages</a>
  </div>


  <p>SETTINGS</p>

  <div class="nav-btn"  id="accountLink">
    <img class="dash-icon"   src="../../pub/images/account.png" alt="">
    <a class="nav-link"  href="/account">Account</a>
  </div>

  <div class="nav-btn logout" >
    <img class="dash-icon" src="../../pub/images/logout.png" alt="">
    <a class="nav-link"  href="/logout">Logout</a>
  </div>
</div>

