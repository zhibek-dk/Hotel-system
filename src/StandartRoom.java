public class StandartRoom extends HotelRoom implements Bookable,Cleanable{
    private String bedType;
    private String viewType;

    public StandartRoom(int rn,int f,double p,int c,boolean a,String b,String v) {
        super(rn,f,p,c,a);
        this.bedType = b;
        this.viewType = v;
    }
    public void displayInfo() {
        System.out.println("\nStandard room");
        super.displayInfo();
        System.out.println("Bed type: " + bedType+"\nView type: " + viewType);
    }
    public String getBedType() {return bedType;}
    public void setBedType(String bedType) {this.bedType = bedType;}
    public String getViewType() {return viewType;}
    public void setViewType(String viewType) {this.viewType = viewType;}

    public double calculateBedUpgradeCost() {
        if (bedType.equals("King")) {
            return 30;
        } else if (bedType.equals("Queen")) {
            return 20;
        }
        return 0;
    }
    public double calculateViewFee() {
        if (viewType.equals("City")) {
            return 15;
        } else if (viewType.equals("Garden")) {
            return 10;
        }
        return 0;
    }
    public double getServiceFee(){
        double fee=10;
        if(viewType.equals("City")){
            fee+=10;
        }
        if(bedType.equals("King")){
            fee+=15;
        }
        return fee;
    }
    public double FinalPrice(int nights) {
        return getPrice() * nights + calculateBedUpgradeCost() + calculateViewFee()+getServiceFee();
    }


}
