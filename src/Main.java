import Model.Game.Game;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Utils.Generators.FixedGenerator;
import View.CLI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI();
        Game game = new Game( "C:/Users/Liele/Documents/אוניברסיטת בן גוריון/OOB/OOP_Assignment3/levels_dir", new FixedGenerator(), cli.getMessageCallback(), cli.getInputCallback());
        game.runGame();
        //Game game = new Game(args[0], new FixedGenerator(), cli.getMessageCallback(), cli.getInputCallback());
        //game.runGame();
    }
}