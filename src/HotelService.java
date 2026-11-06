
import java.util.*;

public class HotelService {

    private List<HotelRoom> rooms=FileManager.loadRooms();
    private Map<Integer,HotelRoom> roomMap=new HashMap<>();
    private Set<Guest> guests=FileManager.loadGuests();
    public HotelService() {
        setRooms(rooms);
    }
    public void setRooms(List<HotelRoom> rooms) {
        this.rooms=rooms;
        roomMap.clear();

        for(HotelRoom room:rooms) {
            roomMap.put(room.getRoomNumber(),room);
        }
    }

    public void setGuests(Set<Guest> guests) {
        this.guests=guests;
    }

    public List<HotelRoom> getRooms() {
        return rooms;
    }

    public Set<Guest> getGuests() {
        return guests;
    }
    public void addRoom(HotelRoom room) {
        if(roomMap.containsKey(room.getRoomNumber())) {
            System.out.println("Room number already exists.");
            return;
        }
        rooms.add(room);
        roomMap.put(room.getRoomNumber(),room);
        System.out.println("Room added successfully.");
    }

    public void addRoomMenu(Scanner input) {
        System.out.println("\n===== ADD ROOM =====\n1. Standard Room\n2. Deluxe Room\n3. Suite Room");
        System.out.print("Choose room type: ");
        int type=input.nextInt();

        System.out.print("Enter room number: ");
        int number=input.nextInt();

        System.out.print("Enter floor: ");
        int floor=input.nextInt();

        System.out.print("Enter price: ");
        double price=input.nextDouble();

        System.out.print("Enter capacity: ");
        int capacity=input.nextInt();

        HotelRoom room;

        switch(type) {
            case 1:
                System.out.print("Enter bed type: ");
                String bed=input.next();

                System.out.print("Enter view: ");
                String view=input.next();

                room=new StandartRoom(number,floor,price,capacity,true,bed,view);
                break;

            case 2:
                System.out.print("Enter balcony type: ");
                String balcony=input.next();

                System.out.print("Enter minibar type: ");
                String minibar=input.next();

                System.out.print("Enter minibar items: ");
                int items=input.nextInt();

                room=new DeluxeRoom(number,floor,price,capacity,true,balcony,minibar,items);
                break;

            case 3:
                System.out.print("Enter kitchen type: ");
                String kitchen=input.next();

                System.out.print("Enter living room type: ");
                String living=input.next();

                room=new SuiteRoom(number,floor,price,capacity,true,kitchen,living);
                break;

            default:
                System.out.println("Invalid room type.");
                return;
        }

        addRoom(room);
    }

    public void viewRooms() {
        if(rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for(HotelRoom room:rooms) {
            room.displayInfo();
        }
    }

    public HotelRoom findRoom(int number) {
        return roomMap.get(number);

    }

    public void removeRoom(int number) {
        HotelRoom room=findRoom(number);

        if(room==null) {
            System.out.println("Room not found.");
            return;
        }

        rooms.remove(room);
        roomMap.remove(number);

        FileManager.saveRooms(rooms);

        System.out.println("Room removed successfully.");
    }

    public void updateRoom(Scanner input) {
        System.out.print("Enter room number: ");
        int number=input.nextInt();

        HotelRoom room=findRoom(number);

        if(room==null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("\n===== UPDATE ROOM =====\n1. Room number\n2. Floor\n3. Price\n4. Capacity\n5. Availability");
        System.out.print("Choose what to update: ");
        int choice=input.nextInt();

        switch(choice) {
            case 1:
                System.out.print("Enter new room number: ");
                int newNumber=input.nextInt();

                if(roomMap.containsKey(newNumber)) {
                    System.out.println("This room number already exists.");
                    return;
                }

                roomMap.remove(number);
                room.setRoomNumber(newNumber);
                roomMap.put(newNumber,room);
                break;

            case 2:
                System.out.print("Enter new floor: ");
                room.setFloor(input.nextInt());
                break;

            case 3:
                System.out.print("Enter new price: ");
                room.setPrice(input.nextDouble());
                break;

            case 4:
                System.out.print("Enter new capacity: ");
                room.setCapacity(input.nextInt());
                break;

            case 5:
                System.out.print("Available? (true/false): ");
                room.setAvailability(input.nextBoolean());
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }
        FileManager.saveRooms(rooms);
        System.out.println("Room updated successfully.");
    }

    public void searchAvailableRooms() {
        boolean found=false;

        for(HotelRoom room:rooms) {
            if(room.isAvailability()&&room.isClean()) {
                room.displayInfo();
                found=true;
            }
        }

        if(!found) {
            System.out.println("No available rooms.");
        }
    }

    public void roomStatistics() {
        int available=0;
        int occupied=0;
        int cleaning=0;

        for(HotelRoom room:rooms) {
            if(!room.isClean()) {
                cleaning++;}
            else if(room.isAvailability()) {
                available++;
            } else {
                occupied++;
            }
        }

        System.out.println("\n===== ROOM STATISTICS =====\nTotal rooms: "+rooms.size()+"\nAvailable rooms: "+available+"\nOccupied rooms: "+occupied+"\nRooms requiring cleaning: "+cleaning);
    }
    public void roomMenu(Scanner input) {
        int choice;
        do {
            System.out.println("\n===== ROOM MANAGEMENT =====\n1. Add room\n2. View rooms\n3. Find room\n4. Update room\n5. Delete room\n6. Search available rooms\n7. Clean room\n0. Back");
            System.out.print("Choose: ");
            choice=input.nextInt();

            switch(choice) {
                case 1:
                    addRoomMenu(input);
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    System.out.print("Enter room number: ");
                    int number=input.nextInt();
                    HotelRoom room=findRoom(number);

                    if(room!=null) {
                        room.displayInfo();
                    } else {
                        System.out.println("Room not found.");
                    }
                    break;
                case 4:
                    updateRoom(input);
                    break;
                case 5:
                    System.out.print("Enter room number: ");
                    int roomNumber=input.nextInt();
                    removeRoom(roomNumber);
                    break;
                case 6:
                    searchAvailableRooms();
                    break;
                case 7:
                    System.out.print("Enter room number to clean: ");
                    int roomNumberC=input.nextInt();
                    cleanRoom(roomNumberC);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice!=0);
    }
    public void sortRoomsByPrice() {
        rooms.sort(Comparator.comparingDouble(HotelRoom::getPrice));
        viewRooms();
    }

    public void sortRoomsByCapacity() {
        rooms.sort(Comparator.comparingInt(HotelRoom::getCapacity));
        viewRooms();
    }

    public void sortRoomsByFloor() {
        rooms.sort(Comparator.comparingInt(HotelRoom::getFloor));
        viewRooms();
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
        FileManager.saveGuests(guests);
    }
    public void cleanRoom(int roomNumber) {
        HotelRoom room=findRoom(roomNumber);

        if(room==null) {
            System.out.println("Room not found.");
            return;
        }
        Cleanable cleanable=room;
        cleanable.cleanRoom();

        FileManager.saveRooms(rooms);
    }
}

