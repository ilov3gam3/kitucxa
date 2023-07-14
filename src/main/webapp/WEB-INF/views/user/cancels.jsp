<%@ page import="java.util.Calendar" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <h3>Danh sách huỷ phòng của bạn</h3>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
                ${error}
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">
                ${success}
        </div>
    </c:if>
    <table ${pageContext.request.getParameter("cancel_id") != null ? "hidden" : ""} class="table table-bordered table-striped" id="mytable">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th>Bill id</th>
            <th>Tên phòng</th>
            <th>Lý do</th>
            <th>Lý do Admin</th>
            <th>Kì</th>
            <th>Trạng thái</th>
            <th>Tạo lúc</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${cancels}">
            <tr>
                <td>${item.getId()}</td>
                <td>${item.getBill_id()} <a href="${pageContext.request.contextPath}/user/bills?bill_id=${item.getBill_id()}">Xem chi tiết</a></td>
                <td>${item.getRoom_name()}</td>
                <td>${item.getReason()}</td>
                <td>${item.getAdmin_reason()}</td>
                <td>${item.getSemester()}</td>
                <td>${item.getStatus().getValue() == 1 ? "<p class='text-success'>đồng ý</p>" :  (item.getStatus().getValue() == 0 ? "<p class='text-info'>chưa xác nhận</p>" : "<p class='text-danger'>không đồng ý</p>")}</td>
                <td>${item.getCreated_at()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    const table = $('#mytable').dataTable( {
        "order": [],
    } );
    const cancel_id = '${pageContext.request.getParameter("cancel_id")}';
    if (cancel_id !== ''){
        table.fnFilter(cancel_id, 0);
        $('#mytable').removeAttr('hidden');
    }
</script>