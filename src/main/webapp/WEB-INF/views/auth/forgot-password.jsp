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
                        <form action="" method="post">
                            <div class="form-group">
                                <label for="email">Nhập Email</label>
                                <input class="form-control m-2" id="email" type="email" name="email" placeholder="Email">
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
