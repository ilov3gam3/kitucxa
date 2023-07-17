<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../include/head.jsp" %>
<div class="container">
    <h1 class="text-center">${message}</h1>
    <div class="row">
        <h3 id="countdown"></h3>
    </div>
    <h3>Hoặc là nhấn vô <a href="${pageContext.request.contextPath}/">đây</a> để tới trang chủ ngay.</h3>
</div>
<%@ include file="../../include/foot.jsp" %>
<script>
    window.onload = function() {
        window.setTimeout(function () {
            location.href = "${pageContext.request.contextPath}/";
        }, 10000);
        let timeLeft = 10;
        const x = setInterval(function() {
            document.getElementById("countdown").innerHTML = "Bạn sẽ được chuyển hướng đến trang chủ trong: "+ timeLeft + " giây.";
            timeLeft--;
            if (timeLeft < 0) {
                clearInterval(x);
                document.getElementById("countdown").innerHTML = "0";
            }
        }, 1000);
    };
</script>
