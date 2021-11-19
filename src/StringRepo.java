import java.io.Console;

public class StringRepo {
    private String errorMessage;

    public StringRepo(){

    }
    public StringRepo(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String  messageError(){
        String input = "Expecting an integer Value received invalid input";
        return input;
    }
    public String messageInfo(){
        String info = "Please enter the number of tickets you want:";
        return info;
    }
    public String menuMessage(){
        String menu = "To return to log in menu enter 7";
        return menu;
    }
    public String startMenuMessage(){
        String startMenu = "To return to Main Menu enter 8";
        return startMenu;
    }
    public String returningMainMenu(){
        String mainMenu ="Refreshing......Returning to Main Menu....";
        return mainMenu;
    }

}
