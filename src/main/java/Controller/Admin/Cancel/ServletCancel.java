package Controller.Admin.Cancel;

import Dao.BillsDao;
import Dao.CancelDao;
import Model.Cancel;
import Model.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletCancel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("session_mess")!=null){
            String session_mess = (String) req.getSession().getAttribute("session_mess");
            req.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            req.getSession().removeAttribute("session_mess");
        }
        ArrayList<Cancel> arrayList;
        CancelDao cancelDao = new CancelDao();
        arrayList = cancelDao.getAllCancels();
        req.setAttribute("cancels", arrayList);
        req.getRequestDispatcher("/WEB-INF/views/admin/manage-cancels.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cancel_id = Integer.parseInt(req.getParameter("cancel_id"));
        int bill_id = Integer.parseInt(req.getParameter("bill_id"));
        int status = Integer.parseInt(req.getParameter("status"));
        System.out.println(status);
        String admin_reason = req.getParameter("admin_reason");
        CancelDao cancelDao = new CancelDao();
        if (cancelDao.updateStatus(cancel_id, status, bill_id, admin_reason)){
            req.getSession().setAttribute("session_mess", "success|Thay đổi thành công");
        } else {
            req.getSession().setAttribute("session_mess", "error|Thay đổi thất bại");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-cancels");
    }
}
