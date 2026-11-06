
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);

        HotelService hotelService=new HotelService();
        ReservationService reservationService=new ReservationService(hotelService);

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====\n1. Room Management\n2. Reservation Management\n3. Reservation Queue\n4. Reports\n0. Exit");
            System.out.print("Choose: ");
            choice=input.nextInt();

            switch(choice) {
                case 1:
                    hotelService.roomMenu(input);
                    break;
                case 2:
                    reservationService.reservationMenu(input,hotelService);
                    break;
                case 3:
                    reservationService.queueMenu(input);
                    break;
                case 4:
                    reservationService.reportsMenu(input,hotelService);
                    break;

                case 0:
                    System.out.println("Guests: "+hotelService.getGuests().size());
                    System.out.println("Rooms: "+hotelService.getRooms().size());
                    System.out.println("Reservations: "+reservationService.getReservations().size());

                    FileManager.saveGuests(hotelService.getGuests());
                    FileManager.saveRooms(hotelService.getRooms());
                    FileManager.saveReservations(reservationService.getReservations());

                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while(choice!=0);

        input.close();
    }
}

