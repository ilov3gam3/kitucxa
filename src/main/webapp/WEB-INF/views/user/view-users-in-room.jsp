<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
  <table class="table table-bordered table-striped" id="mytable">
    <thead>
    <tr>
      <th>Tên</th>
      <th>Mã số sinh viên</th>
      <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${users}">
      <tr>
        <td>${item.getName()}</td>
        <td><a href="${pageContext.request.contextPath}/user/view-student-info?student_code=${item.getStudent_code()}">${item.getStudent_code()}</a></td>
        <td>${item.getEmail()}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>
<%@ include file="../../include/foot.jsp"%>
<script>
  let table = new DataTable('#mytable');
</script>