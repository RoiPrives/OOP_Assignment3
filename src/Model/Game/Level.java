package Model.Game;
import Control.Initializers.LevelInitializer;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Utils.Callbacks.DeathCallback;
import Utils.Callbacks.InputCallback;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;

public class Level {
    private final LevelInitializer levelInitializer;
    private final String levelPath;
    private final Board board;
    private final MessageCallback messageCallback;
    private final InputCallback inputCallback;

    public Level(int playerId, String levelPath, Generator generator, MessageCallback messageCallback, InputCallback inputCallback){
        this.messageCallback = messageCallback;
        levelInitializer = new LevelInitializer(playerId, generator, messageCallback);
        this.board = new Board();
        this.levelPath = levelPath;
        this.inputCallback = inputCallback;
    }

    public void initLevel(){
        levelInitializer.initLevel(levelPath, board);
    }

    public void start(){
        /*messageCallback.send("Player: " + board.getPlayer().toString());
        messageCallback.send("\nEnemies:");
        for(Enemy enemy: board.getEnemies()){
            messageCallback.send(enemy.toString());
        }
        messageCallback.send("\nBoard:");
        messageCallback.send(board.toString());
         */
        messageCallback.send("\nBoard:");
        messageCallback.send(board.toString());

        for (int i = 0; i < 15; i++) {
            this.board.tick(inputCallback.recieve().charAt(0));
            messageCallback.send("Player: " + board.getPlayer().toString());
            messageCallback.send("\nBoard:");
            messageCallback.send(board.toString());
        }
    }
}
