<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
  <div class="col-md-6">
    <h1>Cập nhật phòng</h1>
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
    <c:if test="${not empty warning}">
      <div class="alert alert-success">
          ${warning}
      </div>
    </c:if>
    <form action="" method="post">
      <div class="form-group">
        <label for="id">ID</label>
        <input class="form-control" type="text" id="id" value="${room.getId()}" name="id" disabled>
      </div>
      <div class="form-group">
        <label for="name">Tên</label>
        <input class="form-control" type="text" id="name" value="${room.getName()}" name="name" disabled>
      </div>
      <div class="form-group">
        <label for="is_available">Khả dụng</label>
        <select class="form-control" name="is_available" id="is_available">
          <option value="true" ${room.isIs_available() ? "selected" : ""}>có</option>
          <option value="false" ${!room.isIs_available() ? "selected" : ""}>không</option>
        </select>
      </div>
      <div class="form-group">
        <label for="price">Giá (VND)</label>
        <input class="form-control" type="number" id="price" value="${room.price}" name="price" placeholder="Nhập giá">
      </div>
      <button class="btn text-center mt-2" style="width: 100%; background-color: #0e63ae; color: white;">Cập nhật
      </button>
    </form>
  </div>
</div>
<%@ include file="../../include/foot.jsp" %>
