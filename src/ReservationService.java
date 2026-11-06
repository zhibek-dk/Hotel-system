
import java.util.*;

public class ReservationService {
    private List<Reservation> reservations=new ArrayList<>();
    private Queue<Reservation> reservationQueue=new LinkedList<>();
    public ReservationService(HotelService hotelService) {
        reservations=FileManager.loadReservations(hotelService.getGuests(),hotelService.getRooms());

        for(Reservation reservation:reservations) {
            if(reservation.getStatus().equals("Active")) {
                reservationQueue.offer(reservation);
            }
        }
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations=reservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
    public void createReservation(Scanner input,HotelService hotelService) {
        System.out.println("===AVAILABLE ROOMS===");
        hotelService.searchAvailableRooms();
        System.out.println();
        input.nextLine();

        System.out.print("Enter guest name: ");
        String name=input.nextLine();

        System.out.print("Enter guest phone: ");
        String phone=input.nextLine();

        System.out.print("Enter room number: ");
        int number=input.nextInt();

        HotelRoom room=hotelService.findRoom(number);

        if(room==null) {
            System.out.println("Room not found.");
            return;
        }

        if(!room.isAvailability() || !room.isClean()) {
            System.out.println("Room is not available or needs cleaning.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int nights=input.nextInt();

        if(nights<=0) {
            System.out.println("Invalid number of nights.");
            return;
        }

        System.out.print("Enter check-in date: ");
        String checkIn=input.next();


        System.out.print("Enter payment amount: ");
        double amount=input.nextDouble();

        if(amount<0) {
            System.out.println("Invalid payment amount.");
            return;
        }

        System.out.print("Enter payment method: ");
        String method=input.next();

        Guest guest=new Guest(name,phone);
        Payment payment=new Payment(amount,method);

        Reservation reservation=new Reservation(guest,room,nights,payment,checkIn);

        hotelService.addGuest(guest);
        addReservation(reservation,hotelService);
    }

    public void addReservation(Reservation reservation,HotelService hotelService) {
        Bookable bookable=reservation.getRoom();

        if(!bookable.bookRoom()) {
            System.out.println("Room is not available or needs cleaning.");
            return;
        }

        reservations.add(reservation);
        reservationQueue.offer(reservation);

        FileManager.saveReservations(reservations);
        FileManager.saveRooms(hotelService.getRooms());

        System.out.println("Reservation created successfully.");
    }

    public void viewReservations() {
        if(reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for(int i=0;i<reservations.size();i++) {
            System.out.println("\nReservation number: "+(i+1));
            reservations.get(i).showReservation();
        }
    }

    public Reservation findReservation(int number) {
        if(number<1 || number>reservations.size()) {
            return null;
        }

        return reservations.get(number-1);
    }

    public void findReservationMenu(Scanner input) {
        System.out.print("Enter reservation number: ");
        int number=input.nextInt();

        Reservation reservation=findReservation(number);

        if(reservation!=null) {
            reservation.showReservation();
        } else {
            System.out.println("Reservation not found.");
        }
    }

    public void cancelReservation(int number,HotelService hotelService) {
        Reservation reservation=findReservation(number);

        if(reservation==null) {
            System.out.println("Reservation not found.");
            return;
        }

        if(!reservation.getStatus().equals("Active")) {
            System.out.println("Reservation cannot be cancelled.");
            return;
        }

        reservation.cancelReservation();
        reservationQueue.remove(reservation);

        HotelRoom room=reservation.getRoom();

        Bookable bookable=room;
        bookable.cancelBooking();

        room.setClean(false);
        room.setAvailability(false);

        FileManager.saveReservations(reservations);
        FileManager.saveRooms(hotelService.getRooms());

        System.out.println("Reservation cancelled successfully.Room needs cleaning.");
    }
    public void updateReservation(Scanner input,HotelService hotelService) {
        System.out.print("Enter reservation number: ");
        int number=input.nextInt();

        Reservation reservation=findReservation(number);

        if(reservation==null || !reservation.getStatus().equals("Active")) {
            System.out.println("Reservation not found or cannot be updated.");
            return;
        }

        System.out.println("\n===== UPDATE RESERVATION =====\n1. Room\n2. Number of nights\n3. Check-in date\n4. Payment amount\n5. Payment method");
        System.out.print("Choose what to update: ");
        int choice=input.nextInt();

        switch(choice) {
            case 1:
                System.out.println("===ROOMS===");
                hotelService.searchAvailableRooms();
                System.out.print("Enter new room number: ");
                int roomNumber=input.nextInt();

                HotelRoom newRoom=hotelService.findRoom(roomNumber);
                HotelRoom oldRoom=reservation.getRoom();

                if(newRoom==null || (newRoom!=oldRoom && (!newRoom.isAvailability() || !newRoom.isClean()))) {
                    System.out.println("Room not found or unavailable.");
                    return;
                }

                if(oldRoom!=newRoom) {
                    Bookable bookable=newRoom;

                    if(!bookable.bookRoom()) {
                        System.out.println("Room is not available or needs cleaning.");
                        return;
                    }

                    oldRoom.setAvailability(true);
                    reservation.setRoom(newRoom);
                }
                break;

            case 2:
                System.out.print("Enter new number of nights: ");
                int nights=input.nextInt();

                if(nights<=0) {
                    System.out.println("Invalid number of nights.");
                    return;
                }

                reservation.setNights(nights);
                break;

            case 3:
                System.out.print("Enter new check-in date: ");
                reservation.setCheckInDate(input.next());
                break;
            case 4:
                System.out.print("Enter new payment amount: ");
                double amount=input.nextDouble();

                if(amount<0) {
                    System.out.println("Invalid payment amount.");
                    return;
                }

                reservation.getPayment().setAmount(amount);
                break;

            case 5:
                System.out.print("Enter new payment method: ");
                reservation.getPayment().setMethod(input.next());
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }
        FileManager.saveReservations(reservations);
        FileManager.saveRooms(hotelService.getRooms());
        System.out.println("Reservation updated successfully.");
    }
    public void reservationMenu(Scanner input,HotelService hotelService) {
        int choice;
        do {
            System.out.println("\n===== RESERVATION MANAGEMENT =====\n1. Create reservation\n2. View all reservations\n3. Find reservation\n4. Update reservation\n5. Cancel reservation\n0. Back");
            System.out.print("Choose: ");
            choice=input.nextInt();
            switch(choice) {
                case 1:
                    createReservation(input,hotelService);
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    findReservationMenu(input);
                    break;
                case 4:
                    updateReservation(input,hotelService);
                    break;
                case 5:
                    System.out.print("Enter reservation number: ");
                    int number=input.nextInt();
                    cancelReservation(number,hotelService);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice!=0);
    }
    public void processReservation() {
        if(reservationQueue.isEmpty()) {
            System.out.println("No pending reservations.");
            return;
        }

        Reservation reservation=reservationQueue.poll();

        if(!reservation.getStatus().equals("Active")) {
            System.out.println("Reservation is no longer active.");
            return;
        }

        reservation.processReservation();
        FileManager.saveReservations(reservations);
        System.out.println("\n===== PROCESSING RESERVATION =====");
        reservation.showReservation();
        System.out.println("Reservation processed successfully.");
    }

    public void viewPendingReservations() {
        if(reservationQueue.isEmpty()) {
            System.out.println("No pending reservations.");
            return;
        }

        System.out.println("\n===== PENDING RESERVATIONS =====");

        for(Reservation reservation:reservationQueue) {
            reservation.showReservation();
        }
    }
    public void queueMenu(Scanner input) {
        int choice;

        do {
            System.out.println("\n===== RESERVATION QUEUE =====\n1. View pending reservations\n2. Process next reservation\n0. Back");
            System.out.print("Choose: ");
            choice=input.nextInt();

            switch(choice) {
                case 1:
                    viewPendingReservations();
                    break;
                case 2:
                    processReservation();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice!=0);
    }

    public void showReservationStatistics() {
        int pending=0;
        int processed=0;
        int cancelled=0;

        for(Reservation reservation:reservations) {
            if(reservation.getStatus().equals("Active")) {
                pending++;
            } else if(reservation.getStatus().equals("Processed")) {
                processed++;
            } else if(reservation.getStatus().equals("Cancelled")) {
                cancelled++;
            }
        }

        System.out.println("\n===== RESERVATION STATISTICS =====\nTotal reservations: "+reservations.size()+"\nPending reservations: "+pending+"\nProcessed reservations: "+processed+"\nCancelled reservations: "+cancelled);
    }
    public void reportsMenu(Scanner input,HotelService hotelService) {
        int choice;

        do {
            System.out.println("\n===== REPORTS =====\n1. Room Statistics\n2. Reservation Statistics\n3. Sort Rooms by Price\n4. Sort Rooms by Capacity\n5. Sort Rooms by Floor\n0. Back");
            System.out.print("Choose: ");
            choice=input.nextInt();

            switch(choice) {
                case 1:
                    hotelService.roomStatistics();
                    break;
                case 2:
                    showReservationStatistics();
                    break;
                case 3:
                    hotelService.sortRoomsByPrice();
                    break;
                case 4:
                    hotelService.sortRoomsByCapacity();
                    break;
                case 5:
                    hotelService.sortRoomsByFloor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice!=0);
    }
}


