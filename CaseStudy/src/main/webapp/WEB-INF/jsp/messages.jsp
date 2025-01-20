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

                             <button id="toggle-new-messages">
                                 <img src="../../pub/images/down.png" onclick=toggleNewMessages() alt="">
                             </button>

                         </div>

                         <div class="accordion-body show" id="new-messages" >



                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>




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

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>

                             <div class="chat-card">
                                 <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="">
                                 <div class="details">
                                     <p>Micheal Myers</p>
                                     <p>Hello There, im micheal and you suck</p>
                                 </div>
                             </div>




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
                             <button id="send-chat">
                                 <img src="../../pub/images/send.png" alt="">
                             </button>
                         </div>
                     </div>

                     <div class="current-chat-detail">
                         <div class="top">
                             <a href="./User.html" class="current-chat">
                                 <img src="https://randomuser.me/api/portraits/women/65.jpg" alt="">
                                 <p class="name" >Sarah Simmons</p>
                                 <p class="location">Austin, TX</p>
                             </a>
                         </div>

                         <div class="activities" >
                             <div class="header">
                                 <p>Activities</p>
                             </div>

                             <div class=""  id="current-chat-activities">
                                 <a href="./activities.html">Tennis</a>
                                 <a href="./activities.html">Hockey</a>
                                 <a href="./activities.html">Swimming</a>
                                 <a href="./activities.html">Yoga</a>
                             </div>
                         </div>

                     </div>

                 </div>

             </div>
         </div>

     </div>
 </div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        $("#messagesLink").addClass("active");
        $("#messagesLink .dash-icon").addClass("active");
    });
</script>

</body>
</html>
