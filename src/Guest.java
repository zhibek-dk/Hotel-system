
import java.util.Objects;

public class Guest {
    private static int nextId=1;
    private int id;
    private String name;
    private String phone;
    public Guest( String name, String phone) {
        this.id=nextId++;
        this.name=name;
        this.phone=phone;
    }
    public Guest(int id,String name,String phone) {
        this.id=id;
        this.name=name;
        this.phone=phone;

        if(nextId<=id) {
            nextId=id+1;
        }
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public void checkIn(HotelRoom room) {
        if(room.isAvailability()) {
            room.setAvailability(false);
            System.out.println(name+" checked into room "+room.getRoomNumber());
        } else {
            System.out.println("Room "+room.getRoomNumber()+" is not available.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Guest)) return false;
        Guest guest=(Guest)obj;
        return id==guest.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

