<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
  <h1>Quản lý tầng</h1>
    <div class="col-md-6">
        <form action="" method="post">
            <div class="form-group">
                <label for="building_id">Toà nhà</label>
                <select class="form-control" name="building_id" id="building_id">
                    <c:forEach var="item" items="${buildings}">
                    <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        <button class="btn text-center mt-3" style="width: 100%; background-color: #0e63ae; color: white;">Thêm mới 1 tầng
            </button>
        </form>
    </div>
    <div class="col-md-6" >
        <table class="table table-bordered table-striped" id="mytable">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th>floor</th>
                <th>name</th>
                <th>building name</th>
                <th>action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${floors}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.floor}</td>
                    <td>${item.name}</td>
                    <td>${item.building_name}</td>
                    <td>
                        <div class="row">
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/admin/delete-floor?id=${item.id}">
                                    <button class="btn btn-danger">Delete</button>
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/admin/update-floor?id=${item.id}">
                                    <button class="btn btn-info">Update</button>
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
</script>