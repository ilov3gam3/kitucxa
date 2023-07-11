<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
    <h3>Danh sách hoá đơn của bạn</h3>
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
    <table ${pageContext.request.getParameter("bill_id") != null ? "hidden" : ""} class="table table-bordered table-striped" id="mytable">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th>Tên phòng</th>
            <th>Giá</th>
            <th>Trạng thái</th>
            <th>Nhận xét</th>
            <th>Đánh giá</th>
            <th>Kì</th>
            <th>Tạo lúc</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${bills}">
            <tr>
                <td>${item.getId()}</td>
                <td>${item.getRoom_name()}</td>
                <td>${item.getPrice()}</td>
                <td>
                    <div class="row">
                        <div class="col-md-12">
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
                </td>
                <td>${item.getEvaluation() == "" ? "Chưa có đánh giá" : item.getEvaluation()}</td>
                <c:if test="${item.getStars() == -1}">
                    <td>Chưa có đánh giá</td>
                </c:if>
                <c:if test="${item.getStars() != -1}">
                    <td>${item.getStars()} <i class="fas fa-star"></i></td>
                </c:if>
                <td title="${item.getStart()} - ${item.getEnd()}">${item.getSemester()}</td>
                <td>${item.getCreated_at()}</td>
                <td>
                    <c:if test="${item.getStatus().getValue() == 1}">
                        <form action="${pageContext.request.contextPath}/user/view-user-in-room" class="mb-1">
                            <input type="hidden" name="room_id" value="${item.getRoom_id()}">
                            <input type="hidden" name="start_date" value="${item.getStart()}">
                            <input type="hidden" name="end_date" value="${item.getEnd()}">
                            <button style="width: 100%;" class="btn btn-info">Xem thành viên</button>
                        </form>

                        <c:if test="${item.getChange_id() != 0}">
                            <c:if test="${item.getChange_status().getValue() == 0}">
                                <p class="text-info">Đã gửi đơn chuyển phòng, chờ xét duyệt.</p>
                            </c:if>
                            <c:if test="${item.getChange_status().getValue() == 1}">
                                <p class="text-success">Đơn chuyển phòng đã được đồng ý.</p>
                            </c:if>
                            <c:if test="${item.getChange_status().getValue() == -1}">
                                <form action="${pageContext.request.contextPath}/user/change-room" class="mb-1">
                                    <input type="hidden" name="bill_id" value="${item.getId()}">
                                    <button style="width: 100%;" class="btn btn-outline-warning">Chuyển phòng</button>
                                </form>
                            </c:if>
                        </c:if>
                        <c:if test="${item.getChange_id() == 0 && item.getStatus().getValue() == 1}">
                            <form action="${pageContext.request.contextPath}/user/change-room" class="mb-1">
                                <input type="hidden" name="bill_id" value="${item.getId()}">
                                <button style="width: 100%;" class="btn btn-outline-warning">Chuyển phòng</button>
                            </form>
                        </c:if>

                        <c:if test="${item.getCancel_id() != 0}">
                            <c:if test="${item.getCancel_status().getValue() == 0}">
                                <p class="text-info">Đã gửi đơn huỷ phòng, chờ xét duyệt.</p>
                            </c:if>
                            <c:if test="${item.getCancel_status().getValue() == 1}">
                                <p class="text-success">Đơn huỷ phòng đã được đồng ý.</p>
                            </c:if>
                            <c:if test="${item.getCancel_status().getValue() == -1}">
                                <button style="width: 100%;" class="btn btn-danger" type="button" data-bs-toggle="modal"
                                        data-bs-target="#modal${item.getId()}">Huỷ
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${item.getCancel_id() == 0}">
                            <button style="width: 100%;" class="btn btn-danger" type="button" data-bs-toggle="modal"
                                    data-bs-target="#modal${item.getId()}">Huỷ
                            </button>
                        </c:if>

                    </c:if>
                    <c:if test="${item.getStatus().getValue() == -1}">

                    </c:if>
                    <c:if test="${item.getStatus().getValue() == 0}">
                        <c:if test="${item.getCancel_id() != 0}">
                            <c:if test="${item.getCancel_status().getValue() == 0}">
                                <p class="text-info">Đã gửi đơn huỷ phòng, chờ xét duyệt.</p>
                            </c:if>
                            <c:if test="${item.getCancel_status().getValue() == 1}">
                                <p class="text-success">Đơn huỷ phòng đã được đồng ý.</p>
                            </c:if>
                            <c:if test="${item.getCancel_status().getValue() == -1}">
                                <button style="width: 100%;" class="btn btn-danger" type="button" data-bs-toggle="modal"
                                        data-bs-target="#modal${item.getId()}">Huỷ
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${item.getCancel_id() == 0}">
                            <button style="width: 100%;" class="btn btn-danger" type="button" data-bs-toggle="modal"
                                    data-bs-target="#modal${item.getId()}">Huỷ
                            </button>
                        </c:if>
                    </c:if>
                    <%--<c:if test="${item.getStatus().getValue() == 1}">
                        <form action="${pageContext.request.contextPath}/user/view-user-in-room" class="mb-1">
                            <input type="hidden" name="room_id" value="${item.getRoom_id()}">
                            <input type="hidden" name="start_date" value="${item.getStart()}">
                            <input type="hidden" name="end_date" value="${item.getEnd()}">
                            <button style="width: 100%;" class="btn btn-info">Xem thành viên</button>
                        </form>
                        <c:if test="${item.getChange_id() == 0}">
                            <form action="${pageContext.request.contextPath}/user/change-room" class="mb-1">
                                <input type="hidden" name="bill_id" value="${item.getId()}">
                                <button style="width: 100%;" class="btn btn-outline-warning">Chuyển phòng</button>
                            </form>
                        </c:if>
                        <c:if test="${item.getChange_id() != 0}">
                            <c:if test="${item.getChange_status().getValue() == 0}">
                                <p class="text-info">Đã gửi đơn chuyển phòng, chờ xét duyệt.</p>
                            </c:if>
                            <c:if test="${item.getChange_status().getValue() == 1}">
                                <p class="text-success">Đơn chuyển phòng đã được đồng ý.</p>
                            </c:if>
                            <c:if test="${item.getChange_status().getValue() == -1}">
                                <p class="text-danger">Đơn chuyển phòng bị từ chối.</p>
                            </c:if>
                        </c:if>

                    </c:if>
                    <c:if test="${item.getStatus().getValue() != -1}">
                        <c:if test="${item.getCancel_id() != 0 && item.getCancel_status().getValue() == 0}">
                            <p class="text-info">Đã gửi yêu cầu huỷ, chờ xét duyệt </p>
                        </c:if>
                        <c:if test="${item.getCancel_id() != 0 && item.getCancel_status().getValue() == -1}">
                            <p class="text-danger">Yêu cầu huỷ không được đồng ý.</p>
                        </c:if>
                        <c:if test="${item.getCancel_id() == 0 && item.getChange_id() == 0}">
                            <button style="width: 100%;" class="btn btn-danger" type="button" data-bs-toggle="modal"
                                    data-bs-target="#modal${item.getId()}">Huỷ
                            </button>
                        </c:if>
                        <c:if test="${item.getCancel_id() == 0 && item.getChange_id() != 0}">

                        </c:if>
                    </c:if>
                    <c:if test="${item.getStatus().getValue() == -1}">
                        <c:if test="${item.getCancel_id() != 0 && item.getCancel_status().getValue() == 1}">
                            <p class="text-success">Yêu cầu huỷ đã được đồng ý</p>
                        </c:if>
                    </c:if>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:forEach var="item" items="${bills}">
    <div class="modal fade" id="modal${item.getId()}" tabindex="-1" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="" method="post">
                    <input type="hidden" name="bill_id" value="${item.getId()}">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Huỷ phòng ${item.getRoom_name()}
                            kì ${item.getSemester()}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" hidden="hidden" name="bill_id" value="${item.getId()}">
                        <div class="form-group">
                            <label for="reason">Lý do huỷ</label>
                            <textarea required class="form-control m-1" name="reason" id="reason" rows="10"></textarea>
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
<%@ include file="../../include/foot.jsp" %>
<script>
    const table = $('#mytable').dataTable({
        "order": [],
    });
    const bill_id = '${pageContext.request.getParameter("bill_id")}';
    if (bill_id !== ''){
        table.fnFilter(bill_id, 0);
        $('#mytable').removeAttr('hidden');
    }
</script>