package Model;

public class User {
    public int id;
    public String student_code;
    public String name;
    public String email;
    public String address;
    public String tel;
    public String birthday;
    public String password;
    public String avatar;
    public String verify_key;
    public boolean admin;
    public boolean verified;

    public String last_chat_content;
    public boolean last_chat_is_img;
    public String last_chat_time;
    public String last_chat_sender;
    public User(int id, String student_code, String name, String email, String address, String tel, String birthday, String password, String avatar, String verify_key, boolean admin, boolean verified, String last_chat_content, boolean last_chat_is_img, String last_chat_time, String last_chat_sender) {
        this.id = id;
        this.student_code = student_code;
        this.name = name;
        this.email = email;
        this.address = address;
        this.tel = tel;
        this.birthday = birthday;
        this.password = password;
        this.avatar = avatar;
        this.verify_key = verify_key;
        this.admin = admin;
        this.verified = verified;
        this.last_chat_content = last_chat_content;
        this.last_chat_is_img = last_chat_is_img;
        this.last_chat_time = last_chat_time;
        this.last_chat_sender = last_chat_sender;
    }

    public User(int id, String student_code, String name, String email, String address, String tel, String birthday, String password, String avatar, String verify_key, boolean admin, boolean verified) {
        this.id = id;
        this.student_code = student_code;
        this.name = name;
        this.email = email;
        this.address = address;
        this.tel = tel;
        this.birthday = birthday;
        this.password = password;
        this.avatar = avatar;
        this.verify_key = verify_key;
        this.admin = admin;
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", student_code='" + student_code + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", birthday='" + birthday + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", verify_key='" + verify_key + '\'' +
                ", admin=" + admin +
                ", verified=" + verified +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVerify_key() {
        return verify_key;
    }

    public void setVerify_key(String verify_key) {
        this.verify_key = verify_key;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
