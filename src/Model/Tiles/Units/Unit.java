package Model.Tiles.Units;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Wall;
import Utils.Callbacks.DeathCallback;
import Utils.Health;
import Utils.Position;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;

import java.util.function.Supplier;

public abstract class Unit extends Tile {

    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected Generator generator;
    protected DeathCallback deathCallback;
    protected MessageCallback messageCallback;
    protected boolean alive;

    public Unit(char tile, String name, int healthPool, int attack, int defense){
        super(tile);
        this.name = name;
        this.health = new Health(healthPool);
        this.attack = attack;
        this.defense = defense;
    }

    public Position moveUp(){
        return new Position(this.position.getX(), this.position.getY() - 1);
    }

    public Position moveDown(){
        return new Position(this.position.getX(), this.position.getY() + 1);
    }

    public Position moveLeft(){
        return new Position(this.position.getX() - 1, this.position.getY());
    }
    public Position moveRight(){
        return new Position(this.position.getX() + 1, this.position.getY());
    }


    public Unit initialize(Position position, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback){
        super.initialize(position);
        this.deathCallback = deathCallback;
        this.generator = generator;
        this.messageCallback = messageCallback;
        return this;
    }

    public Position interact(Tile t){
        return t.accept(this);
    }

    public Position visit(EmptyTile empty) {
        swapPositions(empty);
        return this.position;
    }

    public Position visit(Wall wall) {
        return this.position;
    }

    public abstract Position visit(Player player);
    public abstract Position visit(Enemy enemy);

    private int attack(){
        return generator.generate(attack);
    }

    private int defend(){
        return generator.generate(defense);
    }

    public boolean alive() { return health.getHealthAmount() > 0; }

    public void combat(Unit defender){
        int attack = this.attack();
        int defend = defender.defend();
        defender.takeDamage(attack - defend);
    }

    public void combatConstAttack(int damage , Unit unit){
        int defend = unit.defend();
        unit.takeDamage(damage - defend);
    }

    public void takeDamage(int damage){
        health.takeDamage(damage);
        if(!alive()) {
            deathCallback.onDeath(this);
        }
    }

    public abstract void handleDeath();

    /*public void onDeath(){
        deathCallback.onDeath();
    }
     */

    public abstract DeathCallback getDeathCallback();

    @Override
    public String toString() {
        String returnValue;
        String tab = "  ";
        returnValue = "name: " +  name + tab +  health.toString() + tab + "attack : " + attack + tab + "defense : "  + defense;
        return returnValue;
    }
}
