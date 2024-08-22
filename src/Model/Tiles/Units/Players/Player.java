package Model.Tiles.Units.Players;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.HeroicUnit;
import Model.Tiles.Units.Unit;
//import Utils.Callbacks.DeathCallback;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Player extends Unit implements HeroicUnit {
    public static final char PLAYER_TILE = '@';
    protected int experience;
    protected int level;
    private DeathCallbackPlayer deathCallback;

    private static final int EXPERIENCE_TO_LEVEL_UP = 50;
    private static final int INCREASE_HEALTH_POOL = 10;
    private static final int INCREASE_ATTACK = 4;
    private static final int INCREASE_DEFENSE = 1;

    private final Map<Character, Supplier<Position>> actions = Map.of(
            'w', this::moveUp,
            'd', this::moveRight,
            's', this::moveDown,
            'a', this::moveLeft,
            'q', this::doNothing
    );

    public Player(String name, int healthPool, int attack, int defense){//, DeathCallbackPlayer deathCallback) {
        super(PLAYER_TILE, name, healthPool, attack, defense);
        this.experience = 0;
        this.level = 1;
    }

    //public abstract Position castAbility(List<Enemy> enemies);
    public Position castAbility(List<Unit> units){
        messageCallback.send("specialAbility");
        return this.position;
    }


    public void addExperience(int experience) {
        this.experience += experience;
        while(experience >= EXPERIENCE_TO_LEVEL_UP * level)
            levelUp();
    }

    public Position tick(char actionChar, List<Unit> units){
        return actions.getOrDefault(actionChar, () -> this.castAbility(units)).get();
    }
    public void killPlayer(){
        health.takeDamage(getHealthPool());
    }

    @Override
    public Position accept(Unit unit) {
        return unit.visit(this);
    }

    protected List<Unit> getEnemiesInRange(List<Unit> enemies, int range){
        List<Unit> inRangeEnemies = new ArrayList<>();
        for(Unit enemy: enemies){
            if(this.position.range(enemy.getPosition()) < range)
                inRangeEnemies.add(enemy);
        }
        return inRangeEnemies;
    }

    public void levelUp(){
        this.experience = Math.abs(this.experience - EXPERIENCE_TO_LEVEL_UP * level);
        this.level++;
        this.health.increaseHealthPool(INCREASE_HEALTH_POOL * level);
        this.health.fillHealthPool();
        this.attack += INCREASE_ATTACK * level;
        this.defense += INCREASE_DEFENSE * level;
    }

    public Player initialize(Position position, Generator generator, DeathCallbackPlayer deathCallback, MessageCallback messageCallback) {
        super.initialize(position, generator, messageCallback);
        this.deathCallback = deathCallback;
        return this;
    }

    public Position visit(Player player){
        return this.position;
    }

    public Position visit(Enemy enemy){
        combat(enemy);
        if(!enemy.alive()) {
            this.swapPositions(enemy);
            addExperience(enemy.getExperience());
        }
        return this.position;
    }

    public void setChar(char c){
        this.tile = c;
    }

    public void playerDeath(Unit unit){
        deathCallback.onDeath(this);
    }

    @Override
    public String toString(){
        return super.toString() + "\tLevel: " + level + "\tExperience: " + experience + "/" + EXPERIENCE_TO_LEVEL_UP * level;
    }

    @Override
    public void notifyDeath(){
        deathCallback.onDeath(this);
    }

    public int getHealthAmount() {
        return  this.health.getHealthAmount();
    }

    public int getExperience() {
        return this.experience;
    }

    public int getHealthPool() {
        return this.health.getHealthPool();
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }
    public int getLevel() { return this.level; }
}
