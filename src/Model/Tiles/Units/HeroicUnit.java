package Model.Tiles.Units;

import Model.Tiles.Units.Enemies.Enemy;
import Utils.Position;

import java.util.List;

public interface HeroicUnit {
    public Position castAbility(List<Unit> units);
}
