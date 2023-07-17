<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>

<div class="row">
  <div class="col-md-3">
    <h1>Quản lý thông báo</h1>
    <form action="" method="post">
      <div class="form-group">
        <label for="name">Tên của thông báo</label>
        <input class="form-control" required type="text" id="name" name="name">
      </div>
      <div class="form-group">
        <label for="content">Nội dung của thông báo</label>
        <textarea rows="10" class="form-control" required id="content" name="content"></textarea>
      </div>
      <div class="form-group">
        <label for="start">Bắt đầu</label>
        <input required class="form-control" type="datetime-local" name="start" id="start">
      </div>
      <div class="form-group">
        <label for="end">Kết thúc</label>
        <input required class="form-control" type="datetime-local" name="end" id="end">
      </div>
      <div class="form-group mt-1">
        <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn">Tạo mới</button>
      </div>
    </form>
  </div>
  <div class="col-md-9">
    <table class="table table-bordered table-striped" id="mytable">
      <thead>
      <tr>
        <th class="col-md-1" scope="col">#</th>
        <th class="col-md-2">Tên thông báo</th>
        <th class="col-md-4">nội dung thông báo</th>
        <th class="col-md-1">Bắt đầu</th>
        <th class="col-md-1">Kết thúc</th>
        <th class="col-md-1">Ngày tạo</th>
        <th class="col-md-2">Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${notifications}">
        <tr>
          <td class="col-md-1">${item.getId()}</td>
          <td class="col-md-2">${item.getName()}</td>
          <td class="col-md-4 big-cell">${item.getContent()}</td>
          <td class="col-md-1">${item.getStart()}</td>
          <td class="col-md-1">${item.getEnd()}</td>
          <td class="col-md-1">${item.getCreated_at()}</td>
          <td class="col-md-2">
            <div class="row">
              <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/admin/delete-notification?id=${item.getId()}">
                  <button style="width: 100%;" class="btn btn-danger">Xoá</button>
                </a>
              </div>
              <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/admin/update-notification?id=${item.getId()}">
                  <button style="width: 100%;" class="btn btn-warning">Sửa</button>
                </a>
              </div>
            </div>
          </td>
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