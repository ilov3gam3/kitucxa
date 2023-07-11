<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <div class="container">
        <h1>Quản lý thông báo</h1>
        <form action="" method="post">
            <div class="form-group">
                <label for="name">Tên của thông báo</label>
                <input class="form-control" required type="text" id="name" name="name" value="${notification.getName()}" >
            </div>
            <div class="form-group">
                <label for="content">Nội dung của thông báo</label>
                <textarea rows="10" class="form-control" required id="content" name="content" >${notification.getContent()}</textarea>
            </div>
            <div class="form-group">
                <label for="start">Bắt đầu</label>
                <input required class="form-control" type="datetime-local" name="start" id="start" value="${notification.getStart()}">
            </div>
            <div class="form-group">
                <label for="end">Kết thúc</label>
                <input required class="form-control" type="datetime-local" name="end" id="end" value="${notification.getEnd()}" >
            </div>
            <div class="form-group mt-1">
                <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn">Cập nhật</button>
            </div>
        </form>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
