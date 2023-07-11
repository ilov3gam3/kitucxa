<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div>
    <h1>Profile</h1>
    <a href="${pageContext.request.contextPath}/${user.getAvatar()}">Xem ảnh đại diện</a>
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
    <div class="row">
        <div class="col-md-6">
            <form action="" method="post">
                <div class="form-group m-2">
                    <label for="student_code">Mã sinh viên</label>
                    <input class="form-control" type="text" id="student_code" name="student_code" value="${user.getStudent_code()}" readonly>
                </div>
                <div class="form-group m-2">
                    <label for="name">Tên</label>
                    <input class="form-control" type="text" id="name" name="name" value="${user.getName()}">
                </div>
                <div class="form-group m-2">
                    <label for="current_password">Mật khẩu hiện tại</label>
                    <input class="form-control" type="password" id="current_password" name="current_password">
                </div>
                <div class="form-group m-2">
                    <label for="new_password">Mật khẩu mới</label>
                    <input class="form-control" type="password" id="new_password" name="new_password">
                </div>
                <div class="form-group m-2">
                    <label for="re_password">Nhập lại mật khẩu mới</label>
                    <input class="form-control" type="password" id="re_password" name="re_password">
                </div>
                <button class="btn btn-success" style="width: 100%">submit</button>
            </form>
            <h4>Ảnh đại diện</h4>
            <img src="${pageContext.request.contextPath}/${user.getAvatar()}" alt="" style="width: 100%">
        </div>
        <div class="col-md-6">
            <form action="${pageContext.request.contextPath}/update-avatar" method="post" enctype="multipart/form-data">
                <div class="form-group m-2">
                    <label for="image">Thêm ảnh đại diện</label>
                    <input type="file" name="image" id="image">
                </div>
                <div class="preview-container">
                    <h5>Preview:</h5>
                    <img id="previewImage" class="img-fluid">
                </div>
                <button class="btn btn-success mt-2" style="width: 100%">submit</button>
            </form>

        </div>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    document.getElementById('image').addEventListener('change', function(e) {
        var file = e.target.files[0];
        var reader = new FileReader();

        reader.onload = function(e) {
            document.getElementById('previewImage').setAttribute('src', e.target.result);
        }

        reader.readAsDataURL(file);
    });
</script>