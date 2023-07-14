package Model;

public class Chat {
    public String id;
    public String sender_id;
    public String receiver_id;
    public boolean is_image;
    public String content;
    public String created_at;

    public Chat(String id, String sender_id, String receiver_id, boolean is_image, String content, String created_at) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.is_image = is_image;
        this.content = content;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public boolean isIs_image() {
        return is_image;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                ", is_image=" + is_image +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
