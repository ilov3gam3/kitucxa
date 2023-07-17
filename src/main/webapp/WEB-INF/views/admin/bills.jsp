<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <h1>Tất cả hoá đơn</h1>
    <table ${pageContext.request.getParameter("bill_id") != null ? "hidden" : ""} class="table table-bordered table-striped" id="mytable">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th>Tên phòng</th>
            <th>Mã sinh viên</th>
            <th>Giá</th>
            <th>Trạng thái</th>
            <th>Nhận xét</th>
            <th>Đánh giá</th>
            <th>Kì</th>
            <th>Tạo lúc</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${bills}">
            <tr>
                <td>
                    ${item.getId()}
                </td>
                <td>${item.getRoom_name()}</td>
                <td><a href="${pageContext.request.contextPath}/admin/user-info?student_code=${item.getStudent_code()}">${item.getStudent_code()}</a></td>
                <td>${item.getPrice()}</td>
                <td>
                    <div class="row">
                        <div class="col-md-6">
                            <c:set var="style" value=""/>
                            <c:if test="${item.getStatus().getValue() == 1}">
                                <c:set var="style" value="style='color: #44FF07'"/>
                            </c:if>
                            <c:if test="${item.getStatus().getValue() == -1}">
                                <c:set var="style" value="style='color: red'"/>
                            </c:if>
                            <c:if test="${item.getStatus().getValue() == 0}">
                                <c:set var="style" value="style='color: blue'"/>
                            </c:if>
                            <c:if test="${item.getStatus().getValue() == 2}">
                                <c:set var="style" value="style='color: red'"/>
                            </c:if>
                            <p ${style}>
                                <c:if test="${item.getStatus().getValue() == 1}">
                                    đã thanh toán
                                </c:if>
                                <c:if test="${item.getStatus().getValue() == -1}">
                                    đã bị huỷ
                                </c:if>
                                <c:if test="${item.getStatus().getValue() == 0}">
                                    chờ xác nhận
                                </c:if>
                                <c:if test="${item.getStatus().getValue() == 2}">
                                    đã chuyển
                                </c:if>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <button style="width: 100%; color: white;" type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#modal${item.getId()}">Thay đổi</button>
                        </div>
                    </div>
                </td>
                <td>${item.getEvaluation() == "" ? "Chưa có đánh giá" : item.getEvaluation()}</td>
                <c:if test="${item.getStars() == -1}">
                    <td>Chưa có đánh giá</td>
                </c:if>
                <c:if test="${item.getStars() != -1}">
                    <td style="width: 12%">
                            <div class="rate">
                                <c:forEach var="i" begin="1" end="5">
                                    <c:if test="${6-i <= item.getStars()}">
                                        <input disabled type="radio" name="rate" value="${6-i}"/>
                                        <label title="text" style="color: #ffc700">${6-i} star</label>
                                    </c:if>
                                    <c:if test="${6-i > item.getStars()}">
                                        <input disabled type="radio" name="rate" value="${6-i}"/>
                                        <label title="text">${6-i} star</label>
                                    </c:if>
                                </c:forEach>
                            </div>
                    </td>
                </c:if>
                <td title="${item.getStart()} - ${item.getEnd()}">${item.getSemester()}</td>
                <td>${item.getCreated_at()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<c:forEach var="item" items="${bills}">
    <div class="modal fade" id="modal${item.getId()}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="" method="post">
                    <input type="hidden" name="room_id" value="${item.getRoom_id()}">
                    <input type="hidden" name="start" value="${item.getStart()}">
                    <input type="hidden" name="end" value="${item.getEnd()}">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thay đổi trạng thái</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" hidden="hidden" name="bill_id" value="${item.getId()}">
                        <div class="form-group">
                            <label for="status">Chọn trạng thái</label>
                            <select class="form-control" name="status" id="status">
                                <option ${item.getStatus().getValue() == 1 ? "disabled" : ""} value="1">Đã thanh toán</option>
                                <option ${item.getStatus().getValue() == -1 ? "disabled" : ""}  value="-1">Huỷ</option>
                                <option ${item.getStatus().getValue() == 0 ? "disabled" : ""} value="0">Chưa thanh toán</option>
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
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    const table = $('#mytable').DataTable( {
        "order": [],
    } );
    let bill_id = '${pageContext.request.getParameter("bill_id")}';
    if (bill_id !== ''){
        bill_id = bill_id.replace(/-/g, '|')
        table.column(0).search(bill_id, true, false).draw()
        $('#mytable').removeAttr('hidden');
    }
</script>