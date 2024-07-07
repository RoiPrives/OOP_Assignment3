package Model.Tiles.Units.Players;

import Model.Tiles.Units.Unit;

public abstract class Player extends Unit {
    public static final char PLAYER_TILE = '@';
    protected int experience;
    protected int level;

    public Player(String name, int healthPool, int attack, int defence){
        super(PLAYER_TILE, name, healthPool, attack, defence);
        this.experience = 0;
        this.level = 1;
    }
}
