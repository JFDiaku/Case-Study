<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<%
    String pageTitle = "Activities";
    String customCss = "../../pub/css/activities.css";

%>
<%@ include file="../include/navbar.jsp" %>

 <div class="container">
     <%@ include file="../include/sidebar.jsp" %>
     <div class="content">
         <div class=""></div>

         <div class="inner activities">
             <div class="groups">

                 <div class="search-box">
                     <img src="../../pub/images/search.png" alt="">
                     <input type="text" class="search-bar" placeholder="Search for Activities">
                 </div>

                <sec:authorize access="hasAuthority('ADMIN')">
                 <div class="create-activity-box">
                     <div class="header">
                         <p>Create Activity</p>
                         <button id="toggle-create-activity" onclick="toggleCreatActivity()">
                             <img src="../../pub/images/down.png" alt="">
                         </button>
                     </div>
                     <form action="" id="create-activity-form"  class="">

                         <div class="form-group">
                             <label for="">Activity Name:</label>
                             <input type="text" name="">
                         </div>

                         <div class="form-group">
                             <label for="">Description:</label>
                             <textarea name="description" id="" maxlength="250"></textarea>
                         </div>

                         <button id="create-activity">
                             Create
                             <img src="../../pub/images/edit.png" alt="">
                         </button>
                     </form>
                 </div>
                </sec:authorize>

                <c:forEach var="entry" items="${activities}">
                    <c:set var="activity" value="${entry.key}" />
                    <c:set var="user" value="${entry.value}" />
                    <div class="group-card" onclick='changeActivity("${activity.name}")'>

                     <div class="top">
                         <p class="title">${activity.name}</p>
                         <p class="description"> ${activity.description}</div>

                     <div class="details">
                         <div class="group-photos">
                             <c:forEach var="member" items="${user}" begin="0" end="3">
                               <img src="${member.image}"  class="card" alt="" onerror="this.src='../../pub/images/profile.png';">
                             </c:forEach>

                             <c:if test="${user.size() > 4}">
                                <div class="card">+${user.size() - 4}</div>
                             </c:if>


                         </div>

                         <div class="group-details">
                             <img src="../../pub/images/group.png" alt="">
                             <p>${user.size()} Member(s)</p>
                         </div>
                     </div>


                 </div>
                </c:forEach>




             </div>

             <div class="activity-section" >
                 <h1 class="title" id="title">${activity.name}</h1>

                 <p class="description" id="description">${activity.description}</p>

                 <p class="members-title" >MEMBERS</p>

                 <div id="members" class="members">
                     <c:forEach var="member" items="${members}">
                     <div class="member-card">

                         <a href="/user/${member.id}">
                             <img class="member-image" src="${member.image}" onerror="this.src='../../pub/images/profile.png';" alt="">
                         </a>


                         <p>${member.firstName} ${member.lastName}</p>
                         <p class="location">${member.city}, ${member.state}</p>


                     </div>
                     </c:forEach>
                 </div>

             </div>
         </div>

     </div>
 </div>
<script src="../../pub/js/activity.js"></script>

</body>
</html>
