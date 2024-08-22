package Model.Tiles.Units.Enemies;

import Model.Tiles.Units.HeroicUnit;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Unit;
import Utils.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Boss extends Monster implements HeroicUnit {
    private int visionRange;
    private int abilityFrequency;
    private int combatTicks;

    public Boss(char tile, String name, int healthPool, int attack, int defense, int visionRange, int experienceVal, int abilityFrequency) {
        super(tile, name, healthPool, attack, defense, visionRange, experienceVal);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }


    @Override
    public Position tick(Player player) {
        if(this.position.range(player.getPosition()) < this.visionRange){
            if(this.combatTicks == this.abilityFrequency){
                combatTicks = 0;
                List<Unit> playerList = new ArrayList<>();
                playerList.add(player);
                return castAbility(playerList);
            }
            else{
                combatTicks++;
                int dx = this.position.getX() - player.getPosition().getX();
                int dy = this.position.getY() - player.getPosition().getY();
                if(Math.abs(dx) > Math.abs(dy)){
                    if(dx > 0)
                        return moveLeft();
                    else
                        return moveRight();
                }
                else {
                    if(dy > 0)
                        return moveUp();
                    else
                        return moveDown();
                }
            }
        }
        else{
            combatTicks = 0;
            return moves.get(generator.generate(moves.size())).get();
        }
    }


    @Override
    public Position castAbility(List<Unit> units) {
        this.combatConstAttack(this.attack, units.getFirst());
        return this.position;
    }
}
