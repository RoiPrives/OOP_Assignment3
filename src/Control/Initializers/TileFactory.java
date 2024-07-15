package Control.Initializers;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Enemies.Monster;
import Model.Tiles.Units.Enemies.Trap;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Wall;
import Utils.Callbacks.DeathCallbackEnemy;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;
import java.util.Arrays;
import Model.Tiles.Units.Players.Warrior;
import Model.Tiles.Units.Players.Mage;
import Model.Tiles.Units.Players.Rogue;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TileFactory {
    private Player player;

    private static final List<Supplier<Player>> playerTypes = Arrays.asList(
            () -> new Warrior("Jon Snow", 300, 99999999, 4, 3),
            () -> new Warrior("The Hound", 400, 20, 6, 5),
            () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6),
            () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4),
            () -> new Rogue("Arya Stark", 150, 40, 2, 20),
            () -> new Rogue("Bronn", 250, 35, 3, 50)
    );

    private static final Map<Character, Supplier<Enemy>> enemyTypes = Map.ofEntries(
            Map.entry('s', () -> new Monster('s', "Lannister Soldier", 80, 5000, 3, 3, 25)),
            Map.entry('k', () -> new Monster('k', "Lannister Knight", 200, 14, 8, 4, 50)),
            Map.entry('q', () -> new Monster('q', "Queen's Guard", 400, 20, 15, 5, 100)),
            Map.entry('z', () -> new Monster('z', "Wight", 600, 30, 15, 3, 100)),
            Map.entry('b', () -> new Monster('b', "Bear-Wight", 1000, 75, 30, 4, 250)),
            Map.entry('g', () -> new Monster('g', "Giant-Wight", 1500, 100, 40, 5, 500)),
            Map.entry('w', () -> new Monster('w', "White Walker", 2000, 150, 50, 6, 1000)),
            Map.entry('M', () -> new Monster('M', "The Mountain", 1000, 60, 25, 6, 500)),
            Map.entry('C', () -> new Monster('C', "Queen Cersei", 100, 10, 10, 1, 1000)),
            Map.entry('K', () -> new Monster('K', "Night’s King", 5000, 300, 150, 8, 5000)),
            Map.entry('B', () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250, 1, 5)),
            Map.entry('Q', () -> new Trap('Q', "Queen’s Trap", 250, 50, 10, 100, 3, 7)),
            Map.entry('D', () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10))
    );

    public TileFactory(){
    }

    public Player producePlayer(Position pos, int playerId, Generator generator, MessageCallback messageCallback, DeathCallbackPlayer deathCallbackPlayer){
        this.player = playerTypes.get(playerId - 1).get();
        this.player.initialize(pos, generator, deathCallbackPlayer, messageCallback);
        return this.player;
    }

    public Player producePlayer(){
        return this.player;
    }

    public Enemy produceEnemy(char tile, Position position, Generator generator, MessageCallback messageCallback, DeathCallbackEnemy deathCallbackEnemy){
        Enemy enemy = enemyTypes.get(tile).get();
        enemy.initialize(position, generator, deathCallbackEnemy, messageCallback);
        return enemy;
    }

    public Tile produceEmptyTile(Position position){
        return new EmptyTile().initialize(position);
    }

    public Tile produceWall(Position position){
        return new Wall().initialize(position);
    }

}
