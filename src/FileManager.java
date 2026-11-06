
import java.io.*;
import java.util.*;

public class FileManager {
    private static final String roomsFile="C:\\Users\\Zhibek\\IdeaProjects\\Hotel\\src\\rooms.txt";
    private static final String guestsFile="C:\\Users\\Zhibek\\IdeaProjects\\Hotel\\src\\guests.txt";
    private static final String reservationsFile="C:\\Users\\Zhibek\\IdeaProjects\\Hotel\\src\\reservations.txt";

    public static void saveGuests(Set<Guest> guests) {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(guestsFile))) {
            for(Guest guest:guests) {
                writer.write(guest.getId()+","+guest.getName()+","+guest.getPhone());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error saving guests: "+e.getMessage());
        }
    }

    public static Set<Guest> loadGuests() {
        Set<Guest> guests=new HashSet<>();

        try(BufferedReader reader=new BufferedReader(new FileReader(guestsFile
        ))) {
            String line;
            while((line=reader.readLine())!=null) {
                String[] data=line.split(",");
                guests.add(new Guest(Integer.parseInt(data[0]),data[1],data[2]));
            }
        } catch(FileNotFoundException e) {
            System.out.println("No saved guests found.");
        } catch(IOException | NumberFormatException e) {
            System.out.println("Error loading guests: "+e.getMessage());
        }

        return guests;
    }

    public static void saveRooms(List<HotelRoom> rooms) {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(roomsFile))) {
            for(HotelRoom room:rooms) {
                String line=room.getClass().getSimpleName()+","+ room.getRoomNumber()+","+ room.getFloor()+","+ room.getPrice()+","+ room.getCapacity()+","+room.isAvailability();

                if(room instanceof StandartRoom) {
                    StandartRoom r=(StandartRoom)room;
                    line+=","+r.getBedType()+","+r.getViewType();
                } else if(room instanceof DeluxeRoom) {
                    DeluxeRoom r=(DeluxeRoom)room;
                    line+=","+r.getBalconyType()+","+r.getMinibarType()+","+r.getMBitems();
                } else if(room instanceof SuiteRoom) {
                    SuiteRoom r=(SuiteRoom)room;
                    line+=","+r.getKitchenType()+","+r.getLivingRoomType();
                }

                writer.write(line);
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error saving rooms: "+e.getMessage());
        }
    }

    public static List<HotelRoom> loadRooms() {
        List<HotelRoom> rooms=new ArrayList<>();

        try(BufferedReader reader=new BufferedReader(new FileReader(roomsFile))) {
            String line;

            while((line=reader.readLine())!=null) {
                String[] data=line.split(",",-1);

                int roomNumber=Integer.parseInt(data[1]);
                int floor=Integer.parseInt(data[2]);
                double price=Double.parseDouble(data[3]);
                int capacity=Integer.parseInt(data[4]);
                boolean availability=Boolean.parseBoolean(data[5]);

                HotelRoom room=null;

                if(data[0].equals("StandartRoom")) {
                    room=new StandartRoom(roomNumber,floor,price,capacity,availability,data[6],data[7]);
                } else if(data[0].equals("DeluxeRoom")) {
                    room=new DeluxeRoom(roomNumber,floor,price,capacity,availability,data[6],data[7],Integer.parseInt(data[8]));
                } else if(data[0].equals("SuiteRoom")) {
                    room=new SuiteRoom(roomNumber,floor,price,capacity,availability,data[6],data[7]);
                }

                if(room!=null) {
                    rooms.add(room);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("No saved rooms found.");
        } catch(IOException | NumberFormatException e) {
            System.out.println("Error loading rooms: "+e.getMessage());
        }

        return rooms;
    }
    public static void saveReservations(List<Reservation> reservations) {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(reservationsFile))) {
            for(Reservation r:reservations) {
                writer.write(r.getId()+","+r.getGuest().getId()+","+r.getRoom().getRoomNumber()+","+r.getNights()+","+r.getCheckInDate()+","+r.getPayment().getAmount()+","+r.getPayment().getMethod()+","+r.getStatus());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error saving reservations: "+e.getMessage());
        }
    }

    public static List<Reservation> loadReservations(Set<Guest> guests,List<HotelRoom> rooms) {
        List<Reservation> reservations=new ArrayList<>();

        try(BufferedReader reader=new BufferedReader(new FileReader(reservationsFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);
                int id = Integer.parseInt(data[0]);
                int guestId = Integer.parseInt(data[1]);
                int roomNumber = Integer.parseInt(data[2]);
                int nights = Integer.parseInt(data[3]);

                Guest guest = null;
                HotelRoom room = null;
                for (Guest g : guests) {
                    if (g.getId() == guestId) {
                        guest = g;
                        break;
                    }
                }
                for (HotelRoom r : rooms) {
                    if (r.getRoomNumber() == roomNumber) {
                        room = r;
                        break;
                    }
                }

                if (guest == null) {
                    System.out.println("Skipped reservation " + id + ": guest " + guestId + " not found");
                } else if (room == null) {
                    System.out.println("Skipped reservation " + id + ": room " + roomNumber + " not found");
                } else {
                    Payment payment = new Payment(Double.parseDouble(data[5]), data[6]);
                    reservations.add(new Reservation(id, guest, room, nights, payment, data[4], data[7]));
                }
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return reservations;
    }}
