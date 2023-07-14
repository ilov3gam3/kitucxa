<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/head.jsp" %>
<div class="text-center">
    <h1>Welcome to FPT Dormitory Management System</h1>
    Thanks for visiting. Make yourself at home. We hope you like it as much as we do.<br><br>
    <% ArrayList<Notification> current = (ArrayList<Notification>) request.getAttribute("current"); %>
    <div class="container">
        <% if (current != null) {%>
        <% for (int i = 0; i < current.size(); i++) {%>
        <div class="alert alert-warning" style="padding: 3px; margin: 0">
            <p style="font-weight: bold; margin: 0"><%= current.get(i).getName()%>
            </p>
            <p class="text-wrap" style="margin: 0"><%= current.get(i).getContent()%>
            </p>
        </div>
        <% } %>
        <% } %>
    </div>
    <img src="${pageContext.request.contextPath}/assets/images/KTX.png" alt="KTX" style="height: 50vh"/>
</div>
<%@ include file="../include/foot.jsp" %>