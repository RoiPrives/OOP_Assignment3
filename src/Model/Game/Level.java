package Model.Game;
import Control.Initializers.LevelInitializer;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
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
        this.board = new Board();
        levelInitializer = new LevelInitializer(playerId, generator, messageCallback, board);
        this.levelPath = levelPath;
        this.inputCallback = inputCallback;
    }

    public void initLevel(){
        levelInitializer.initLevel(levelPath);
    }

    public boolean start(){
        messageCallback.send(board.toString());
        boolean playerDied = false;
        while(!playerDied && !board.getEnemies().isEmpty()) {
            messageCallback.send(board.getPlayer().toString());
            playerDied = this.board.tick(inputCallback.recieve().charAt(0));
            messageCallback.send(board.toString());
        }
        return !playerDied;
    }

    public String getBoard(){
        return board.toString();
    }
}
