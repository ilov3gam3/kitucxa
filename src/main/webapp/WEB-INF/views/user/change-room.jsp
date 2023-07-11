<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<h3>Đơn chuyển phòng</h3>
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
    <div class="col-md-4">
        <form action="" method="post">
            <div class="form-group">
                <label for="reason">Nhập lý do</label>
                <textarea required name="reason" id="reason" class="form-control" rows="5"></textarea>
            </div>
            <div class="form-group">
                <h4>từ phòng ${bill.getRoom_name()} kì ${bill.getSemester()} <span hidden id="room_name"></span></h4>
                <input type="hidden" id="to_room" name="to_room">
            </div>
            <button class="btn mt-2" style="width: 100%; background-color: #0e63ae; color: white;" >Gửi đơn</button>
        </form>
    </div>
    <div class="col-md-8">
        <h2>Bạn đang xem danh sách phòng của kì ${bill.getSemester()}</h2>
        <table class="table table-bordered table-striped" id="mytable" >
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
            <c:if test="${not empty rooms}">
                <c:forEach var="item" items="${rooms}">
                    <tr ${!item.isIs_available() ? "class='table-danger'" : ""}>
                        <td>${item.getId()}</td>
                        <td>${item.getName()}</td>
                        <td>${item.isIs_available() ? "có" : "không"}</td>
                        <td>${item.getPrice()}</td>
                        <td>${item.getNumber()}/4</td>
                        <td>
                            <div class="row">
                                <c:if test="${item.getId() == bill.getRoom_id()}">
                                    <button type="button" disabled class="btn btn-warning" ${!item.isIs_available() ? "disabled" : ""} style="width: 100%; color: white;">Phòng hiện tại</button>
                                </c:if>
                                <c:if test="${item.getId() != bill.getRoom_id()}">
                                    <button type="button" onclick="changeRoom(${item.getId()}, '${item.getName()}')" ${item.getNumber() == 4 ? "hidden" : ""}  class="btn" ${!item.isIs_available() ? "hidden" : ""} style="width: 100%; background-color: #0e63ae; color: white;">Chọn phòng</button>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    $('#mytable').dataTable( {
        "order": [],
    } );
    function changeRoom(room_id, room_name) {
        console.log(room_id)
        console.log(room_name)
        const myInput = document.getElementById("to_room");
        myInput.value = room_id
        const view_room_name = document.getElementById("room_name");
        view_room_name.innerText = " sang phòng " + room_name
        view_room_name.hidden = false;
    }
</script>