package Model.Tiles.Units.Enemies;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallback;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;


public abstract class Enemy extends Unit {

    protected int experience;

    public Enemy(char tile, String name, int healthPool, int attack, int defense) {
        super(tile, name, healthPool, attack, defense);
    }

    public int getExperience() {
        return this.experience;
    }

    @Override
    public DeathCallback getDeathCallback() {
        return this::enemyDeath;
    }

    @Override
    public String toString(){
        return super.toString() + " Experience: " + this.experience;
    }

    @Override
    public Position accept(Unit unit) {
        return unit.visit(this);
    }


    public void enemyDeath(Unit unit){
        deathCallback.onDeath(this);
        // some more things
    }


    public Enemy initialize(Position position, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback) {
        super.initialize(position, generator, deathCallback, messageCallback);
        return this;
    }

    public Position visit(Enemy enemy){
        return this.position;
    }



    public Position visit(Player player){
        combat(player);
        if(!player.alive())
            player.onDeath();
        return this.position;
    }
}
