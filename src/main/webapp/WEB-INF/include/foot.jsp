</div>
</main>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.7.0.js" integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.0/js/all.min.js"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/assets/demo/chart-area-demo.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/assets/assets/demo/chart-bar-demo.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/datatables-simple-demo.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/js/toastr.js"></script>
</body>
<script>
    const mess_error = "${error}"
    const mess_success = "${success}"
    const mess_warning = "${warning}"
    const mess_warning1 = "${warning1}"
    const info = "${info}"
    if (mess_error !== ""){
        toastr.error(mess_error)
    }
    if (mess_success !== ""){
        toastr.success(mess_success)
    }
    if (mess_warning !== ""){
        toastr.warning(mess_warning)
    }
    if (mess_warning1 !== ""){
        toastr.warning(mess_warning1)
    }
    if (info !== ""){
        toastr.info(info)
    }
</script>
</html>