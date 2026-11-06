class SuiteRoom extends HotelRoom implements Bookable,Cleanable{
    private String kitchenType;
    private String livingRoomType;

    public SuiteRoom(int rn,int f,double p,int c,boolean a,String k,String l) {
        super(rn,f,p,c,a);
        this.kitchenType = k;
        this.livingRoomType = l;
    }
    @Override
    public void displayInfo() {
        System.out.println("\nSuite room");
        super.displayInfo();
        System.out.println("Kitchen type: " + kitchenType+"\nLiving room type: " + livingRoomType);
    }
    public String getKitchenType() {return kitchenType;}
    public void setKitchenType(String kitchenType) {this.kitchenType = kitchenType;}
    public String getLivingRoomType() {return livingRoomType;}
    public void setLivingRoomType(String livingRoomType) {this.livingRoomType = livingRoomType;}
    public double calculateKitchenFee() {
        if (kitchenType.equals("Chef")) {
            return 100;
        } else if (kitchenType.equals("Luxury")) {return 60;}
        return 30;
    }
    public double calculateLivingRoomFee() {
        if (livingRoomType.equals("VIP")) {
            return 100;
        } else if (livingRoomType.equals("Luxury")) {return 70;}
        return 40;
    }
    public double getServiceFee(){
        double fee=30;
        if(kitchenType.equals("Luxury")){
            fee+=30;
        }
        if(livingRoomType.equals("VIP")){
            fee+=40;
        }
        return fee;
    }
    public double FinalPrice(int nights) {
        return getPrice()*nights+ calculateKitchenFee() + calculateLivingRoomFee()+getServiceFee();
    }
}

