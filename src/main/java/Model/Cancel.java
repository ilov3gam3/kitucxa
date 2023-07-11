package Model;

public class Cancel {
    public int id;
    public int bill_id;
    public String reason;
    public Status status;
    public String created_at;
    public String username;
    public int user_id;
    public String start;
    public String end;
    public String semester;
    public String room_name;

    public Cancel(int id, int bill_id, String reason, Status status, String created_at, String username, int user_id, String start, String end, String room_name) {
        this.id = id;
        this.bill_id = bill_id;
        this.reason = reason;
        this.status = status;
        this.created_at = created_at;
        this.username = username;
        this.user_id = user_id;
        this.start = start;
        this.end = end;
        this.room_name = room_name;
        this.semester = Semester.getSemester(this.start, this.end);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Cancel(int id, int bill_id, String reason, Status status, String created_at) {
        this.id = id;
        this.bill_id = bill_id;
        this.reason = reason;
        this.status = status;
        this.created_at = created_at;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getSemester() {
        return semester;
    }

    public String getRoom_name() {
        return room_name;
    }

    @Override
    public String toString() {
        return "Cancel{" +
                "id=" + id +
                ", bill_id=" + bill_id +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                ", username='" + username + '\'' +
                ", user_id=" + user_id +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", semester='" + semester + '\'' +
                ", room_name='" + room_name + '\'' +
                '}';
    }
}
