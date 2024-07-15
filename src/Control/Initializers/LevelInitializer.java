package Control.Initializers;
import Model.Game.Board;
import Model.Game.Level;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Wall;
import Utils.Callbacks.DeathCallbackEnemy;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LevelInitializer {
    private final Map<Character, Function<Position, Tile>> tileCreator;
    private final TileFactory tileFactory;
    private Player player;
    private final List<Enemy> enemies;
    private final Generator generator;
    private final MessageCallback messageCallback;
    private final int playerId;
    private Board board;


    public LevelInitializer(int playerId, Generator generator, MessageCallback messageCallback, Board board) {
        this.playerId = playerId;
        this.enemies = new ArrayList<>();
        this.board = board;
        tileFactory = new TileFactory();
        tileCreator = Map.of(
                '.', tileFactory::produceEmptyTile,
                '#', tileFactory::produceWall,
                '@', (pos) -> producePlayer(pos, board.getDeathCallbackPlayer())
        );
        this.generator = generator;
        //this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
    }

    private Player producePlayer(Position pos, DeathCallbackPlayer deathCallbackPlayer){
        if(this.player == null)
            this.player = tileFactory.producePlayer(pos, playerId, generator, messageCallback, deathCallbackPlayer);
        return this.player;
    }

    private Enemy produceEnemy(Position pos, char tile, DeathCallbackEnemy deathCallbackEnemy) {
        Enemy enemy = tileFactory.produceEnemy(tile, pos, generator, messageCallback, deathCallbackEnemy);
        this.enemies.add(enemy);
        return enemy;

    }

    public void initLevel(String levelPath){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Tile> tiles = new ArrayList<>();
        int y = 0;
        int x = 0;
        for(String line : lines){
            x = 0;
            for(char c : line.toCharArray()){
                tiles.add(tileCreator.getOrDefault(c, (pos) -> this.produceEnemy(pos, c, board.getDeathCallbackEnemy())).apply(new Position(x, y)));
                x++;
            }
            y++;
        }
        this.board.initBoard(tiles, this.player, this.enemies, x);
    }
}
