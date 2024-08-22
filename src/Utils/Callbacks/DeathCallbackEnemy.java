package Utils.Callbacks;

import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Unit;

public interface DeathCallbackEnemy {
    void onDeath(Enemy enemy);
}
