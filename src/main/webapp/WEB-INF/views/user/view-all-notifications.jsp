<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Tất cả thông báo</h1>
  <div class="container">
    <table class="table table-bordered table-striped" id="mytable">
      <thead>
      <tr>
        <th class="col-md-1" scope="col">#</th>
        <th class="col-md-2">Tên thông báo</th>
        <th class="col-md-4" style="width: 500px;">nội dung thông báo</th>
        <th class="col-md-1">Bắt đầu</th>
        <th class="col-md-1">Kết thúc</th>
        <th class="col-md-1">Ngày tạo</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${notifications}">
        <tr>
          <td class="col-md-1">${item.getId()} (<a href="${pageContext.request.contextPath}/user/view-notification-detail?id=${item.getId()}">chi tiết</a>)</td>
          <td class="col-md-2">${item.getName()}</td>
          <td class="col-md-4 big-cell">
                ${item.getContent()}
          </td>
          <td class="col-md-2">${item.getStart()}</td>
          <td class="col-md-2">${item.getEnd()}</td>
          <td class="col-md-1">${item.getCreated_at()}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  $('#mytable').dataTable( {
    "order": [],
  } );
</script>