<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Tất cả người dùng</h1>
  <table class="table table-bordered table-striped" id="mytable">
    <thead>
    <tr>
      <th scope="col">#</th>
      <th>Mã</th>
      <th>Tên</th>
      <th>Email</th>
      <th>Địa chỉ</th>
      <th>Điện thoại</th>
      <th>Ngày sinh</th>
      <th>Ảnh đại diện</th>
      <th>Là admin</th>
      <th>Đã xác thực</th>
      <th>Chi tiết</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${users}">
      <tr>
        <td>${item.getId()}</td>
        <td>${item.getStudent_code()}</td>
        <td>${item.getName()}</td>
        <td>${item.getEmail()}</td>
        <td>${item.getAddress()}</td>
        <td>${item.getTel()}</td>
        <td>${item.getBirthday()}</td>
        <td><a href="${pageContext.request.contextPath}/${item.getAvatar()}" target="_blank"><img src="${pageContext.request.contextPath}/${item.getAvatar()}" alt="" style="width: 100px; height: 100px; object-fit: cover;"></a></td>
        <td>${item.isAdmin() ? "có" : "không"}</td>
        <td>${item.isVerified() ? "rồi" : "chưa"}</td>
        <td>
          <a href="${pageContext.request.contextPath}/admin/user-info?student_code=${item.getStudent_code()}">
            <button class="btn btn-success">Xem</button>
          </a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  let table = new DataTable('#mytable');
</script>