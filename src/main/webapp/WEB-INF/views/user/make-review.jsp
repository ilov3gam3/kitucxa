<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div>
  <h1>Nhận xét</h1>
  <div class="row">
    <div class="col-md-6">
      <form action="" method="post">
        <div class="form-group m-2">
          <label for="review">Nhập nhận xét</label>
          <textarea class="form-control" name="review" id="review" rows="5"></textarea>
        </div>
        <button class="btn btn-success" style="width: 100%">submit</button>
      </form>
    </div>
  </div>
</div>
<%@ include file="../../include/foot.jsp"%>