package Control.Initializers;
import Model.Game.Board;
import Model.Game.Level;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Wall;
import Utils.Callbacks.DeathCallback;
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


    public LevelInitializer(int playerId, Generator generator, MessageCallback messageCallback) {
        this.playerId = playerId;
        this.enemies = new ArrayList<>();
        tileFactory = new TileFactory();
        tileCreator = Map.of(
                '.', tileFactory::produceEmptyTile,
                '#', tileFactory::produceWall,
                '@', this::producePlayer
        );
        this.generator = generator;
        //this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
    }

    private Player producePlayer(Position pos){
        if(this.player == null)
            this.player = tileFactory.producePlayer(pos, playerId, generator, messageCallback);
        return this.player;
    }

    private Enemy produceEnemy(Position pos, char tile){
        Enemy enemy = tileFactory.produceEnemy(tile, pos, generator, messageCallback);
        this.enemies.add(enemy);
        return enemy;

    }


    public void initLevel(String levelPath, Board board){
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
                tiles.add(tileCreator.getOrDefault(c, (pos) -> this.produceEnemy(pos, c)).apply(new Position(x, y)));
                x++;
            }
            y++;
        }
        board.initBoard(tiles, this.player, this.enemies, x);
    }
}
