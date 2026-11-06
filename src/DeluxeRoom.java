class DeluxeRoom extends HotelRoom implements Bookable,Cleanable{
    private String balconyType;
    private String minibarType;
    private int mBitems;

    public DeluxeRoom(int rn,int f,double p,int c,boolean a,String b,String m,int i) {
        super(rn,f,p,c,a);
        this.balconyType = b;
        this.minibarType = m;
        this.mBitems=i;
    }
    @Override
    public void displayInfo() {
        System.out.println("\nDeluxe room");
        super.displayInfo();
        System.out.println("Balcony type: " + balconyType+"\nMini bar type: " + minibarType);
    }
    public String getBalconyType() {return balconyType;}
    public void setBalconyType(String balconyType) {this.balconyType = balconyType;}
    public String getMinibarType() {return minibarType;}
    public void setMinibarType(String minibarType) {this.minibarType = minibarType;}
    public int getMBitems(){return mBitems;}
    public double calculateBalconyFee() {
        if (balconyType.equals("Private")) {
            return 80;
        } else if (balconyType.equals("Panoramic")) {
            return 50;
        }
        return 20;
    }
    public double calculateMiniBarCost(int items) {
        if (minibarType.equals("Premium")) {
            return items * 10;
        } else if (minibarType.equals("Standard")) {
            return items * 5;
        }
        return 0;
    }
    public double getServiceFee(){
        double fee=20;
        if(balconyType.equals("Panoramic")){
            fee+=20;
        }
        if(minibarType.equals("Premium")){
            fee+=15;
        }
        return fee;
    }
    public double FinalPrice(int nights){
        return getPrice()*nights+calculateBalconyFee()+calculateMiniBarCost(mBitems)+getServiceFee();
    }

}

