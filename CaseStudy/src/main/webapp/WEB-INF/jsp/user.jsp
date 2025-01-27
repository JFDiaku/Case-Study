<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    String pageTitle = "User";
    String customCss = "../../pub/css/user.css";
%>
<%@ include file="../include/navbar.jsp" %>

<div class="container">
    <%@ include file="../include/sidebar.jsp" %>

    <div class="content">
        <div></div>
        <div class="inner User">
            <div class="left">
                <img class="user_image" src="${user.image}" alt=""  onerror="this.src='../../pub/images/profile.png';">


                <div class="user_activities">
                    <p>${user.firstName}'s Activities</p>

                    <div class="activities">
                       <c:forEach var="activity" items="${activities}">
                        <a href="">${activity.name}</a>
                       </c:forEach>
                    </div>
                </div>

                <a class="chat-btn" href="/messages/${user.id}">
                    <img src="../../pub/images/chat.png">
                    <p>Send message</p>
                </a>
            </div>

            <div class="">
                <p class="user_name">${user.firstName} ${user.lastName}</p>

                <div class="user_location">
                    <img src="../../pub/images/location.png" alt="" >
                    <p>${user.city}, ${user.state}</p>
                </div>

                <div class="user-approx-location" id="map">

                </div>

                <p class="user_bio">${user.description}</p>





            </div>

            <div class="mutuals">
                <p class="title">Mutual profiles</p>
                <div class="mutual-cards">
                    <c:forEach var="mutualChat" items="${mutualChats}">
                        <a href="/user/${mutualChat.id}" class="mutual-card">
                            <img src="${mutualChat.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                            <p class="mutual-name">${mutualChat.firstName} ${mutualChat.lastName}</p>
                            <p class="mutual-location">${mutualChat.city}, ${mutualChat.state}</p>
                        </a>
                    </c:forEach>

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
    document.addEventListener("DOMContentLoaded", function() {
        $("#searchLink").addClass("active");
        $("#searchLink .dash-icon").addClass("active");
    });

function initMap() {
  // Create the map.
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 13,
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
      radius: 600,
    });

}

window.initMap = initMap;
</script>
</body>
</html>
