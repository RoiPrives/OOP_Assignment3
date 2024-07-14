package View;
import java.util.Scanner;



public class CLI extends View{
    Scanner scanner;

    public CLI(){
        scanner = new Scanner(System.in);
    }
    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

}
