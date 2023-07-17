<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
    <h1>Quản lý toà nhà</h1>
    <div class="col-md-6">
        <form action="" method="post">
            <div class="form-group">
                <label for="name">Tên toà nhà</label>
                <input required class="form-control" type="text" id="name" name="name" placeholder="Nhập tên toà nhà">
            </div>
            <button class="btn text-center mt-2" style="width: 100%; background-color: #0e63ae; color: white;">Thêm mới
            </button>
        </form>
    </div>
    <div class="col-md-6">
        <table class="table" id="mytable">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col" class="text-center">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${buildings}">
                <tr>
                    <th scope="row">${item.id}</th>
                    <td>${item.name}</td>
                    <th>
                        <div class="row">
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/admin/delete-building?id=${item.id}">
                                    <button class="btn btn-danger">Delete</button>
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/admin/update-building?id=${item.id}">
                                    <button class="btn btn-info">Update</button>
                                </a>
                            </div>
                        </div>
                    </th>
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