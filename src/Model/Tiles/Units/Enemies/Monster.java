package Model.Tiles.Units.Enemies;



public class Monster extends Enemy{
    private int visionRange;

    public Monster(char tile, String name, int healthPool, int attack, int defense, int visionRange, int experienceVal){
        super(tile, name, healthPool, attack, defense);
        this.experience = experienceVal;
        this.visionRange = visionRange;
    }

    @Override
    public String toString(){
        return super.toString() + " Vision Range: " + this.visionRange;
    }
}
