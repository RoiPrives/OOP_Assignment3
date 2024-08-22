package Model.Game;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallbackEnemy;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Position;

import java.util.*;

public class Board {
    private Map<Position, Tile> board;
    private Player player;
    private List<Enemy> enemies;
    private int width;
    private final DeathCallbackPlayer deathCallbackPlayer;
    private final DeathCallbackEnemy deathCallbackEnemy;
    private boolean over = false;


    public Board() {
        deathCallbackPlayer = this::playerDeath;
        deathCallbackEnemy = this::enemyDeath;
    }

    public void playerDeath(Player myPlayer){
        this.player.setChar('X');
        over = true;
    }

    public void enemyDeath(Enemy enemy){
        board.put(enemy.getPosition(), new EmptyTile().initialize(this.player.getPosition()));
        enemies.remove(enemy);
    }

    public DeathCallbackPlayer getDeathCallbackPlayer(){
        return deathCallbackPlayer;
    }

    public DeathCallbackEnemy getDeathCallbackEnemy(){
        return deathCallbackEnemy;
    }

    public void initBoard(List<Tile> tiles, Player player, List<Enemy> enemies, int width){
        this.board = new TreeMap<>();
        for (Tile tile : tiles) {
            board.put(new Position(tile.getPosition()), tile);
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

    public boolean tick(char userAction){
        playerTick(userAction);
        for (int i = 0; i < enemies.size() && !over; i++) {
            enemyTick(enemies.get(i));
        }
        return over;
    }

    public void playerTick(char userAction){
        Position playerPosition = new Position(player.getPosition());
        Position newPos = this.player.tick(userAction, new ArrayList<>(this.enemies));
        Position finalPos = this.player.interact(this.board.get(newPos));
        board.put(playerPosition, board.get(finalPos));
        board.put(finalPos, this.player);
    }

    public void enemyTick(Enemy enemy){
        Position enemyPos = new Position(enemy.getPosition());
        Position newPos = enemy.tick(this.player);
        Position finalPos = enemy.interact(this.board.get(newPos));
        board.put(enemyPos, board.get(finalPos));
        board.put(finalPos, enemy);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < this.board.size() / width; y++){
            for(int x = 0; x < width; x++){
                Tile tile = this.board.get(new Position(x, y));
                sb.append(board.get(new Position(x, y)).getTile());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
