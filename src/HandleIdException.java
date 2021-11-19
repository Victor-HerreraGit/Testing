public class HandleIdException extends Exception{

    private String message;



    public HandleIdException(String message1) {

        this.message = message1;
    }
    public String getMessage() {
        return message;
    }
    /**
     * Method Provided by Victor Herrera
     * Method is designed to check the event ids.
     * User enters invalid Id exception is handled.
     * @return a
     * return valid Id type.
     * **/

    public int eventId(int a) throws HandleIdException {

        if (a<1||a >71) {
            throw new HandleIdException("Id Entered does not exist Enter Valid Id");
        } else {
            return a;
        }
    }
    public int numTicketsPurchase(int b) throws HandleIdException{
        if(b<2||b>6){
            throw new HandleIdException("You can only purchase 2 to 6 tickets");
        }else{
            return b;
        }
    }
    public int checkInput(int a) throws HandleIdException{
       if(((Object)a).getClass().getSimpleName()!=((Object)a).getClass().getSimpleName()){

           System.out.println("error");
       }
       return 0;
    }
    /**
     * Method provided by Victor Herrera
     * Throws exception when an input exceeds designated number.
     * @return a
     * returns the value of when correct.
     *
     * **/
    public int checkMultipleUsers(int a)throws HandleIdException{
        if (a<1||a >10) {
            throw new HandleIdException("Exceeds number of users at once.");
        } else {
            return a;
        }
    }

}
