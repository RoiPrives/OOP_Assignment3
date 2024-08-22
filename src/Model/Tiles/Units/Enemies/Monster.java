package Model.Tiles.Units.Enemies;


import Model.Tiles.Units.Players.Player;
import Utils.Position;

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

    @Override
    public Position tick(Player player) {
        Position playerPos = player.getPosition();
        if(this.position.range(playerPos) < this.visionRange){
            int dx = this.position.getX() - playerPos.getX();
            int dy = this.position.getY() - playerPos.getY();
            if(Math.abs(dx) > Math.abs(dy)){
                if(dx > 0)
                    return moveLeft();
                return moveRight();
            }
            if(dy > 0)
                return moveUp();
            return moveDown();
        }
        return moves.get(generator.generate(moves.size())).get();
    }
}
