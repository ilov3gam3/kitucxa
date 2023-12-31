<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="container">
  <div class="card">
    <div class="card-block">
      <div class="card-header">
        <div class="text-center">
          <img src="https://lmsdn.fpt.edu.vn/pluginfile.php/1/core_admin/logo/0x200/1657934026/2021-FPTU-Eng.png" alt="">
        </div>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-6">
            <form action="" method="post">
              <div class="form-group">
                <label for="password">Nhập mật khẩu mới</label>
                <input class="form-control m-2" id="password" type="password" name="password" placeholder="Password">
              </div>
              <div class="form-group">
                <label for="re_password">Nhập lại mật khẩu mới</label>
                <input class="form-control m-2" id="re_password" type="password" name="re_password" placeholder="Password">
              </div>
              <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn btn-success m-2">Gửi</button>
            </form>
          </div>
          <div class="col-md-6 text-center">
            <a href="${pageContext.request.contextPath}/forgot-password">Forgotten your username or password?</a>
            <p>Cookies must be enabled in your browser</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="../../include/foot.jsp"%>
