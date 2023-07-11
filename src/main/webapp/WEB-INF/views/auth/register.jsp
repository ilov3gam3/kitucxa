<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="container" style="max-width: 50%">
    <div class="card">
        <div class="card-block">
            <div class="card-header">
                <div class="text-center">
                    <img src="https://lmsdn.fpt.edu.vn/pluginfile.php/1/core_admin/logo/0x200/1657934026/2021-FPTU-Eng.png" alt="">
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="container" style="max-width: 50%">
                        <c:if test="${not empty error}">
                            <p style="color: red">${error}</p>
                        </c:if>
                        <c:if test="${not empty success}">
                            <p style="color: green">${success}</p>
                        </c:if>
                        <p id="info" style="color: deepskyblue; display: none">Quá trình đăng kí có thể hơi lâu, vui lòng đợi!</p>
                        <form id="register-form" action="" method="post">
                            <div class="form-group">
                                <label for="name">Tên</label>
                                <input class="form-control m-2" type="text" id="name" name="name" placeholder="Nhập tên">
                            </div>
                            <div class="form-group">
                                <label for="student_code">Mã số sinh viên</label>
                                <input class="form-control m-2" type="text" id="student_code" name="student_code" placeholder="Nhập mã số sinh viên">
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input class="form-control m-2" type="text" id="email" name="email" placeholder="Nhập email">
                            </div>
                            <div class="form-group">
                                <label for="address">Địa chỉ</label>
                                <input class="form-control m-2" type="text" id="address" name="address" placeholder="Nhập địa chỉ">
                            </div>
                            <div class="form-group">
                                <label for="tel">Số điện thoại</label>
                                <input class="form-control m-2" type="text" id="tel" name="tel" placeholder="Nhập số điện thoại">
                            </div>
                            <div class="form-group">
                                <label for="birthday">Ngày sinh</label>
                                <input class="form-control m-2" type="date" id="birthday" name="birthday" placeholder="Nhập tên">
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu</label>
                                <input class="form-control m-2" type="password" id="password" name="password" placeholder="Nhập Nhập Mật khẩu">
                            </div>

                            <button style="width: 100%; background-color: #0e63ae; color: white;" class="btn btn-success m-2">Đăng kí</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script >
    var form = document.getElementById('register-form');
    form.addEventListener('submit', function(event) {
        console.log("form submit")
        var paragraph = document.getElementById("info");
        paragraph.style.removeProperty("display");
    });
</script>