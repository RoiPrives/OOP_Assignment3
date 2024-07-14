package Model.Tiles.Units.Players;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.DeathCallback;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import Utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Player extends Unit {
    public static final char PLAYER_TILE = '@';
    protected int experience;
    protected int level;

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

    public Player(String name, int healthPool, int attack, int defense) {
        super(PLAYER_TILE, name, healthPool, attack, defense);
        this.experience = 0;
        this.level = 1;
    }

    public abstract Position castAbility(List<Enemy> enemies);


    public void addExperience(int experience) {
        this.experience += experience;
        while(experience >= EXPERIENCE_TO_LEVEL_UP * level)
            levelUp();
    }

    public Position tick(char actionChar, List<Enemy> enemies){
        return actions.getOrDefault(actionChar, () -> this.castAbility(enemies)).get();
    }


    public Position doNothing(){
        return this.position;
    }

    @Override
    public Position accept(Unit unit) {
        return unit.visit(this);
    }

    protected List<Enemy> getEnemiesInRange(List<Enemy> enemies, int range){
        List<Enemy> inRangeEnemies = new ArrayList<>();
        for(Enemy enemy: enemies){
            if(this.position.range(enemy.getPosition()) < range)
                inRangeEnemies.add(enemy);
        }
        return inRangeEnemies;
    }

    public void levelUp(){
        this.experience -= EXPERIENCE_TO_LEVEL_UP * level;
        this.level++;
        this.health.increaseHealthPool(INCREASE_HEALTH_POOL * level);
        this.health.fillHealthPool();
        this.attack += INCREASE_ATTACK * level;
        this.defense += INCREASE_DEFENSE * level;
    }

    public Player initialize(Position position, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback) {
        super.initialize(position, generator, deathCallback, messageCallback);
        return this;
    }

    public Position visit(Player player){
        return this.position;
    }

    public Position visit(Enemy enemy){
        combat(enemy);
        if(!enemy.alive()) {
            addExperience(enemy.getExperience());
            //enemy.onDeath();
        }
        return this.position;
    }

    public void playerDeath(Unit unit){
        deathCallback.onDeath(this);
    }

    public DeathCallback getDeathCallback(){
        return this::playerDeath;
    }

    @Override
    public String toString(){
        return super.toString() + "  " + level;
    }

}
