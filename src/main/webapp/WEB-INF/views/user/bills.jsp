<%@ page import="Model.Bill" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
    <h3>Danh sách hoá đơn của bạn</h3>
    <table ${pageContext.request.getParameter("bill_id") != null ? "hidden" : ""}
            class="table table-bordered table-striped" id="mytable">
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
        <% ArrayList<Bill> arrayList = (ArrayList<Bill>) request.getAttribute("bills");%>
        <c:forEach var="item" items="${bills}" varStatus="loop">
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
                    </div>
                </td>
                <td>
                    <c:if test="${item.getStatus().getValue() == 1}">
                        <c:if test="${item.getEvaluation() == ''}">
                            <fmt:parseDate var="start_date_current_semester" value="${current_semester[0]}"
                                           pattern="yyyy-MM-dd"/>
                            <fmt:parseDate var="this_bill_start" value="${item.getStart()}" pattern="yyyy-MM-dd"/>
                            <c:if test="${start_date_current_semester eq this_bill_start}">
                                <%--cho nhận xét --%>
                                <a href="${pageContext.request.contextPath}/user/make-review?bill_id=${item.getId()}">
                                    <button class="btn btn-success">Nhận xét</button>
                                </a>
                            </c:if>
                            <c:if test="${start_date_current_semester lt this_bill_end}">
                                chưa có nhận xét
                            </c:if>
                        </c:if>
                        <c:if test="${item.getEvaluation() != ''}">
                            ${item.getEvaluation()}
                        </c:if>
                    </c:if>
                    <c:if test="${item.getStatus().getValue() != 1}">
                        <c:if test="${item.getEvaluation() == ''}">
                            Chưa có nhận xét
                        </c:if>
                        <c:if test="${item.getEvaluation() != ''}">
                            ${item.getEvaluation()}
                        </c:if>
                    </c:if>
                </td>
                <c:if test="${item.getStars() == -1}">
                    <td>Chưa có đánh giá</td>
                </c:if>
                <c:if test="${item.getStars() != -1}">
                    <td>
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
                            <textarea required class="form-control m-1" name="reason" id="reason" rows="5"></textarea>
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
    if (bill_id !== '') {
        table.fnFilter(bill_id, 0);
        $('#mytable').removeAttr('hidden');
    }
</script>