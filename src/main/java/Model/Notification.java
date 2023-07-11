package Model;

public class Notification {
    public int id;
    public String name;
    public String content;
    public String start;
    public String end;
    public String created_at;

    public Notification(int id, String name, String content, String start, String end, String created_at) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
