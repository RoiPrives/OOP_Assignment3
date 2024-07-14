package Model.Tiles.Units.Enemies;



public class Trap extends Enemy{

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int healthPool, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(tile, name, healthPool, attack, defense);
        visible = true;
        ticksCount = 0;
        this.invisibilityTime = invisibilityTime;
        this.visibilityTime = visibilityTime;
    }

    @Override
    public String toString(){
        return super.toString() + " Visibility time: " + this.visibilityTime + " invisibility time: " + this.invisibilityTime +
                " Ticks count: " + this.ticksCount + " visible: " + this.visible;
    }



}
