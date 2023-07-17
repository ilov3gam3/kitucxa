<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Thông tin sinh viên</h1>
  <div class="col-md-6">
      <form action="" method="post">
        <input type="hidden" name="id" value="${user1.getId()}">
        <div class="form-group m-2">
          <label for="student_code">Mã sinh viên</label>
          <input class="form-control" type="text" id="student_code" name="student_code" value="${user1.getStudent_code()}">
        </div>
        <div class="form-group m-2">
          <label for="name">Tên</label>
          <input class="form-control" type="text" id="name" name="name" value="${user1.getName()}">
        </div>
        <div class="form-group m-2">
          <label for="email">Email</label>
          <input class="form-control" type="email" id="email" name="email" value="${user1.getEmail()}">
        </div>
        <div class="form-group m-2">
          <label for="email">Address</label>
          <input class="form-control" type="text" id="address" name="address" value="${user1.getAddress()}">
        </div>
        <div class="form-group m-2">
          <label for="email">Phone</label>
          <input class="form-control" type="tel" id="tel" name="tel" value="${user1.getTel()}">
        </div>
        <div class="form-group m-2">
          <label for="password">Password</label>
          <input class="form-control" type="password" id="password" name="new_password">
        </div>
        <div class="form-group m-2">
          <label for="verify">Tài khoản đã xác minh</label>
          <select class="form-control" name="verify" id="verify">
            <option value="true" ${user1.isVerified() ? "selected"  : ""}>Rồi</option>
            <option value="false"${!user1.isVerified() ? "selected"  : ""}>Chưa</option>
          </select>
        </div>
        <div class="form-group m-2">
          <label for="admin">Là admin</label>
          <select class="form-control" name="admin" id="admin">
            <option value="true" ${user1.isAdmin() ? "selected"  : ""}>Có</option>
            <option value="false"${!user1.isAdmin() ? "selected"  : ""}>Không</option>
          </select>
        </div>
        <div class="d-grid gap-2">
          <button class="btn" type="submit" style="background-color: #0e63ae; color: white">Cập nhật</button>
        </div>
      </form>
  </div>
  <div class="col-md-6">
    <div class="form-group m-2 text-center">
      <h4>Ảnh đại diện</h4>
      <img src="${pageContext.request.contextPath}/${user1.getAvatar()}" alt="" style="max-width: 75%; ">
    </div>
  </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  let table = new DataTable('#mytable');
</script>