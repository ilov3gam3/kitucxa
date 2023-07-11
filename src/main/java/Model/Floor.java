package Model;

public class Floor {
    public int id;
    public int floor;
    public String name;
    public int building_id;
    public String building_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public Floor(int id, int floor, String name, int building_id) {
        this.id = id;
        this.floor = floor;
        this.name = name;
        this.building_id = building_id;
    }

    public Floor(int id, int floor, String name, int building_id, String building_name) {
        this.id = id;
        this.floor = floor;
        this.name = name;
        this.building_id = building_id;
        this.building_name = building_name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\": ").append(id).append(", ");
        sb.append("\"floor\": ").append(floor).append(", ");
        sb.append("\"name\": \"").append(name).append("\", ");
        sb.append("\"building_id\": ").append(building_id).append(", ");
        sb.append("\"building_name\": \"").append(building_name).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
