<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="container">
    <h3>Bạn đang xem đánh giá của phòng <%=request.getParameter("room_name")%></h3>
    <c:forEach var="item" items="${bills}">
      <div class="bg-light p-5 rounded m-2" style="background-color: #E9ECEF">
        <p> <span class="fw-bold">Người đánh giá : </span> <a href="${pageContext.request.contextPath}/admin/user-info?student_code=${item.getStudent_code()}">${item.getUsername()}-${item.getStudent_code()}</a> </p>
        <p> <span class="fw-bold" >Nội dung đánh giá : </span> ${item.getEvaluation()}</p>
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
      </div>
    </c:forEach>
</div>
<%@ include file="../../include/foot.jsp"%>