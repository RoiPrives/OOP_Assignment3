package Model.Game;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallbackEnemy;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    //public void onDeath(Unit unit){
        //unit.handleDeath();
    //}

    public void playerDeath(Player myPlayer){
        System.out.println("Player death " + myPlayer.toString());
        over = true;
    }

    public void enemyDeath(Enemy enemy){
        System.out.println("Enemy death " + enemy.toString());
        board.put(enemy.getPosition(), new EmptyTile().initialize(enemy.getPosition()));
        enemies.remove(enemy);
    }

    public DeathCallbackPlayer getDeathCallbackPlayer(){
        return deathCallbackPlayer;
    }

    public DeathCallbackEnemy getDeathCallbackEnemy(){
        return deathCallbackEnemy;
    }

    public void initBoard(List<Tile> tiles, Player player, List<Enemy> enemies, int width){
        this.board = new HashMap<>();
        for (Tile tile : tiles) {
            board.put(tile.getPosition(), tile);
            //board.put(new Position(tile.getPosition()), tile);
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
        Position playerPosition = new Position(player.getPosition().getX(), player.getPosition().getY());
        //board.remove(playerPosition);
        //this.player.getPosition().setPosition(new Position(1000, 1000));
        //this.enemies.get(0).getPosition().setPosition(new Position(1000, 1000));
        Position newPos = this.player.tick(userAction, this.enemies);
        board.remove(playerPosition);
        Tile removed = board.remove(newPos);
        Tile newTile = this.board.get(newPos);
        Position finalPos = this.player.interact(newTile);
        board.put(finalPos, player);
        if(!newPos.equals(finalPos))
            board.put(newPos, removed);
        else
            board.put(finalPos, newTile);
        //Tile tileInNewPos = this.board.get(newPos);
        //this.board.put(newPos, player);
        //this.board.put(playerPosition, tileInNewPos);
        for (int i = 0; i < enemies.size() && !over; i++) {
            //Position enemyPos = new Position(enemies.get(i).getPosition());
            //newPos = enemies.get(i).tick(player.getPosition());
            //newPos = enemies.get(i).interact(this.board.get(newPos));
            //tileInNewPos = this.board.get(newPos);
            //this.board.put(newPos, enemies.get(i));
            //this.board.put(enemyPos, tileInNewPos);
        }
        return over;
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
//        for(Map.Entry<Position, Tile> entry : board.entrySet()){
//            sb.append(entry.getValue().getTile());
//            if(entry.getKey().getX() == width - 1){
//                sb.append("\n");
//            }
//        }
        return sb.toString();
    }
}
