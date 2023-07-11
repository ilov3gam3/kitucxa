package Model;

public class Bill {
    public int id;
    public int room_id;
    public int user_id;
    public int price;
    public Status status;
    public String evaluation;
    public int stars;
    public String start;
    public String end;
    public String created_at;
    public String room_name;
    public String student_code;
    public String semester;
    public int cancel_id;
    public String cancel_reason;
    public Status cancel_status;
    public String cancel_created_at;


    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
    public int change_id;
    public String change_reason;
    public int change_room_to_id;
    public Status change_status;
    public String change_created_at;

    public Bill(int id, int room_id, int user_id, int price, Status status, String evaluation, int stars, String start, String end, String created_at) {
        this.id = id;
        this.room_id = room_id;
        this.user_id = user_id;
        this.price = price;
        this.status = status;
        this.evaluation = evaluation;
        this.stars = stars;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
    }

    public Bill(int id, int room_id, int user_id, int price, Status status, String evaluation, int stars, String start, String end, String created_at, String room_name) {
        this.id = id;
        this.room_id = room_id;
        this.user_id = user_id;
        this.price = price;
        this.status = status;
        this.evaluation = evaluation;
        this.stars = stars;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
        this.room_name = room_name;
        this.semester = Semester.getSemester(this.start, this.end);
    }
    public Bill(int id, int room_id, int user_id, int price, Status status, String evaluation, int stars, String start, String end, String created_at, String room_name, String username) {
        this.id = id;
        this.room_id = room_id;
        this.user_id = user_id;
        this.price = price;
        this.status = status;
        this.evaluation = evaluation;
        this.stars = stars;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
        this.room_name = room_name;
        this.student_code = username;
        this.semester = Semester.getSemester(this.start, this.end);
    }

    public Bill(int id, int room_id, int user_id, int price, Status status, String evaluation, int stars, String start, String end, String created_at, String room_name, int cancel_id, String cancel_reason, Status cancel_status, String cancel_created_at,int change_id, String change_reason, int change_room_to_id, Status change_status, String change_created_at) {
        this.id = id;
        this.room_id = room_id;
        this.user_id = user_id;
        this.price = price;
        this.status = status;
        this.evaluation = evaluation;
        this.stars = stars;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
        this.room_name = room_name;
        this.cancel_id = cancel_id;
        this.cancel_reason = cancel_reason;
        this.cancel_status = cancel_status;
        this.cancel_created_at = cancel_created_at;
        this.semester = Semester.getSemester(this.start, this.end);
        this.change_id = change_id;
        this.change_reason = change_reason;
        this.change_room_to_id = change_room_to_id;
        this.change_status = change_status;
        this.change_created_at = change_created_at;

    }

    public int getId() {
        return id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public int getStars() {
        return stars;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getCancel_id() {
        return cancel_id;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public Status getCancel_status() {
        return cancel_status;
    }

    public String getCancel_created_at() {
        return cancel_created_at;
    }

    public int getChange_id() {
        return change_id;
    }

    public String getChange_reason() {
        return change_reason;
    }

    public int getChange_room_to_id() {
        return change_room_to_id;
    }

    public Status getChange_status() {
        return change_status;
    }

    public String getChange_created_at() {
        return change_created_at;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", user_id=" + user_id +
                ", price=" + price +
                ", status=" + status +
                ", evaluation='" + evaluation + '\'' +
                ", stars=" + stars +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", created_at='" + created_at + '\'' +
                ", room_name='" + room_name + '\'' +
                ", student_code='" + student_code + '\'' +
                ", semester='" + semester + '\'' +
                ", cancel_id=" + cancel_id +
                ", cancel_reason='" + cancel_reason + '\'' +
                ", cancel_status=" + cancel_status +
                ", cancel_created_at='" + cancel_created_at + '\'' +
                ", change_id=" + change_id +
                ", change_reason='" + change_reason + '\'' +
                ", change_room_to_id=" + change_room_to_id +
                ", change_status=" + change_status +
                ", change_created_at='" + change_created_at + '\'' +
                '}' + "\n";
    }
}
