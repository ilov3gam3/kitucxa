<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
    <h1>Cập nhật toà nhà ${building.name}</h1>
    <div class="col-md-6">
        <form action="" method="post">
            <input type="text" hidden="hidden" value="${building.id}">
            <div class="form-group">
                <label for="name">Tên toà nhà</label>
                <input class="form-control m-2" type="text" id="name" value="${building.name}" name="name" placeholder="Nhập tên toà nhà">
            </div>
            <button class="btn text-center" style="width: 100%; background-color: #0e63ae; color: white;">Cập nhật
            </button>
        </form>
    </div>
</div>
<%@ include file="../../include/foot.jsp" %>
