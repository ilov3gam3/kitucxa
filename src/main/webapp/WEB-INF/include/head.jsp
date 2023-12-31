<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="Model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Notification" %>
<%@ page import="Dao.NotificationDao" %>
<%@ page import="Model.Data" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>FPT dormitory</title>
    <link rel="shortcut icon" href="https://lmsdn.fpt.edu.vn/theme/image.php/boost/theme/1657934026/favicon"/>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/assets/css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/css/toastr.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/assets/css/custom.css" rel="stylesheet"/>

</head>
    <% User user = (User) session.getAttribute("user"); %>
    <% request.setAttribute("user", user); %>
    <% String uri = request.getRequestURI(); %>
<body class="sb-nav-fixed"  >
<nav class="sb-topnav navbar navbar-expand navbar-light">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="${pageContext.request.contextPath}/">
        <img style="width: 70%;"
             src="https://lmsdn.fpt.edu.vn/pluginfile.php/1/core_admin/logo/0x200/1657934026/2021-FPTU-Eng.png"
             alt="">
    </a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i
            class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
        <!-- <div class="input-group">
            <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
            <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
        </div> -->
    </form>
    <% if (user == null) {%>
    <div class="m-1">
        <a href="${pageContext.request.contextPath}/login">
            <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn btn-info">Login</button>
        </a>
    </div>
    <div class="m-1">
        <a href="${pageContext.request.contextPath}/register">
            <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn btn-info">Register</button>
        </a></a>
    </div>
    <%} else {%>
    <div class="sb-sidenav-footer bell-icon dropstart" style="margin-right: 30px;">
        <% Data data = new NotificationDao().getCurrentNotifications();%>
        <% ArrayList<Notification> current = data.currentNotifications;
            request.setAttribute("current", current);
        %>
        <% ArrayList<Notification> ended = data.top5Notification; %>
        <button type="button" style="background-color: transparent; border: none;" id="dropdownMenuButton1"
                data-bs-toggle="dropdown" aria-expanded="false">
            <i class="fa-regular fa-bell fa-xl"></i>
            <% if (current.size() > 0) {%>
                <span class="badge"><%=current.size()%></span>
            <% } %>
        </button>
        <% if (user.isAdmin()) { %>
        <ul class="dropdown-menu" style="width: 500px; padding: 0" aria-labelledby="dropdownMenuButton1">
            <div class="card">
                <% if (current.size() == 0) { %>
                <li><a class="dropdown-item"><p class="text-center text-warning font-weight-bold">Hiện tại không có
                    thông báo mới.</p></a></li>
                <% } else {%>
                <li><a class="dropdown-item"><p class="text-center text-warning font-weight-bold">Thông báo mới.</p></a>
                </li>
                <% for (int i = 0; i < current.size(); i++) {%>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/admin/update-notification?id=<%= current.get(i).getId()%>">
                    <div class="alert alert-warning" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0"><%= current.get(i).getName()%>
                        </p>
                        <p class="text-wrap" style="margin: 0"><%= current.get(i).getContent()%>
                        </p>
                    </div>
                </a></li>
                <%}%>
                <% } %>
            </div>
            <div class="card">
                <li><a class="dropdown-item"><p class="text-center text-info font-weight-bold">Thông báo cũ hơn.</p></a>
                </li>
                <% for (int i = 0; i < ended.size(); i++) {%>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/admin/update-notification?id=<%= ended.get(i).getId()%>">
                    <div class="" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0"><%= ended.get(i).getName()%>
                        </p>
                        <p style="margin: 0"><%= ended.get(i).getContent()%>
                        </p>
                    </div>
                </a></li>
                <%}%>
            </div>
            <div class="card" >
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/manage-notification"><p
                        class="text-center text-warning font-weight-bold">Xem tất cả thông báo.</p></a></li>
            </div>
        </ul>
        <% } else {%>
        <ul class="dropdown-menu" style="width: 500px" aria-labelledby="dropdownMenuButton1">
            <div class="card">
                <% if (current.size() == 0) { %>
                <li><a class="dropdown-item"><p class="text-center text-warning font-weight-bold">Hiện tại không có
                    thông báo mới.</p></a></li>
                <% } else {%>
                <li><a class="dropdown-item"><p class="text-center text-warning font-weight-bold">Thông báo mới.</p></a>
                </li>
                <% for (int i = 0; i < current.size(); i++) {%>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/user/view-notification-detail?id=<%= current.get(i).getId()%>">
                    <div class="alert alert-warning" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0"><%= current.get(i).getName()%>
                        </p>
                        <p class="text-wrap" style="margin: 0"><%= current.get(i).getContent()%>
                        </p>
                    </div>
                </a></li>
                <%}%>
                <% } %>
            </div>
            <div class="card">
                <li><a class="dropdown-item"><p class="text-center text-info font-weight-bold">Thông báo cũ hơn.</p></a>
                </li>
                <% for (int i = 0; i < ended.size(); i++) {%>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/user/view-notification-detail?id=<%= ended.get(i).getId()%>">
                    <div class="" style="padding: 3px; margin: 0">
                        <p style="font-weight: bold; margin: 0"><%= ended.get(i).getName()%>
                        </p>
                        <p style="margin: 0"><%= ended.get(i).getContent()%>
                        </p>
                    </div>
                </a></li>
                <%}%>
            </div>
            <div class="card">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/view-all-notification"><p
                        class="text-center text-warning font-weight-bold">Xem tất cả thông báo.</p></a></li>
            </div>
        </ul>
        <% } %>
    </div>


    <div class="sb-sidenav-footer" style="margin-left: 30px;">
        <% if (user.isAdmin()) { %>
        <div class="small">Logged in as admin:</div>
        <% } else {%>
        <div class="small">Logged in as user:</div>
        <% } %>
        <%= user.name%>
    </div>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4" style="margin-right: 20px;">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false"><img
                    style="width: 40px;height: 40px; object-fit: cover; border-radius: 50%;"
                    src="${pageContext.request.contextPath}/<%= user.avatar%>" alt=""></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile">Profile</a>
                </li>
                <li>
                    <hr class="dropdown-divider"/>
                </li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </li>
    </ul>
    <%}%>

</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-light" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav ">
                    <% if (user != null) {%>
                        <% if (user.isAdmin()) {%>
                            <div class="sb-sidenav-menu-heading">Admin</div>
                            <a class="nav-link <%= uri.endsWith("manage-buildings.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-buildings">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-building"></i></div>
                                Quản lý toà nhà
                            </a>
                            <a class="nav-link <%= uri.endsWith("manage-floors.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-floors">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-stairs"></i></div>
                                Quản lý tầng
                            </a>
                            <a class="nav-link <%= uri.endsWith("manage-rooms.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-rooms">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-door-closed"></i></div>
                                Quản lý phòng
                            </a>
                            <a class="nav-link <%= uri.endsWith("/admin/bills.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-bills">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-money-bill"></i></div>
                                Quản lý hoá đơn
                            </a>
                            <a class="nav-link <%= uri.endsWith("manage-users.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-users">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-user"></i></div>
                                Quản lý người dùng
                            </a>
                            <a class="nav-link <%= uri.endsWith("manage-notification.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-notification">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-bell"></i></div>
                                Quản lý thông báo
                            </a>
                            <a class="nav-link <%= uri.endsWith("admin/manage-cancels.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-cancels">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-ban"></i></div>
                                Quản lý huỷ phòng
                            </a>
                            <a class="nav-link <%= uri.endsWith("manage-changes.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-change-rooms">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-arrow-right"></i></div>
                                Quản lý chuyển phòng.
                            </a>
                            <a class="nav-link <%= uri.endsWith("extra-bills.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/admin/manage-extra-bills">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-bolt"></i></div>
                                Tiền điện, nước
                            </a>
                            <a class="nav-link <%= uri.endsWith("chat.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/chat">
                                <div class="sb-nav-link-icon"><i class="fa-regular fa-message"></i></div>
                                Chat
                            </a>
                        <%} %>

                            <div class="sb-sidenav-menu-heading">User</div>
                            <a class="nav-link <%= uri.endsWith("chat.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/chat">
                                <div class="sb-nav-link-icon"><i class="fa-regular fa-message"></i></div>
                                Chat
                            </a>
                            <a class="nav-link <%= uri.endsWith("book-room.jsp") || uri.endsWith("book-room-detail.jsp") || uri.endsWith("change-room.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/book-rooms">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-door-open"></i></div>
                                Đặt phòng
                            </a>
                            <a class="nav-link <%= uri.endsWith("/user/bills.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/bills">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-money-bill"></i></div>
                                Hoá đơn của bạn
                            </a>
                            <a class="nav-link <%= uri.endsWith("user/cancels.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/view-cancels">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-ban"></i></div>
                                Yêu cầu huỷ phòng
                            </a>
                            <a class="nav-link <%= uri.endsWith("view-changes.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/view-changes">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-arrow-right"></i></div>
                                yêu cầu chuyển phòng
                            </a>
                            <a class="nav-link <%= uri.endsWith("payment.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/payment">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-money-bill"></i></div>
                                Thanh toán
                            </a>
                            <a class="nav-link <%= uri.endsWith("rules.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/rules">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-scale-balanced"></i></div>
                                Quy định
                            </a>
                            <a class="nav-link <%= uri.endsWith("view-admins.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/user/admins">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-users"></i></div>
                                Danh sách quản trị viên
                            </a>
                        <%}%>
                </div>
            </div>

        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
