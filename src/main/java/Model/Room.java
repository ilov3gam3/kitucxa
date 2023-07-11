package Model;

public class Room {
    public int id;
    public String name;
    public int floor_id;
    public boolean is_available;
    public int price;
    public String floor_name;
    public int number;
    public String start;
    public String end;

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

    public int getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public Room(int id, String name, int price, String start, String end) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public Room() {
    }

    public Room(int id, String name, int floor_id, boolean is_available, int price) {
        this.id = id;
        this.name = name;
        this.floor_id = floor_id;
        this.is_available = is_available;
        this.price = price;
    }

    public Room(int id, String name, int floor_id,boolean is_available, int price, int number) {
        this.id = id;
        this.name = name;
        this.floor_id = floor_id;
        this.is_available = is_available;
        this.price = price;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", floor_id=" + floor_id +
                ", is_available=" + is_available +
                ", price=" + price +
                ", floor_name='" + floor_name + '\'' +
                '}';
    }
}
