package Model.Tiles.Units.Players;
import Model.Tiles.Units.Enemies.Enemy;
import Utils.Callbacks.InputCallback;
import Utils.Position;

import java.util.List;
import java.util.function.Supplier;

public class Mage extends Player{
    private int manaPool;
    private int currentMana;
    private final int manaCost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;

    public Mage(String name, int healthPool, int attack, int defense, int manaPool , int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, healthPool, attack, defense);
        this.manaPool = manaPool;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
        this.currentMana = manaPool/4;
    }

    public Position castAbility(List<Enemy> enemies){
        if(currentMana >= manaCost){
            currentMana -= manaCost;
            int hits  = 0;
            List<Enemy> enemiesInRange = getEnemiesInRange(enemies, abilityRange);
            while (hits < hitsCount && !enemiesInRange.isEmpty()) {
                Enemy enemyToAttack = enemiesInRange.get(generator.generate(enemiesInRange.size()));
                this.combatConstAttack(spellPower, enemyToAttack);
                if (enemyToAttack.alive())
                    enemiesInRange.add(enemyToAttack);
                hits++;
            }
        }
    return this.position;
    }


    @Override
    public void levelUp() {
        super.levelUp();
        manaPool = manaPool + 25 * level;
        currentMana = Math.min(currentMana + manaPool/4, manaPool);
        spellPower = spellPower + 10 * level;
    }

    @Override
    public Position tick(char actionChar, List<Enemy> enemies){
        Position pos = super.tick(actionChar, enemies);
        currentMana = Math.min(currentMana + level, manaPool);
        return pos;
    }


    @Override
    public String toString() {
        String tab = "  ";
        return super.toString() + tab + "current mana: " + currentMana  + "/" + manaPool + tab + "spellPower: "  + spellPower;
    }

    public int getMana() {
        return this.currentMana;
    }


}
