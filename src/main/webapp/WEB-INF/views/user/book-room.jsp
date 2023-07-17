<%@ page import="Model.Semester" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div>
    <h1>Đặt phòng</h1>
    <div class="row">
        <div class="col-md-3">
            <form action="" method="post">
                <div class="form-group">
                    <label for="year-input">Enter a year:</label>
                    <input class="form-control" type="number" id="year-input" name="year" min="${year}" max="2100" value="${year}" required>
                </div>
                <div class="form-group">
                    <label for="semester">Enter a semester:</label>
                    <select class="form-control" name="semester" id="semester">
                        <option value="spring" ${semester == "spring" ? "selected" : ""}>spring</option>
                        <option value="summer" ${semester == "summer" ? "selected" : ""}>summer</option>
                        <option value="fall" ${semester == "fall" ? "selected" : ""}>fall</option>
                    </select>
                </div>
                <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn mt-1">Xem</button>
            </form>
        </div>
        <div class="col-md-9">
            <h3>Bạn đang xem phòng của kì ${semester} năm ${year}</h3><br>
            <h3>Từ <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_start"))%> đến <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_end"))%></h3>
            <table class="table table-bordered table-striped" id="mytable" >
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th>tên phòng</th>
                    <th>khả dụng</th>
                    <th>giá</th>
                    <th>số lượng</th>
                    <th class="text-center">action</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty rooms}">
                    <c:forEach var="item" items="${rooms}">
                        <tr ${!item.isIs_available() ? "class='table-danger'" : ""}>
                            <td>${item.getId()}</td>
                            <td>${item.getName()}</td>
                            <td>${item.isIs_available() ? "có" : "không"}</td>
                            <td>${item.getPrice()}</td>
                            <td>${item.getNumber()}/4</td>
                            <td>
                                <div class="row">
                                    <div class="col-md-12">
                                        <form action="${pageContext.request.contextPath}/user/book-rooms/detail">
                                            <input type="text" name="id" value="${item.getId()}" hidden="hidden">
                                            <input type="text" name="semester" value="${semester}" hidden="hidden">
                                            <input type="text" name="year" value="${year}" hidden="hidden">
                                            <button ${item.getNumber() == 4 ? "disabled" : ""}  class="btn" ${!item.isIs_available() ? "disabled" : ""} style="width: 100%; background-color: #0e63ae; color: white;">Book</button>
                                        </form>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    let table = new DataTable('#mytable');
</script>