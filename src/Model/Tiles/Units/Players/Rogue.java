package Model.Tiles.Units.Players;

import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Unit;
import Utils.Callbacks.InputCallback;
import Utils.Position;

import java.util.List;
import java.util.function.Supplier;

public class Rogue extends Player {
    private static final int MAX_ENERGY = 100;
    private static final int INCREASE_ATTACK = 2;

    private int energy;
    private int cost;

    public Rogue(String name, int healthPool, int attack, int defense, int cost) {
        super(name, healthPool, attack, defense);
        this.energy = MAX_ENERGY;
        this.cost = cost;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        energy = MAX_ENERGY;
        attack += INCREASE_ATTACK * this.level;
    }

    @Override
    public Position castAbility(List<Unit> units) {
        if (energy >= cost) {
            energy -= cost;
            List<Unit> enemiesInRange = getEnemiesInRange(units, 2);
            for (Unit enemy : enemiesInRange) {
                combatConstAttack(this.attack, enemy);
            }
        }
        return this.position;
    }

    @Override
    public Position tick(char actionChar, List<Unit> units) {
        Position pos = super.tick(actionChar ,units);
        energy = Math.min(energy + 10, MAX_ENERGY);
        return pos;
    }

    @Override
    public String toString() {
        return super.toString() + " Energy " + this.energy + " Cost " + this.cost;
    }

    public int getEnergy() {
        return this.energy;
    }
}