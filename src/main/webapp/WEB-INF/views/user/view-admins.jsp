<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp"%>
<div class="row">
    <div class="container">
        <table class="table table-bordered table-striped" id="mytable">
            <thead>
            <tr>
                <th class="col-md-2">Tên Admin</th>
                <th class="col-md-4" >Email</th>
                <th class="col-md-1">Số điện thoại</th>
                <th class="col-md-1">Ngày sinh</th>
                <th class="">Avatar</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${admins}">
                <tr>
                    <td class="col-md-2">${item.getName()}</td>
                    <td class="col-md-4"> ${item.getEmail()} </td>
                    <td class="col-md-1">${item.getTel()}</td>
                    <td class="col-md-1">${item.getBirthday()}</td>
                    <td class="col-4 text-center" style="max-height: 300px;max-width: 300px;">
                        <img src="${pageContext.request.contextPath}/${item.getAvatar()}" alt="" style="width: 100%;max-height: 300px ;object-fit: contain; ">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../../include/foot.jsp"%>
<script>
    $('#mytable').dataTable( {
        "order": [],
    } );
</script>