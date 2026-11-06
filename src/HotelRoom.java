import java.util.Objects;

abstract class HotelRoom implements Comparable<HotelRoom>,Bookable,Cleanable {
    private int roomNumber;
    private int floor;
    private double price;
    private int capacity;
    private boolean availability;
    private boolean isClean;
    public HotelRoom() {
        roomNumber=0;
        floor=0;
        price=0;
        capacity=1;
        availability=true;
        isClean=true;
    }
    public HotelRoom(int rn,int f,double p,int c,boolean a){
        this.roomNumber=rn;
        this.floor=f;
        this.price=p;
        this.capacity=c;
        this.availability=a;
        isClean=true;
    }
    public boolean isClean() {
        return isClean;
    }
    public int getRoomNumber() {return roomNumber;}
    public int getFloor() {return floor;}
    public double getPrice() {return price;}
    public int getCapacity() {return capacity;}
    public boolean isAvailability() {
        return availability;
    }

    public void setRoomNumber(int roomNumber) {
        if(roomNumber>0){
            this.roomNumber=roomNumber;
        }else {
            System.out.println("Invalid room number.");}}
    public void setFloor(int floor) { this.floor = floor;}
    public void setPrice(double price) {
        if(price>=0){this.price = price;}
        else {
            System.out.println("Invalid price.");}}
    public void setCapacity(int capacity) {
        if(capacity>0){this.capacity = capacity;}
        else{
            System.out.println("Invalid capacity.");}}
    public void setAvailability(boolean availability) {this.availability = availability;}

    public void displayInfo(){
        System.out.println("Room number: "+roomNumber+"\nFloor: "+floor+"\nRaw Price: "+price+" dollars"+"\nCapacity: "+capacity+" people"+"\nAvailibility: "+availability);
    }
    public int compareTo(HotelRoom other) {
        return Integer.compare(this.roomNumber,other.roomNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null || getClass()!=obj.getClass()) return false;

        HotelRoom room=(HotelRoom)obj;
        return getRoomId().equals(room.getRoomId());
    }
    public int hashCode() {
        return Objects.hash(getRoomId());
    }

    public String getRoomId() {
        if(this instanceof StandartRoom) {
            return "S-"+roomNumber;
        }
        else if(this instanceof DeluxeRoom) {
            return "D-"+roomNumber;
        }
        else if(this instanceof SuiteRoom) {
            return "SU-"+roomNumber;
        }
        return "R-"+roomNumber;
    }
    public boolean bookRoom() {
        if(!availability || !isClean) {
            System.out.println("Room "+getRoomNumber()+" is not available for booking.");
            return false;
        }

        availability=false;
        System.out.println("Room "+getRoomNumber()+" has been booked.");
        return true;
    }

    @Override
    public void cancelBooking() {
        if(availability) {
            System.out.println("Room "+getRoomNumber()+" is not booked.");
            return;
        }

        availability=false;
        isClean=false;
        System.out.println("Room "+getRoomNumber()+" booking has been cancelled. Cleaning is required.");
    }

    @Override
    public void cleanRoom() {
        if(isClean) {
            System.out.println("Room "+getRoomNumber()+" is already clean.");
            return;
        }

        isClean=true;
        availability=true;
        System.out.println("Room "+getRoomNumber()+" has been cleaned and is available for booking.");
    }

    public void setClean(boolean clean) {
        isClean=clean;
    }
    abstract double getServiceFee();
    abstract double FinalPrice(int nights);

}

