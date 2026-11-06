
import java.util.Objects;

public class Reservation{

    private static int nextId=1;
    private int id;
    private Guest guest;
    private HotelRoom room;
    private int nights;
    private String checkInDate;;
    private Payment payment;
    private String status;

    public Reservation(Guest guest,HotelRoom room,int nights,Payment payment,String cID) {
        this.id=nextId++;
        this.guest=guest;
        this.room=room;
        this.nights=nights;
        this.payment=payment;
        this.checkInDate=cID;

        this.status="Active";
    }
    public Reservation(int id,Guest guest,HotelRoom room,int nights,Payment payment,String checkInDate,String status) {
        this.id=id;
        this.guest=guest;
        this.room=room;
        this.nights=nights;
        this.payment=payment;
        this.checkInDate=checkInDate;
        this.status=status;

        if(nextId<=id) {
            nextId=id+1;
        }
    }
    public int getId() {
        return id;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getNights() {
        return nights;
    }

    public String getCheckInDate() {
        return checkInDate;
    }


    public String getStatus() {
        return status;
    }
    public Payment getPayment() {
        return payment;
    }
    public void processReservation() {
        if(status.equals("Active")) {
            status="Processed";
        }
    }
    public void setRoom(HotelRoom room) {
        this.room=room;
    }

    public void setNights(int nights) {
        this.nights=nights;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate=checkInDate;
    }

    public double calculateTotalPrice() {
        return room.FinalPrice(nights);
    }

    public void cancelReservation() {
        if(status.equals("Active")) {
            status="Cancelled";
            room.setAvailability(true);

        }
    }

    public void showReservation() {
        System.out.println("Reservation ID: "+id);
        System.out.println("Guest: "+guest.getName()+" ("+guest.getPhone()+")");
        System.out.println("Room: "+room.getRoomNumber());
        System.out.println("Nights: "+nights);
        System.out.println("Check-in: "+checkInDate);
        System.out.println("Payment: "+payment.getAmount()+" ("+payment.getMethod()+")");
        System.out.println("Status: "+status);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Reservation)) return false;
        Reservation reservation=(Reservation)obj;
        return id==reservation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


