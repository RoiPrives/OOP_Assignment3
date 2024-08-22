package Model.Tiles.Units;
import Model.Tiles.EmptyTile;
import Model.Tiles.Tile;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Wall;
import Utils.Health;
import Utils.Position;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Unit extends Tile {

    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected Generator generator;
    //protected DeathCallback deathCallback;
    protected MessageCallback messageCallback;
    protected List<Supplier<Position>> moves = List.of(
            this::moveLeft,
            this::moveRight,
            this::moveUp,
            this::moveDown,
            this::doNothing
    );

    public Unit(char tile, String name, int healthPool, int attack, int defense){
        super(tile);
        this.name = name;
        this.health = new Health(healthPool);
        this.attack = attack;
        this.defense = defense;
    }
    public String getName() {
        return name;
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

    public Position doNothing(){
        return this.position;
    }


    public Unit initialize(Position position, Generator generator,  MessageCallback messageCallback){
        super.initialize(position);
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
        messageCallback.send(this.name + " engaged in combat with " + defender.getName());
        messageCallback.send(this.toString());
        messageCallback.send(defender.toString());
        int attack = this.attack();
        int defend = defender.defend();
        messageCallback.send(this.name + " rolled " + attack + " attack points.");
        messageCallback.send(defender.name + " rolled " + defend + " defence points.");
        messageCallback.send(this.name + " dealt " + defender.takeDamage(attack - defend, this) + " damage to " + defender.getName() + ".");
    }

    public void combatConstAttack(int damage , Unit unit){
        int defend = unit.defend();
        unit.takeDamage(damage - defend, this);
    }

    public int takeDamage(int damage, Unit attacker){
        int taken = health.takeDamage(damage);
        if(!alive()) {
            messageCallback.send(this.name + " was killed by " + attacker.name + ".");
            notifyDeath();
        }
        return taken;
    }

    public abstract void notifyDeath();
    public int getHealth(){
        return this.health.getHealthAmount();
    }

    @Override
    public String toString() {
        String returnValue;
        returnValue = name + "\t" +  health.toString() + "\tAttack: " + attack + "\tDefense: "  + defense;
        return returnValue;
    }
}
