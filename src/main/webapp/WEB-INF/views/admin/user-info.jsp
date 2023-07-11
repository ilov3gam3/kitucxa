<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Admin view user info</h1>
  <form action="">
    <form action="" method="post">
      <div class="form-group m-2">
        <label for="student_code">Mã sinh viên</label>
        <input class="form-control" type="text" id="student_code" name="student_code" disabled value="${user.getStudent_code()}" readonly>
      </div>
      <div class="form-group m-2">
        <label for="name">Tên</label>
        <input class="form-control" type="text" id="name" name="name" disabled value="${user.getName()}">
      </div>
      <div class="form-group m-2">
        <label for="email">Tên</label>
        <input class="form-control" type="email" id="email" name="email" disabled value="${user.getEmail()}">
      </div>
      <div class="form-group m-2">
        <h4>Ảnh đại diện</h4>
        <img src="${pageContext.request.contextPath}/${user.getAvatar()}" alt="" style="width: 50%">
      </div>
    </form>
  </form>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  let table = new DataTable('#mytable');
</script>