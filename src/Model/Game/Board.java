package Model.Game;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallback;
import Utils.Position;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    private Map<Position, Tile> board;
    private Player player;
    private List<Enemy> enemies;
    private int width;
    private DeathCallback deathCallback;


    public Board() {
        deathCallback = this::onDeath;
    }

    //public void onDeath(Unit unit){
        //unit.handleDeath();
    //}

    public void onDeath(Player player){
      player.handleDeath();
    }

    public void initBoard(List<Tile> tiles, Player player, List<Enemy> enemies, int width){
        this.board = new TreeMap<>();
        for (Tile tile : tiles) {
            board.put(tile.getPosition(), tile);
        }
        this.player = player;
        this.enemies = enemies;
        this.width = width;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }

    public void tick(char userAction){
        Position playerPosition = player.getPosition();
        Position newPos = this.player.tick(userAction, this.enemies);
        newPos = this.player.interact(this.board.get(newPos));
        Tile toSwap = board.get(newPos);
        board.put(newPos, player);
        board.put(playerPosition, toSwap);
        /*for (Enemy enemy : this.enemies) {
            newPos = enemy.tick();
            enemy.interact(this.board.get(newPos));
        }
         */
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            sb.append(entry.getValue().getTile());
            if(entry.getKey().getX() == width - 1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
