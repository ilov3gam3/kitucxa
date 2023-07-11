<%@ page import="Model.Semester" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row" id="app">
  <div class="col-md-4">
    <h1>Thêm phòng</h1>
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
    <form action="" method="post">
      <input type="hidden" name="method" value="add">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label for="price">Giá</label>
            <input required class="form-control" type="number" id="price" name="price" placeholder="Nhập giá">
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label for="is_available">is available</label>
            <select class="form-control" name="is_available" id="is_available">
              <option value="true">có</option>
              <option value="false">không</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label for="names">Chọn toà nhà:</label>
            <select class="form-control" id="names" onchange="updateBuildingNames(this.value)">
              <option value="">Chọn toà nhà</option>
            </select>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label for="buildingNames">Chọn tầng:</label>
            <select name="floor_id" class="form-control" id="buildingNames">
              <option value="">Chọn tầng</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
          <button class="btn text-center mt-4" style="width: 100%; background-color: #0e63ae; color: white;">Thêm mới
          </button>
      </div>
    </form>
    <h1>Xem số lượng phòng</h1>
    <form action="" method="post">
      <input type="hidden" name="method" value="semester">
      <div class="form-group">
        <label for="year-input">Enter a year:</label>
        <input class="form-control" type="number" id="year-input" name="year" max="2100" value="${year}" required>
      </div>
      <div class="form-group">
        <label for="semester">Enter a semester:</label>
        <select class="form-control" name="semester" id="semester">
          <option value="spring" ${semester == "spring" ? "selected" : ""}>spring</option>
          <option value="summer" ${semester == "summer" ? "selected" : ""}>summer</option>
          <option value="fall" ${semester == "fall" ? "selected" : ""}>fall</option>
        </select>
      </div>
      <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn mt-1">Xem</button>
    </form>
  </div>
  <div class="col-md-8">
    <h3>Bạn đang xem phòng của kì ${semester} năm ${year}</h3><br>
    <h3>Từ <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_start"))%> đến <%= Semester.getDate((int)request.getAttribute("year"), Semester.valueOf((request.getAttribute("semester")) + "_end"))%></h3>
    <table class="table table-bordered table-striped" id="mytable" data-page-length="15">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th>tên phòng</th>
        <th>khả dụng</th>
        <th>giá</th>
        <th>số lượng</th>
        <th class="text-center">action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${rooms}">
        <tr ${!item.isIs_available() ? "class='table-danger'" : ""}>
          <td>${item.getId()}</td>
          <td>${item.getName()} <a href="${pageContext.request.contextPath}/admin/view-members-in-rooms?room_id=${item.getId()}&year=${year}&semester=${semester}">Xem thành viên</a></td>
          <td>${item.isIs_available() ? "có" : "không"}</td>
          <td>${item.getPrice()}</td>
          <td>${item.getNumber()}/4</td>
          <td>
            <div class="row">
              <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/admin/delete-room?id=${item.getId()}">
                  <button style="width: 100%;" class="btn btn-danger">Delete</button>
                </a>
              </div>
              <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/admin/update-room?id=${item.getId()}">
                  <button style="width: 100%;" class="btn btn-info">Update</button>
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
<%@ include file="../../include/foot.jsp" %>
<script>
  let table = new DataTable('#mytable');
  let buildings = ${buildings_json};
  let floors = ${floors_json};
  window.onload = function () {
    console.log(buildings)
    console.log(floors)
    var namesSelect = document.getElementById("names");
    for (var i = 0; i < buildings.length; i++) {
      var option = document.createElement("option");
      option.text = buildings[i].name;
      option.value = buildings[i].name;
      namesSelect.appendChild(option);
    }
  }
  function updateBuildingNames(selectedName) {
    var buildingSelect = document.getElementById("buildingNames");
    buildingSelect.innerHTML = '<option value="">Select Building</option>';

    if (selectedName) {
      for (var i = 0; i < floors.length; i++) {
        if (floors[i].building_name === selectedName) {
          var option = document.createElement("option");
          option.text = floors[i].name;
          option.value = floors[i].id;
          buildingSelect.appendChild(option);
        }
      }
    }
  }
</script>