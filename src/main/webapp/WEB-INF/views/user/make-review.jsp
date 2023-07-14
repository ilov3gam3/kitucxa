<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="row">
  <div class="col-md-6">
    <h2>Đánh giá phòng ${bill.getRoom_name()} trong kì ${bill.getSemester()}</h2>
    <form action="" method="post">
      <div class="form-group">
        <label for="review">Nhận xét</label>
        <textarea class="form-control" name="review" id="review" rows="10" placeholder="Viết nhận xét ở đây"></textarea>
      </div>
      <div class="form-group">
        <div class="rate">
          <input type="radio" id="star5" name="rate" value="5" />
          <label for="star5" title="text">5 stars</label>
          <input type="radio" id="star4" name="rate" value="4" />
          <label for="star4" title="text">4 stars</label>
          <input type="radio" id="star3" name="rate" value="3" />
          <label for="star3" title="text">3 stars</label>
          <input type="radio" id="star2" name="rate" value="2" />
          <label for="star2" title="text">2 stars</label>
          <input type="radio" id="star1" name="rate" value="1" />
          <label for="star1" title="text">1 star</label>
        </div>
      </div>
        <button class="btn text-center mt-2" style="width: 100%; background-color: #0e63ae; color: white;">Đánh giá
      </button>
    </form>
  </div>
</div>
<%@ include file="../../include/foot.jsp" %>
