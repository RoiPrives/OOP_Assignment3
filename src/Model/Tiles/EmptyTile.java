package Model.Tiles;

import Model.Tiles.Units.Unit;
import Utils.Position;

public class EmptyTile extends Tile {
    public static final char EMPTY_TILE = '.';
    public EmptyTile() {
        super(EMPTY_TILE);
    }

    @Override
    public Position accept(Unit unit) {
        return unit.visit(this);
    }
}
