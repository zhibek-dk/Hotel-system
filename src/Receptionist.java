public class Receptionist {
    private String name;

    public Receptionist(String name) {
        this.name=name;
    }

    public Reservation makeReservation(Guest guest,HotelRoom room,int nights,Payment payment,String cID) {
        Reservation reservation=new Reservation(guest,room,nights,payment,cID);
        System.out.println(name+" made a reservation.");
        reservation.showReservation();
        return reservation;
    }
    public void cancelReservation(Reservation reservation) {
        reservation.cancelReservation();
        System.out.println(name+" cancelled the reservation.");
    }
}