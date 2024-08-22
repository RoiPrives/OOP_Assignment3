package Model.Tiles.Units.Players;

import Model.Tiles.Units.Enemies.Enemy;
import Utils.Position;

import java.util.List;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    private int ticksCount;


    public Hunter(String name, int healthPool, int attack, int defense, int range) {
        super(name, healthPool, attack, defense);
        this.arrowsCount = 10 * getLevel();
        this.ticksCount = 0;

    }

    @Override
    public void levelUp() {
        super.levelUp();
        arrowsCount = arrowsCount + (10 * getLevel());
        attack = getAttack() + (2 * getLevel());
        defense = getDefense() + getLevel();
    }

    public Enemy closestEnemy(List<Enemy> enemies){
        List<Enemy> inRange = getEnemiesInRange(enemies, range);
        Enemy enemy = null;
        double closestPos = range;
        for(Enemy e : inRange){
            double closerPos = e.getPosition().range(this.position);
            if(closerPos < closestPos){
                closestPos = closerPos;
                enemy = e;
            }
        }
        return enemy;
    }

    @Override
    public Position castAbility(List<Enemy> enemies) {
        if(arrowsCount == 0)
            return this.position;
        Enemy enemy = closestEnemy(enemies);
        if (enemy!=null) {
            arrowsCount = arrowsCount - 1;
            combatConstAttack(this.attack , enemy);
        }
        return this.position;
    }

    @Override
    public Position tick(char actionChar, List<Enemy> enemies) {
        if (ticksCount == 10) {
            arrowsCount = arrowsCount + getLevel();
            ticksCount = 0;
        }
        Position pos = super.tick(actionChar ,enemies);
        ticksCount++;
        return pos;
    }


    @Override
    public String toString() {
        return super.toString() + " Range " + this.range + " ArrowsCount " + this.arrowsCount;
    }


    public int getArrowsCount() {
        return this.arrowsCount;
    }
}


