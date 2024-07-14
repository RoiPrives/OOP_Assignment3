import Model.Game.Game;
import Utils.Generators.FixedGenerator;
import View.CLI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI();
        Game game = new Game(args[0], new FixedGenerator(), cli.getMessageCallback(), cli.getInputCallback());
        game.runGame();
    }
}