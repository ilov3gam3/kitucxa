<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/head.jsp" %>
<div class="text-center">
    <h1>Welcome to FPT Dormitory Management System</h1>
    Thanks for visiting. Make yourself at home. We hope you like it as much as we do.<br><br>
    <% ArrayList<Notification> current = (ArrayList<Notification>) request.getAttribute("current"); %>
    <% if (current != null) {%>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <% for (int i = 0; i < current.size(); i++) {%>
            <% if (i == 0) { %>
            <div class="carousel-item active">
                <a href="${pageContext.request.contextPath}/<%= user!=null && user.isAdmin() ? "admin/update-notification?id="+ current.get(i).id : "/user/view-notification-detail?id=" + current.get(i).id  %>">
                    <div class="alert alert-warning mb-1" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0">
                            <%= current.get(i).getName()%>
                        </p>
                        <p class="text-wrap" style="margin: 0">
                            <%= current.get(i).getContent()%>
                        </p>
                    </div>
                </a>
            </div>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/<%= user!=null && user.isAdmin() ? "admin/update-notification?id="+ current.get(i).id : "/user/view-notification-detail?id=" + current.get(i).id  %>">
                <div class="carousel-item">
                    <div class="alert alert-warning mb-1" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0">
                            <%= current.get(i).getName()%>
                        </p>
                        <p class="text-wrap" style="margin: 0">
                            <%= current.get(i).getContent()%>
                        </p>
                    </div>
                </div>
            </a>
            <% } %>
            <% } %>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <% } %>

    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner" style="height: <%= current != null ? "75vh" : "82vh" %>;">
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/assets/images/HCM-scaled.jpeg"
                     style="width: 100%; height: 100%; object-fit: cover;" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/assets/images/db4c1032-1585103324053396888676.webp"
                     style="width: 100%; height: 100%; object-fit: cover;" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/assets/images/base64-16288251235401909942603.png"
                     style="width: 100%; height: 100%; object-fit: cover;" class="d-block w-100" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>
<%@ include file="../include/foot.jsp" %>