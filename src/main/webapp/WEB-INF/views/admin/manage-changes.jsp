<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
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
  <table class="table table-bordered table-striped" id="mytable">
    <thead>
    <tr>
      <th scope="col">#</th>
      <th>Tên người chuyển</th>
      <th>Từ phòng</th>
      <th>Đến phòng</th>
      <th>Lý do</th>
      <th>Kì</th>
      <th>Trạng thái</th>
      <th>Tạo lúc</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${changes}">
      <tr>
        <td>${item.getId()}</td>
        <td>${item.getUsername()}</td>
        <td>${item.getRoom_from_name()}</td>
        <td>${item.getRoom_to_name()}</td>
        <td>${item.getReason()}</td>
        <td>${item.getSemester()}</td>
        <td>${item.getStatus().getValue() == 1 ? "<p class='text-success'>đồng ý</p>" :  (item.getStatus().getValue() == 0 ? "<p class='text-info'>chưa xác nhận</p>" : "<p class='text-danger'>không đồng ý</p>")}</td>
        <td>${item.getCreated_at()}</td>
        <th>
          <c:if test="${item.getStatus().getValue() == 0}">
            <button data-bs-toggle="modal" data-bs-target="#modal${item.getId()}" class="btn btn-success">Đổi trạng thái</button>
          </c:if>
          <c:if test="${item.getStatus().getValue() != 0}">
          </c:if>
        </th>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<c:forEach var="item" items="${changes}">
  <div class="modal fade" id="modal${item.getId()}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="" method="post">
          <input type="hidden" name="bill_id" value="${item.getBill_id()}">
          <input type="hidden" name="change_id" value="${item.getId()}">
          <input type="hidden" name="start" value="${item.getStart()}">
          <input type="hidden" name="end" value="${item.getEnd()}">
          <input type="hidden" name="room_to_id" value="${item.getRoom_to_id()}">
          <input type="hidden" name="user_id" value="${item.getUser_id()}">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">${item.getUsername()} Đổi từ phòng ${item.getRoom_from_name()} sang phòng ${item.getRoom_to_name()} trong kì ${item.getSemester()}</h5><br>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <a href="${pageContext.request.contextPath}/admin/manage-bills?bill_id=${item.getBill_id()}"><h6>Xem hoá đơn</h6></a>
            <input type="text" hidden="hidden" name="cancel_id" value="${item.getId()}">
            <div class="form-group">
              <label for="reason">Trạng thái</label>
              <textarea class="form-control" name="reason" id="reason" rows="10" readonly >${item.getReason()}</textarea>
            </div>
            <div class="form-group">
              <label for="status">Trạng thái</label>
              <select class="form-control" name="status" id="status">
                <option ${item.getStatus().getValue() == 1 ? "disabled selected" : ""} value="1">Đồng ý</option>
                <option ${item.getStatus().getValue() == 0 ? "disabled selected" : ""} value="0">Chờ xác nhận</option>
                <option ${item.getStatus().getValue() == -1 ? "disabled selected" : ""}  value="-1">Không đồng ý</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</c:forEach>
<%@ include file="../../include/foot.jsp"%>
<script>
  $('#mytable').dataTable( {
    "order": [],
  } );
</script>