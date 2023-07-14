package Model;

public class ExtraBill {
    public int id;
    public int electricity;
    public int electricity_end;
    public int electricity_price;
    public int water;
    public int water_end;
    public int water_price;
    public boolean status;
    public int room_id;
    public String start;
    public String end;
    public String room_name;
    public int price;
    public int bill_id;
    public String username;
    public String user_email;

    public ExtraBill() {
    }

    public void ExtraBillForSendMail(int id, int electricity, int electricity_price, int water, int water_price, boolean status, int room_id, String start, String end, String room_name, int bill_id, String username, String user_email, int electricity_end, int water_end) {
        this.id = id;
        this.electricity = electricity;
        this.electricity_price = electricity_price;
        this.water = water;
        this.water_price = water_price;
        this.status = status;
        this.room_id = room_id;
        this.start = start;
        this.end = end;
        this.room_name = room_name;
        this.bill_id = bill_id;
        this.username = username;
        this.user_email = user_email;
        this.electricity_end = electricity_end;
        this.water_end = water_end;
    }

    public ExtraBill(int id, int electricity, int electricity_price, int water, int water_price, boolean status, int room_id, String start, String end, String room_name, int price, int electricity_end, int water_end) {
        this.id = id;
        this.electricity = electricity;
        this.electricity_price = electricity_price;
        this.water = water;
        this.water_price = water_price;
        this.status = status;
        this.room_id = room_id;
        this.start = start;
        this.end = end;
        this.room_name = room_name;
        this.price = price;
        this.electricity_end = electricity_end;
        this.water_end = water_end;
    }

    public int getElectricity_end() {
        return electricity_end;
    }

    public int getWater_end() {
        return water_end;
    }

    public int getBill_id() {
        return bill_id;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_email() {
        return user_email;
    }

    public int getId() {
        return id;
    }

    public int getElectricity() {
        return electricity;
    }

    public int getElectricity_price() {
        return electricity_price;
    }

    public int getWater() {
        return water;
    }

    public int getWater_price() {
        return water_price;
    }

    public boolean isStatus() {
        return status;
    }

    public int getRoom_id() {
        return room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ExtraBill{" +
                "id=" + id +
                ", electricity=" + electricity +
                ", electricity_price=" + electricity_price +
                ", water=" + water +
                ", water_price=" + water_price +
                ", status=" + status +
                ", room_id=" + room_id +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", room_name='" + room_name + '\'' +
                ", price=" + price +
                ", bill_id=" + bill_id +
                ", username='" + username + '\'' +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
