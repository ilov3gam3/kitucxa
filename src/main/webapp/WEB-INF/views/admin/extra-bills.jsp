<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
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
    <div class="row">
        <div class="col-md-6">
            <form action="">
            <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="year">Nhập năm</label>
                            <input type="number" id="year" name="year" class="form-control" value="${year}">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="semester">Nhập kì</label>
                            <select class="form-control" name="semester" id="semester">
                                <option ${semester == "spring" ? "selected" : ""} value="spring">spring</option>
                                <option ${semester == "summer" ? "selected" : ""} value="summer">summer</option>
                                <option ${semester == "fall" ? "selected" : ""} value="fall">fall</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="semester"></label>
                            <button type="submit" class="btn btn-success" style="width: 100%">Xem</button>
                        </div>
                    </div>
            </div>
            </form>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label for="semester"></label>
                <h4>Kì hiện tại ${current} bắt đầu từ ngày ${currents[0]} đến ngày ${currents[0]}</h4>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <h3>Hoá đơn điện, nước kì ${semester} từ ${semesters[0]} đến ${semesters[1]}</h3>
        <table class="table table-bordered table-striped" id="mytable">
            <thead>
            <tr>
                <th scope="col">Tên phòng</th>
                <th scope="col">Số điện</th>
                <th scope="col">Giá điện</th>
                <th scope="col">Số khối</th>
                <th scope="col">Giá nước</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${rooms}">
                <tr>
                    <form action="" method="post">
                        <c:if test="${item.getId() == 0}">
                            <input type="hidden" name="room_id" value="${item.getRoom_id()}">
                            <input type="hidden" name="start" value="${semesters[0]}">
                            <input type="hidden" name="end" value="${semesters[1]}">
                            <input type="hidden" name="method" value="create">
                        </c:if>
                        <c:if test="${item.getId() != 0}">
                            <input type="hidden" name="extra_bill_id" value="${item.getId()}">
                            <input type="hidden" name="method" value="update">
                        </c:if>
                    <th scope="row">${item.getRoom_name()} <a href="${pageContext.request.contextPath}/admin/view-bills-in-extra?room_id${item.getRoom_id()}&start=${semesters[0]}&end=${semesters[1]}">Chi tiết</a></th>
                    <th scope="row">
                        <input type="text" class="form-control" name="electricity" value="${item.getElectricity()}" >
                    </th>
                    <th scope="row">
                        <input type="text" class="form-control" name="electricity_price" value="${item.getElectricity_price()}" >
                    </th>
                    <th scope="row">
                        <input type="text" class="form-control" name="water" value="${item.getWater()}" >
                    </th>
                    <th scope="row">
                        <input type="text" class="form-control" name="water_price" value="${item.getWater_price()}" >
                    </th>
                    <th>
                        <select class="form-control" name="status" id="">
                            <option value="true" ${item.isStatus() ? "selected" : ""}>Đã thanh toán</option>
                            <option value="false" ${!item.isStatus() ? "selected" : ""}>Chưa thanh toán</option>
                        </select>
                    </th>
                    <th>
                        <div class="row m-1">
                            <div class="col-md-12">
                                <a href="${pageContext.request.contextPath}/admin/send-mail-extra-bill?extra_bill_id=${item.getId()}" ${item.getId() == 0 ? "hidden" : ""}>
                                    <button type="button" class="btn btn-success" style="width: 100%;">Gửi thông báo</button>
                                </a>
                            </div>
                        </div>
                        <div class="row m-1">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-warning" style="width: 100%;">Cập nhật</button>
                            </div>
                        </div>
                    </th>
                    </form>
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