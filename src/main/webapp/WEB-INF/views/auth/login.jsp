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
              <c:if test="${not empty login_fail}">
                <div class="alert alert-danger" role="alert">
                    ${login_fail}
                </div>
              </c:if>
              <form action="" method="post">
                <div class="form-group">
                  <label for="student_code_or_email">Mã sv hoặc mail</label>
                  <input class="form-control m-2" id="student_code_or_email" type="text" name="student_code_or_email" placeholder="Mã sinh viên hoặc email">
                </div>
                <div class="form-group">
                  <label for="password">Mật khẩu</label>
                  <input class="form-control m-2" id="password" type="password" name="password" placeholder="Mật khẩu">
                </div>
                <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn btn-success m-2">Đăng nhập</button>
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
