import Model.Game.Game;
import Utils.Generators.FixedGenerator;
import View.View;
import View.CLI;

import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<Character> actions = new ArrayList<>(Arrays.asList('3', 'd', 's', 'd', 'd', 's'));
        View view = new CLI();
        Game game = new Game("C:\\Users\\roipr\\OneDrive\\Documents\\UNI\\Semester B\\OOB\\OOP_Assignment3\\levels_dir", new FixedGenerator(), view.getMessageCallback(), view.getInputCallback());
        game.runGame();
    }
}