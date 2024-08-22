package Model.Tiles.Units.Enemies;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallbackEnemy;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;



public abstract class Enemy extends Unit {

    protected int experience;
    private DeathCallbackEnemy deathCallback;

    public Enemy(char tile, String name, int healthPool, int attack, int defense) {
        super(tile, name, healthPool, attack, defense);
    }

    public int getExperience() {
        return this.experience;
    }


//    public DeathCallbackEnemy getDeathCallback() {
//        return this::enemyDeath;
//    }

    @Override
    public String toString(){
        return super.toString() + " Experience: " + this.experience;
    }

    @Override
    public Position accept(Unit unit) {
        return unit.visit(this);
    }


    /*public void enemyDeath(Unit unit){
        deathCallback.onDeath(this);
        // some more things
    }

     */


    public Enemy initialize(Position position, Generator generator, DeathCallbackEnemy deathCallback, MessageCallback messageCallback) {
        super.initialize(position, generator, messageCallback);
        this.deathCallback = deathCallback;
        return this;
    }

    public Position visit(Enemy enemy){
        return this.position;
    }

    @Override
    public void notifyDeath() {
        deathCallback.onDeath(this);
    }

    public abstract Position tick(Player player);



    public Position visit(Player player){
        combat(player);
        //if(!player.alive())
            //player.notifyDeath();
        return this.position;
    }
}
