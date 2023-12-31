<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <h3>Danh sách chuyển phòng của bạn</h3>
    <table ${pageContext.request.getParameter("change_id") != null ? "hidden" : ""} class="table table-bordered table-striped" id="mytable">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th>Từ phòng</th>
            <th>Đến phòng</th>
            <th>Lý do</th>
            <th>Lý do Admin</th>
            <th>Kì</th>
            <th>Trạng thái</th>
            <th>Tạo lúc</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${changes}">
            <tr>
                <td>${item.getId()}</td>
                <td>${item.getRoom_from_name()}</td>
                <td>${item.getRoom_to_name()}</td>
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
    const change_id = '${pageContext.request.getParameter("change_id")}';
    if (change_id !== ''){
        table.fnFilter(change_id, 0);
        $('#mytable').removeAttr('hidden');
    }
</script>