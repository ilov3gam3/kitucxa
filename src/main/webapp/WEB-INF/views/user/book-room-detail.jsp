<%@ page import="java.util.Calendar" %>
<%@ page import="Model.Semester" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <h1>Đặt phòng</h1>
    <div class="col-md-6">
            <div class="form-group">
                <label for="room_name">Tên phòng</label>
                <input type="text" id="room_name" name="room_name" class="form-control" value="${room.getName()}" readonly>
            </div>
            <div class="form-group">
                <label for="price">Giá</label>
                <input type="text" id="price" name="price" class="form-control" value="${room.getPrice()}" readonly>
            </div>
            <div class="form-group">
                <label for="number">Số lượng</label>
                <input type="text" id="number" name="number" class="form-control" value="${room.getNumber()}/4" readonly>
            </div>
                <form action="${pageContext.request.contextPath}/user/book-rooms/detail">
                    <div class="row">
                        <input hidden="hidden" type="text" name="id" value="${room.getId()}">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="year-input">Enter a year:</label>
                                <input class="form-control" type="number" id="year-input" name="year" min="<%=Calendar.getInstance().get(Calendar.YEAR)%>" max="2100" value="${year}" required>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="semester">Enter a semester:</label>
                                <select class="form-control" name="semester" id="semester">
                                    <option value="spring" ${semester == "spring" ? "selected" : ""}>spring</option>
                                    <option value="summer" ${semester == "summer" ? "selected" : ""}>summer</option>
                                    <option value="fall" ${semester == "fall" ? "selected" : ""}>fall</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                                <div class="form-group">
                                    <label for="check">Kiểm tra:</label>
                                    <button class="btn" style="width: 100%; background-color: #0e63ae; color: white;" id="check">Kiểm tra</button>
                                </div>
                            </div>
                    </div>
                </form>
            <form action="" method="post">
                <input hidden="hidden" type="text" name="id" value="${room.getId()}">
                <input hidden="hidden" class="form-control" type="number" name="year" value="${year}">
                <input hidden="hidden" type="text" name="semester" value="${semester}">
                <button class="btn mt-1" style="width: 100%; background-color: #0e63ae; color: white;" ${room.getNumber() == 4 ? "disabled" : ""} >Đăng kí</button>
            </form>
    </div>
    <div class="col-md-6">
        <h3>Bạn đang đặt phòng ${room.getName()} của kì ${semester} năm ${year}</h3>
        <h3>Từ <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_start"))%> đến <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_end"))%></h3>
        <div class="alert alert-warning">
            Lưu ý, nếu muốn đặt phòng này nhưng ở kì khác, vui lòng chọn thời gian và kiểm tra số lượng người trong phòng trước khi đặt, nếu không bạn sẽ đặt phòng với kì đang hiển thị ở phía trên.
        </div>
        <c:forEach var="item" items="${bills}">
            <div class="bg-light p-5 rounded m-2" style="background-color: #E9ECEF">
                <p> <span class="fw-bold">Người đánh giá : </span> ${item.getUsername()} : ${item.getStudent_code()}</p>
                <p> <span class="fw-bold" >Nội dung đánh giá : </span> ${item.getEvaluation()}</p>
                <div class="rate">
                    <c:forEach var="i" begin="1" end="5">
                        <c:if test="${6-i <= item.getStars()}">
                            <input disabled type="radio" name="rate" value="${6-i}"/>
                            <label title="text" style="color: #ffc700">${6-i} star</label>
                        </c:if>
                        <c:if test="${6-i > item.getStars()}">
                            <input disabled type="radio" name="rate" value="${6-i}"/>
                            <label title="text">${6-i} star</label>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

    </div>
</div>
<%@ include file="../../include/foot.jsp"%>