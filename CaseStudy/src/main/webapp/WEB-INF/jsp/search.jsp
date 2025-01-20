<%--
  Created by IntelliJ IDEA.
  User: jeffr
  Date: 1/7/2025
  Time: 10:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    String pageTitle = "Search";
    String customCss = "../../pub/css/search.css";

%>
<%@ include file="../include/navbar.jsp" %>

    <div class="container">
        <%@ include file="../include/sidebar.jsp" %>

        <div class="content">
            <div class=""></div>
            <div class="inner search">

            <form action="" class="search-bar-box">
                <button type="submit">
                    <img src="../../pub/images/search.png" alt="">
                </button>

                <input type="text" placeholder="Search Users ,Activities or Locations" maxlength="50" name="query" value="${query}">
            </form>

            <div class="search-results">
                <p class="title">Search results</p>
                <div class="search-result-cards">
                    <c:if test="${empty resultUsers and not empty param.query}">
                        <p style="font-size: 25px;font-weight: 200" >No results for ${param.query} </p>
                    </c:if>
                    <c:forEach var="result" items="${resultUsers}">
                        <a href="/user/${result.id}" class="search-result-card">
                            <img src="${result.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                            <p class="search-result-name">${result.firstName} ${result.lastName}</p>
                            <p class="search-result-location">${result.city}, ${result.state}</p>
                        </a>
                    </c:forEach>



                </div>
            </div>
        </div>
        </div>

    </div>
<script>

        $(document).ready(function() {
            $("#searchLink").addClass("active");
            $("#searchLink .dash-icon").addClass("active");
            $("#search-box").hide();
            $("#user-box").css({
                'margin-right': '40%',
        });

</script>

</body>
</html>
