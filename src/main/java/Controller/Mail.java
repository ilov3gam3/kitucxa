package Controller;


import Dao.BillsDao;
import Dao.UserDao;
import Model.Bill;
import Model.Config;
import Model.Semester;
import Model.User;
import jakarta.servlet.http.HttpServletRequest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Mail {
    public static String getHost(HttpServletRequest request){
        String domain = request.getServerName();
        String protocol = request.getScheme();
        int port = request.getServerPort();
        String context = request.getContextPath();
        return protocol + "://" + domain +":"+ port + context;
    }
    public static boolean send(String mail_to,String subject, String html){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // if authentication is required
        properties.put("mail.smtp.starttls.enable", "true"); // if using TLS
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.map.get("email_address"), Config.map.get("email_address_password"));
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.map.get("email_address"), "Kí túc xá FPT"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
            message.setSubject(subject);
            message.setContent(html, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean verify_email(String mailto, String name, String uuid, String host) {
        System.out.println("verify send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // if authentication is required
        properties.put("mail.smtp.starttls.enable", "true"); // if using TLS
        // Create a Session instance with the email properties
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("tranquangminh116@gmail.com", "iahskjpdwuvcyzym");
                return new PasswordAuthentication(Config.map.get("email_address"), Config.map.get("email_address_password"));
            }
        });
        try {
            // Create a MimeMessage instance
            MimeMessage message = new MimeMessage(session);

            // Set the From address
            message.setFrom(new InternetAddress("tranquangminh116@gmail.com", "Kí túc xá FPT"));

            // Set the To address(es)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailto));

            // Set the email subject
            message.setSubject("Xác nhận tài khoản.");
            String html = "<h1>Xin chào " + name + ", chào mừng bạn đến với kí túc xá FPT. Để kích hoạt tài khoản vui lòng nhấn vào <a href='" + host + "/active/" + uuid + "'>đây.</a></h1>";
            // Set the email body
            message.setContent(html, "text/html; charset=UTF-8");

            // Send the email
            Transport.send(message);
            System.out.println("verify mail sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register_room_success(String mailto, String name, int year, String semester, HttpServletRequest request) {
        System.out.println("register_room_success send mail");
        String host = getHost(request);
        System.out.println("get host done");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // if authentication is required
        properties.put("mail.smtp.starttls.enable", "true"); // if using TLS
        System.out.println("properties set done");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.map.get("email_address"), Config.map.get("email_address_password"));
            }
        });
        System.out.println("auth ok");
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tranquangminh116@gmail.com", "Kí túc xá FPT"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailto));
            message.setSubject("Đăng kí thành công.");
            String html = "<h1>Xin chào " + name + ", Bạn đã đăng kí phòng thành công của kì " + semester + " năm " + year + " nhấn vào <a href='" + host + "/user/bills'> đây </a> để xem hoá đơn của bạn.</h1>";
            message.setContent(html, "text/html; charset=UTF-8");
            System.out.println("about to send");
            Transport.send(message);
            System.out.println("register_room_success mail sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false ;
        }
    }
    public static boolean adminConfirmRegisterRoom(HttpServletRequest request, int bill_id){
        System.out.println("adminConfirmRegisterRoom send mail");
        BillsDao billsDao = new BillsDao();
        Bill bill = billsDao.getBillById(bill_id);
        User user = new UserDao().findUserById(bill.user_id);
        String semester = Semester.getSemester(bill.start.split(" ")[1], bill.end.split(" ")[1]);
        System.out.println("get data ok");
        String host = getHost(request);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // if authentication is required
        properties.put("mail.smtp.starttls.enable", "true"); // if using TLS
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.map.get("email_address"), Config.map.get("email_address_password"));
            }
        });
        System.out.println("auth ok");
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tranquangminh116@gmail.com", "Kí túc xá FPT"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.email));
            message.setSubject("Đăng kí thành công.");
            String html = "<h1>Xin chào " + user.name + ", Admin đã xác nhận phòng "+bill.room_name+" của kì " + semester + " năm " + bill.start.split(" ")[0].split(":")[0] + " của bạn. Nhấn vào <a href='" + host + "/user/bills'> đây </a> để xem hoá đơn của bạn.</h1>";
            message.setContent(html, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("adminConfirmRegisterRoom mail sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean forgot_password(String mailto, String key, String host) {
        System.out.println("forgot_password send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // if authentication is required
        properties.put("mail.smtp.starttls.enable", "true"); // if using TLS
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.map.get("email_address"), Config.map.get("email_address_password"));
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.map.get("email_address"), "Kí túc xá FPT"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailto));
            message.setSubject("Lấy lại mật khẩu.");
            String html = "<h1>Nhấp vào <a href = '" + host + "/reset-password?key=" + key + "' target='_blank'>đây</a> để lấy lại mật khẩu</h1>";
            message.setContent(html, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("forgot_password mail sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
