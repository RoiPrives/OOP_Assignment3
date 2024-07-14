package Model.Tiles;

import Model.Tiles.Units.Unit;
import Utils.Position;

public class Wall extends Tile{
    public static final char WALL_TILE = '#';

    public Wall() {
        super(WALL_TILE);
    }


    @Override
    public Position accept(Unit unit) {

        return unit.visit(this);
    }
}
