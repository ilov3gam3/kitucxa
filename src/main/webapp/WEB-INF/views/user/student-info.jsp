<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <h1>Thông tin sinh viên</h1>
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
    </form>
  </form>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  let table = new DataTable('#mytable');
</script>