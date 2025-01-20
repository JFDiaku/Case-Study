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
    String pageTitle = "Account";
    String customCss = "../../pub/css/account.css";

%>
<%@ include file="../include/navbar.jsp" %>
<div class="container">
    <%@ include file="../include/sidebar.jsp" %>
        <div class ="content">

            <div></div>
            <div class="inner account">

                <p class="edit-profile">Edit Profile</p>

                <div class="edit-profile-box" >

                    <div class="profile-picture">
                        <div class="profile-picture-box"  >
                            <img class="profile-pic" id="profile-pic" src="${User.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                            <img title="Upload new profile picture"  class="edit" src="../../pub/images/edit.png" alt="">

                            <input type="file" class="image-upload" onchange="updateCurrentFile(this)" id="image-upload" onclick="showUpload()"  name="upload">
                        </div>

                        <div class="upload-box" id="upload-box">
                            <div id="current-file-box" class="current-file">
                                <p >Current File:</p>
                                <p id="current-file"></p>
                            </div>
                            <div class="save-image" id="save-image">
                                <div title="upload" class="upload-image-button" onclick="uploadImage()" >Upload Image</div>
                                <div title="exit image upload" class="close-upload" onclick="closeSaveImage()">X</div>
                            </div>
                        </div>
                    </div>

                    <div class="form-box contact-form-box">

                        <div class="top">
                            <p class="form-title">Personal Info</p>

                            <p id="personal-response-message" >Message from database</p>

                            <div class="edit-info" id="edit-personal" onclick="editPersonal()" >
                                <img src="../../pub/images/edit.png" alt="">
                                Edit
                            </div>


                            <div class="save-info" id="save-personal" onclick="updatePersonal()" >
                                <img src="../../pub/images/save.png" alt="">
                                Save
                            </div>


                        </div>


                        <form action="" id="personal-form"  class="editable-form personal-form" >

                            <div class="form-group">
                                <label for="fname">First-name</label>
                                <input id="fname" type="text" value="${User.firstName}" placeholder="${User.firstName}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="lname">Last-name</label>
                                <input id="lname" type="text" value="${User.lastName}" placeholder="${User.lastName}" disabled>
                            </div>

                            <div class="form-group ">
                                <label for="email">Email</label>
                                <input id="email" type="text" value="${User.email}" placeholder="${User.email}" disabled>
                            </div>

                        </form>
                    </div>

                    <div class="form-box location-form-box">

                        <div class="top">
                            <p class="form-title">Location</p>

                            <p id="location-response-message" >Message from database</p>

                            <div class="edit-info" id="edit-location" onclick="editLocation()" >
                                <img src="../../pub/images/edit.png" alt="">
                                Edit
                            </div>


                            <div class="save-info" id="save-location" onclick="updateLocation()" >
                                <img src="../../pub/images/save.png" alt="">
                                Save
                            </div>
                        </div>


                        <form action="" class="editable-form location-form ">
                            <div class="form-group">
                                <Label>City</Label>
                                <input type="text" id="city" value="${User.city}" placeholder="${User.city}" disabled>
                            </div>

                            <div class="form-group">
                                <Label>State</Label>
                                <input type="text" id="state" value="${User.state}" placeholder="${User.state}" disabled>
                            </div>

                        </form>
                    </div>

                    <div class="form-box Bio-form-box">

                        <div class="top">
                            <p class="form-title" >Bio</p>

                            <p id="bio-response-message" >Message from database</p>

                            <div class="edit-info" id="edit-bio" onclick="editBio()" >
                                <img src="../../pub/images/edit.png" alt="">
                                Edit
                            </div>


                            <div class="save-info" id="save-bio" onclick="updateBio()" >
                                <img src="../../pub/images/save.png" alt="">
                                Save
                            </div>
                        </div>


                        <form action="" class="editable-form Bio-form ">
                            <div class="form-group">
                                <textarea name="" id="bio" maxlength="300" disabled rows="4" aria-disabled="true"  placeholder="${User.description}">${User.description}</textarea >

                            </div>

                        </form>
                    </div>

                    <div class="form-box Activities-form-box">

                        <div class="top">
                            <p class="form-title">Activities</p>
                        </div>


                        <form action="" class="editable-form Activities-form ">
                            <div id="activities" class="activities">
                                <c:forEach var="activity" items="${userActivities}">
                                    <div id="activity" class="activity">
                                        <p class="value" style="display: none" >${activity.id}</p>
                                        <p class="activity-name">${activity.name}</p>
                                        <div id="activity-button"  onclick="removeActivity(${activity.id}, this)" class="activity-button" >x</div>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="form-group activity-form-group" >

                                <form  action="">


                                    <label >Add Activity</label>
                                        <div style="display: flex; gap: 2rem; align-items: center">
                                            <select name="add" id="activity-select" class="activity_select">

                                            <c:forEach var="activity" items="${activities}">
                                                <option value="${activity.id}">${activity.name}</option>
                                            </c:forEach>
                                            </select>

                                            <div onclick="addActivity()" id="add-sport" class="add-sport" >Add</div>
                                            <p id="response-message"></p>
                                         </div>


                                </form>

                            </div>

                        </form>
                    </div>

                </div>


            </div>
        </div>
</div>
<script src="../../pub/js/account.js"></script>
</body>
</html>
