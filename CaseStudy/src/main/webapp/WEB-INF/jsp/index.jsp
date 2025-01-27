<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    String pageTitle = "Dashboard";
    String customCss = "../../pub/css/index.css";

%>
<%@ include file="../include/navbar.jsp" %>

<div class="container">
    <%@ include file="../include/sidebar.jsp" %>

    <div class ="content">

        <div class=""></div>
        <div class="inner">
            <div class="account-alert">
                <div class="left">
                    <img src="../../pub/images/bell.png" alt="">
                    <div class="account-message">
                        <p>Hey ${user.firstName},</p>
                        <p>Finish setting up your account.</p>
                    </div>
                </div>

                <a href="/account">
                    Finish set up
                </a>
            </div>

            <div class="dash-top">

                <div class="card">
                    <p class="card-title">Active Users</p>
                    <div class="card-detail">
                        <img src="../../pub/images/account.png" alt="">
                        <p>${userCount}</p>
                    </div>
                </div>

                <div class="card">
                    <p class="card-title">Activities</p>
                    <div class="card-detail">
                        <img src="../../pub/images/running.png" alt="">
                        <p>${activities.size()}</p>
                    </div>
                </div>

                <div class="card">
                    <p class="card-title">New messages</p>
                    <div class="card-detail">
                        <img src="../../pub/images/chat.png" alt="">
                        <p>${newMessages}</p>
                    </div>
                </div>

                <div class="card">
                    <p class="card-title">Current Location</p>
                    <div class="card-detail location">
                        <img src="../../pub/images/location.png" alt="">
                        <p>${user.city}, ${user.state}</p>
                    </div>
                </div>

            </div>

            <div class="dash-bottom">


                <div class="left">
                    <p class="title">People you might like</p>
                    <div class="user-list" id="user-list">
                        <c:forEach var="suggestedUser" items="${suggestedUsers}">
                            <div class="user-card">
                              <a href="/user/${suggestedUser.id}" class="user-link">
                                <img class="user-image" src="${suggestedUser.image}" alt="${suggestedUser.firstName}'s image"
                                  onerror="this.src='../../pub/images/profile.png';">
                              </a>
                              <div class="details">
                                <div>
                                  <h3>${suggestedUser.firstName} ${suggestedUser.lastName}</h3>
                                  <p class="location">${suggestedUser.city}, ${suggestedUser.state}</p>
                                </div>
                                <div class="activities">
                                    <c:forEach var="activity" items="${suggestedUserActivities[suggestedUser]}">
                                        <p class="activity">${activity.name}</p>
                                    </c:forEach>
                                </div>

                              </div>


                            </div>
                        </c:forEach>
                    </div>


                </div>

                <div class="right">

                    <div class="top">
                        <div id="map"></div>
                    </div>

                    <div class="bottom">
                        <p>Active Chats</p>
                        <div class="active-players" id="active-players">

                            <c:forEach var="activeChat" items="${activeChats}">

                                <a href="/messages/${activeChat.id}" class="player">
                                  <img src="${activeChat.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                                  <div class="name">
                                    ${activeChat.firstName}
                                  </div>
                                </a>
                            </c:forEach>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>
<script
        src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&v=weekly"
        defer
></script>
<script>
    $(document).ready(function() {
    $("#dashboardLink").toggleClass("active");
    $("#dashboardLink .dash-icon").toggleClass("active");
 });



function initMap() {
  // Create the map.
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 15,
    center: { lat: ${user.latitude}, lng: ${user.longitude}},
    mapTypeId: "terrain",
  });

  // Construct the circle for each value in citymap.
  // Note: We scale the area of the circle based on the population.

    // Add the circle for this city to the map.
    const cityCircle = new google.maps.Circle({
      strokeColor: "#FF0000",
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: "#FF0000",
      fillOpacity: 0.35,
      map,
      center: { lat: ${user.latitude}, lng: ${user.longitude}},
      radius: 1000,
    });

}

window.initMap = initMap;
</script>
</body>
</html>
