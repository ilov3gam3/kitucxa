package Model;

public class Change {
    public int id;
    public int bill_id;
    public String reason;
    public int room_to_id;
    public Status status;
    public String created_at;
    public int user_id;
    public String username;
    public String room_from_name;
    public String room_to_name;
    public String start;
    public String end;
    public String semester;
    public String admin_reason;

    public Change(int id, int bill_id, String reason, int room_to_id, Status status, String created_at, int user_id, String username, String room_from_name, String room_to_name,String start, String end, String admin_reason) {
        this.id = id;
        this.bill_id = bill_id;
        this.reason = reason;
        this.room_to_id = room_to_id;
        this.status = status;
        this.created_at = created_at;
        this.user_id = user_id;
        this.username = username;
        this.room_from_name = room_from_name;
        this.room_to_name = room_to_name;
        this.start = start;
        this.end = end;
        this.semester = Semester.getSemester(start, end);
        this.admin_reason = admin_reason;
    }

    public String getAdmin_reason() {
        return admin_reason;
    }

    public int getId() {
        return id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public String getReason() {
        return reason;
    }

    public int getRoom_to_id() {
        return room_to_id;
    }

    public Status getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getRoom_from_name() {
        return room_from_name;
    }

    public String getRoom_to_name() {
        return room_to_name;
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

    @Override
    public String toString() {
        return "Change{" +
                "id=" + id +
                ", bill_id=" + bill_id +
                ", reason='" + reason + '\'' +
                ", room_to_id=" + room_to_id +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", room_from_name='" + room_from_name + '\'' +
                ", room_to_name='" + room_to_name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
