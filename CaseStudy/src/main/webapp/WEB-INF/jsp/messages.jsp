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
    String pageTitle = "Messages";
    String customCss = "../../pub/css/messages.css";

%>
<%@ include file="../include/navbar.jsp" %>

 <div class="container">
     <%@ include file="../include/sidebar.jsp" %>
     <div class="content">

         <div class=""></div>

         <div class="inner messages" >
             <div class="">

                 <div class="accordion">

                     <div class="accordion-item">

                         <div class="accordion-header">
                             <p>New Messages</p>

                             <button id="toggle-new-messages" >
                                 <img src="../../pub/images/down.png" class="<c:if test='${empty newMessages}'>show</c:if>"
                                      onclick=toggleNewMessages() alt="">
                             </button>

                         </div>

                         <div class="accordion-body <c:if test='${not empty newMessages}'>show</c:if>" id="new-messages" >

                            <c:forEach var="entry" items="${newMessages}">
                                <c:set var="user" value="${entry.key}" />
                                <c:set var="message" value="${entry.value}" />
                                <div class="chat-card" onclick="changeReceiver(${user.id})">
                                 <img src="${user.image}"  alt="" onerror="this.src='../../pub/images/profile.png';">
                                 <div class="details">
                                     <p>${user.firstName} ${user.lastName}</p>

                                     <p>${message}</p>
                                 </div>
                                </div>

                             </c:forEach>


<%--                             <div class="chat-card">--%>
<%--                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">--%>
<%--                                 <div class="details">--%>
<%--                                     <p>Micheal Myers</p>--%>
<%--                                     <p>Hello There, im micheal and you suck</p>--%>
<%--                                 </div>--%>
<%--                             </div>--%>






                         </div>

                     </div>


                     <div class="accordion-item" style="margin-top: 1rem;">

                         <div class="accordion-header">
                             <p>Active Chats</p>

                             <button id="toggle-active-chat">
                                 <img src="../../pub/images/down.png" onclick=toggleActiveChat() alt="">
                             </button>
                         </div>

                         <div class="accordion-body show" id="active-chats" >

                             <c:forEach var="entry" items="${userMessages}">
                                <c:set var="user" value="${entry.key}" />
                                <c:set var="message" value="${entry.value}" />
                                <div class="chat-card" onclick="changeReceiver(${user.id})">
                                 <img src="${user.image}"  alt="" onerror="this.src='../../pub/images/profile.png';">
                                 <div class="details">
                                     <p>${user.firstName} ${user.lastName}</p>

                                     <p>${message}</p>
                                 </div>
                                </div>

                             </c:forEach>





                         </div>

                     </div>

                 </div>


             </div>


             <div class="chat-box">
                 <div class="chat-box-inner">


                     <div class="middle">



                         <div class="" id="messages">


                             <!-- <div class="outgoing singleMessage">
                               <div class="message-body">
                                 <p>Hello There How are you.</p>
                               </div>
                               <div class="message-details">
                                 <p>02.30 AM</p>
                                 <p>You</p>
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                               </div>
                             </div>

                             <div class="incoming singleMessage">
                               <div class="message-body">
                                 <p>Hello There How are you.</p>
                               </div>
                               <div class="message-details">
                                 <p>02.30 AM</p>
                                 <p>Sarah</p>
                                 <img src="https://randomuser.me/api/portraits/women/65.jpg" alt="">
                               </div>
                             </div> -->

                         </div>



                         <div class="bottom">
                             <textarea name="" placeholder="Type Something...." id="chat-input" maxlength="200" rows="2"></textarea>
                             <button id="send-chat" onclick="sendMessage()">
                                 <img src="../../pub/images/send.png"  alt="">
                             </button>
                         </div>
                     </div>

                     <div class="current-chat-detail">
                         <div class="top">
                             <a href="/user/${recipient.id}" id="current-chat" class="current-chat">
                                 <img id="current-chat-image" src="${recipient.image}"  alt=""
                                      onerror="this.src='../../pub/images/profile.png';">
                                 <p id="current-chat-name"  class="name" >${recipient.firstName} ${recipient.lastName}</p>
                                 <p id="current-chat-location" class="location">${recipient.city}, ${recipient.state}</p>
                             </a>
                         </div>

                         <div class="activities" >
                             <div class="header">
                                 <p>Activities</p>
                             </div>

                             <div class=""  id="current-chat-activities">
                                 <c:forEach var="activity" items="${recipientActivities}">
                                 <a href="/activities">${activity.name}</a>
                                 </c:forEach>
                             </div>
                         </div>

                     </div>

                 </div>

             </div>
         </div>

     </div>
 </div>
<input value="${userId}" style="display:none" id="loggedInUserId">/>
<input value="${recipient.id}" style="display:none" id="recipientId">/>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        $("#messagesLink").addClass("active");
        $("#messagesLink .dash-icon").addClass("active");
    });
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="../../pub/js/messages.js"></script>

</body>
</html>
