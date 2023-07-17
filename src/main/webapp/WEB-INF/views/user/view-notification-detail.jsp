<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Xem chi tiết thông báo</h1>
  <div class="container">
    <table class="table table-bordered table-striped" id="mytable">
      <thead>
      <tr>
        <th class="col-md-1" scope="col">#</th>
        <th class="col-md-1">Tên thông báo</th>
        <th class="col-md-4" style="width: 500px;">nội dung thông báo</th>
        <th class="col-md-1">Bắt đầu</th>
        <th class="col-md-1">Kết thúc</th>
        <th class="col-md-1">Ngày tạo</th>
      </tr>
      </thead>
        <tbody>
          <tr>
            <td class="col-md-1">${notification.getId()}</td>
            <td class="col-md-1">${notification.getName()}</td>
            <td class="col-md-4" style="word-break: break-all;">${notification.getContent()} </td>
            <td class="col-md-2">${notification.getStart()}</td>
            <td class="col-md-2">${notification.getEnd()}</td>
            <td class="col-md-2">${notification.getCreated_at()}</td>
          </tr>
        </tbody>
    </table>
  </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
</script>