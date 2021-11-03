public abstract class Venue{
    /**Attributes**/
    private String nameAvenue;
    private String nameType;
    private int pctSeats;
    private int capacity;
    private double cost;
    private int vipPct;
    private int goldPct;
    private int silverPct;
    private int bronzePct;
    private int generalPct;
    private int reservedPct;
    private String fireWorks;
    private double fireWorksCost;

    private int totalSeatsVip; //Total seats
    private int totalSeatsGold; //Total seats
    private int totalSeatsSilver; //Total seats
    private int totalSeatsBronze; //Total seats
    private int totalSeatsGeneral; //Total seats

    private double totalVipRevenue;
    private double totalGoldRevenue;
    private double totalSilverRevenue;
    private double totalBronzeRevenue;
    private double totalGeneralRevenue;

    //Constructors
    public Venue(){

    }

    public Venue(String nameAvenueIn, String nameTypeIn, int pctSeatsIn, int capacityIn, double costIn, int vipPctIn,
                 int goldPctIn, int silverPctIn, int bronzePctIn, int generalPctIn, int reservedPctIn, String fireWorksIn, double fireWorksCostIn) {
        this.nameAvenue = nameAvenueIn;
        this.nameType = nameTypeIn;
        this.pctSeats = pctSeatsIn;
        this.capacity = capacityIn;
        this.cost = costIn;
        this.vipPct = vipPctIn;
        this.goldPct = goldPctIn;
        this.silverPct = silverPctIn;
        this.bronzePct = bronzePctIn;
        this.generalPct = generalPctIn;
        this.reservedPct = reservedPctIn;
        this.fireWorks = fireWorksIn;
        this.fireWorksCost = fireWorksCostIn;
    }
    public Venue(String nameAvenueIn, String nameTypeIn, int pctSeatsIn, int capacityIn, double costIn, int vipPctIn,
                 int goldPctIn, int silverPctIn, int bronzePctIn, int generalPctIn, int reservedPctIn) {
        this.nameAvenue = nameAvenueIn;
        this.nameType = nameTypeIn;
        this.pctSeats = pctSeatsIn;
        this.capacity = capacityIn;
        this.cost = costIn;
        this.vipPct = vipPctIn;
        this.goldPct = goldPctIn;
        this.silverPct = silverPctIn;
        this.bronzePct = bronzePctIn;
        this.generalPct = generalPctIn;
        this.reservedPct = reservedPctIn;
    }
    //Setters and Getters
    public String getNameAvenue() {
        return nameAvenue;
    }

    public void setNameAvenue(String nameAvenue) {
        this.nameAvenue = nameAvenue;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getPctSeats() {
        return pctSeats;
    }

    public void setPctSeats(int pctSeats) {
        this.pctSeats = pctSeats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getVipPct() {
        return vipPct;
    }

    public void setVipPct(int vipPct) {
        this.vipPct = vipPct;
    }

    public int getGoldPct() {
        return goldPct;
    }

    public void setGoldPct(int goldPct) {
        this.goldPct = goldPct;
    }

    public int getSilverPct() {
        return silverPct;
    }

    public void setSilverPct(int silverPct) {
        this.silverPct = silverPct;
    }

    public int getBronzePct() {
        return bronzePct;
    }

    public void setBronzePct(int bronzePct) {
        this.bronzePct = bronzePct;
    }

    public int getGeneralPct() {
        return generalPct;
    }

    public void setGeneralPct(int generalPct) {
        this.generalPct = generalPct;
    }

    public int getReservedPct() {
        return reservedPct;
    }

    public void setReservedPct(int reservedPct) {
        this.reservedPct = reservedPct;
    }

    public String getFireWorks() {
        return fireWorks;
    }

    public void setFireWorks(String fireWorks) {
        this.fireWorks = fireWorks;
    }

    public double getFireWorksCost() {
        return fireWorksCost;
    }

    public void setFireWorksCost(double fireWorksCost) {
        this.fireWorksCost = fireWorksCost;
    }

    public int getTotalSeatsVip() {
        return totalSeatsVip;
    }

    public void setTotalSeatsVip(int totalSeatsVip) {
        this.totalSeatsVip = totalSeatsVip;
    }

    public int getTotalSeatsGold() {
        return totalSeatsGold;
    }

    public void setTotalSeatsGold(int totalSeatsGold) {
        this.totalSeatsGold = totalSeatsGold;
    }

    public int getTotalSeatsSilver() {
        return totalSeatsSilver;
    }

    public void setTotalSeatsSilver(int totalSeatsSilver) {
        this.totalSeatsSilver = totalSeatsSilver;
    }

    public int getTotalSeatsBronze() {
        return totalSeatsBronze;
    }

    public void setTotalSeatsBronze(int totalSeatsBronze) {
        this.totalSeatsBronze = totalSeatsBronze;
    }

    public int getTotalSeatsGeneral() {
        return totalSeatsGeneral;
    }

    public void setTotalSeatsGeneral(int totalSeatsGeneral) {
        this.totalSeatsGeneral = totalSeatsGeneral;
    }

    public double getTotalVipRevenue() {
        return totalVipRevenue;
    }

    public void setTotalVipRevenue(double totalVipRevenue) {
        this.totalVipRevenue = totalVipRevenue;
    }

    public double getTotalGoldRevenue() {
        return totalGoldRevenue;
    }

    public void setTotalGoldRevenue(double totalGoldRevenue) {
        this.totalGoldRevenue = totalGoldRevenue;
    }

    public double getTotalSilverRevenue() {
        return totalSilverRevenue;
    }

    public void setTotalSilverRevenue(double totalSilverRevenue) {
        this.totalSilverRevenue = totalSilverRevenue;
    }

    public double getTotalBronzeRevenue() {
        return totalBronzeRevenue;
    }

    public void setTotalBronzeRevenue(double totalBronzeRevenue) {
        this.totalBronzeRevenue = totalBronzeRevenue;
    }

    public double getTotalGeneralRevenue() {
        return totalGeneralRevenue;
    }

    public void setTotalGeneralRevenue(double totalGeneralRevenue) {
        this.totalGeneralRevenue = totalGeneralRevenue;
    }

    //Methods
    /**
     * Method that updates the total vip seats after a reservation.
     * @param numSeats total number of seats remain.
     */
    public void subVipPct(int numSeats){ //Remember pct means seats
        vipPct-=numSeats;
    }

    /**
     * Method that updates the total gold seats after a reservation.
     * @param numSeats total number of seats remain.
     */
    public void subGoldPct(int numSeats){
        goldPct-=numSeats;
    }

    /**
     * Method that updates the total silver seats after a reservation.
     * @param numSeats total number of seats remain.
     */
    public void subSilverPct(int numSeats){
        silverPct-=numSeats;
    }

    /**
     * Method that updates the total bronze seats after a reservation.
     * @param numSeats total number of seats remain.
     */
    public void subBronzePct(int numSeats){
        bronzePct-=numSeats;
    }

    /**
     * Method that updates the total general seats after a reservation.
     * @param numSeats total number of seats remain.
     */
    public void subGeneralPct(int numSeats){
        generalPct-=numSeats;
    }

    /**
     * Method that calculates the total of vip seats that were sold.
     * @param numSeats total of seats sold.
     */
    public void totalSeatsVipSold(int numSeats){
        totalSeatsVip+=numSeats;
    }

    /**
     * Method that calculates the total of gold seats that were sold.
     * @param numSeats total of seats sold.
     */
    public void totalSeatsGoldSold(int numSeats){
        totalSeatsGold+=numSeats;
    }

    /**
     * Method that calculates the total of silver seats that were sold.
     * @param numSeats total of seats sold.
     */
    public void totalSeatsSilverSold(int numSeats){
        totalSeatsSilver+=numSeats;
    }

    /**
     * Method that calculates the total of bronze seats that were sold.
     * @param numSeats total of seats sold.
     */
    public void totalSeatsBronzeSold(int numSeats){
        totalSeatsBronze+=numSeats;
    }

    /**
     * Method that calculates the total of general seats that were sold.
     * @param numSeats total of seats sold.
     */
    public void totalSeatsGeneralSold(int numSeats){
        totalSeatsGeneral+=numSeats;
    }

    /**
     * Method that calculates the total of seats that were sold for a specific event.
     * @return sum of total seats that were sold.
     */
    public int totalSeatsSoldEvent(){
        return totalSeatsVip+totalSeatsGold+totalSeatsSilver+totalSeatsBronze+totalSeatsGeneral;
    }

    /**
     * Method that calculates the total of money that was recollected for vip tickets in a specific event.
     * @param numSeats Number of seats that were sold.
     * @param price vip seat price.
     */
    public void totalVipRevenue(int numSeats,double price){
        double total = numSeats*price;
        totalVipRevenue+=total;
    }

    /**
     * Method that calculates the total of money that was recollected for gold tickets in a specific event.
     * @param numSeats Number of seats that were sold.
     * @param price gold seat price.
     */
    public void totalGoldRevenue(int numSeats,double price){
        double total = numSeats*price;
        totalGoldRevenue+=total;
    }

    /**
     * Method that calculates the total of money that was recollected for silver tickets in a specific event.
     * @param numSeats Number of seats that were sold.
     * @param price silver seat price.
     */
    public void totalSilverRevenue(int numSeats,double price){
        double total = numSeats*price;
        totalSilverRevenue+=total;
    }

    /**
     * Method that calculates the total of money that was recollected for bronze tickets in a specific event.
     * @param numSeats Number of seats that were sold.
     * @param price bronze seat price.
     */
    public void totalBronzeRevenue(int numSeats,double price){
        double total = numSeats*price;
        totalBronzeRevenue+=total;
    }

    /**
     * Method that calculates the total of money that was recollected for general tickets in a specific event.
     * @param numSeats Number of seats that were sold.
     * @param price general seat price.
     */
    public void totalGeneralRevenue(int numSeats,double price){
        double total = numSeats*price;
        totalGeneralRevenue+=total;
    }

    /**
     * Method that calculates the actual profit of the event.
     * @return the actual profit number of the event.
     */
    public double actualProfit(){
        return getCost()-(totalVipRevenue+totalGeneralRevenue+totalSilverRevenue+totalBronzeRevenue+totalGeneralRevenue);
    }
    //toString
    @Override
    public String toString() {
        return "Venue{" +
                "nameAvenue='" + nameAvenue + '\'' +
                ", nameType='" + nameType + '\'' +
                ", pctSeats=" + pctSeats +
                ", capacity=" + capacity +
                ", cost=" + cost +
                ", vipPct=" + vipPct +
                ", goldPct=" + goldPct +
                ", silverPct=" + silverPct +
                ", bronzePct=" + bronzePct +
                ", generalPct=" + generalPct +
                ", reservedPct=" + reservedPct +
                ", fireWorks='" + fireWorks + '\'' +
                ", fireWorksCost=" + fireWorksCost +
                '}';
    }
}
