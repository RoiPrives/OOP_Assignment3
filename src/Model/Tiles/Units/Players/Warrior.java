package Model.Tiles.Units.Players;

import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.InputCallback;
import Utils.Generators.Generator;
import Utils.Position;

import java.util.List;

public class Warrior extends Player{

    private int cooldown;
    private int remainingCooldown;

    private static final int INCREASE_HEALTH_POOL = 5;
    private static final int INCREASE_ATTACK = 2;
    private static final int INCREASE_DEFENSE = 1;
    private static final int INCREASE_HEALTH = 10;
    private static final int ABILITY_RANGE = 3;
    private static final double ABILITY_FACTOR = 0.1;

    public Warrior(String name, int healthPool, int attack, int defense, int cooldown) {
        super(name, healthPool, attack, defense);
        remainingCooldown = 0;
        this.cooldown = cooldown;
    }

    @Override
    public Position castAbility(List<Unit> enemies) {
        if(remainingCooldown <= 0) {
            remainingCooldown = cooldown;
            health.increaseHealthAmout(INCREASE_HEALTH * defense);
            List<Unit> enemiesInRange = getEnemiesInRange(enemies, ABILITY_RANGE);
            Unit enemyToAttack = enemiesInRange.get(generator.generate(enemiesInRange.size()));
            enemyToAttack.takeDamage((int)(ABILITY_FACTOR * health.getHealthPool()), this);
        }
        return this.position;
    }

    @Override
    public void levelUp(){
        super.levelUp();
        remainingCooldown = 0;
        health.increaseHealthPool(INCREASE_HEALTH_POOL * level);
        attack += INCREASE_ATTACK * level;
        defense += INCREASE_DEFENSE * level;
        health.fillHealthPool();
    }
    @Override
    public Position tick(char actionChar, List<Unit> enemies) {
        Position pos = super.tick(actionChar ,enemies);
        remainingCooldown = remainingCooldown -1;
        return pos;
    }

    @Override
    public String toString(){
        return super.toString() + " Cooldown: " + this.cooldown + " Remaining: " + this.remainingCooldown;
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }
}
