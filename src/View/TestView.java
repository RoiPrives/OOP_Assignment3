package View;

import java.util.ArrayList;

public class TestView extends View{
    ArrayList<Character> actions;
    int i;
    public TestView(ArrayList<Character> actions) {
        this.actions = actions;
        i = -1;
    }
    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput(){
        i++;
        return actions.get(i).toString();
    }

}
