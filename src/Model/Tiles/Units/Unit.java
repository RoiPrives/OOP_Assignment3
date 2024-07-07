package Model.Tiles.Units;
import Model.Tiles.Tile;
import Model.Utils.Health;
import Model.Utils.Generators.Generator;
import Model.Utils.Position;

public class Unit extends Tile {

    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected Generator generator;

    public Unit(char tile, String name, int healthPool, int attack, int defense){
        super(tile);
        this.name = name;
        this.health = new Health(healthPool);
        this.attack = attack;
        this.defense = defense;
    }


    private void initialize(Position position, Generator generator){
        super.initialize(position);
        this.generator = generator;
    }

    private int attack(){
        return generator.generate(attack);
    }

    private int defend(){
        return generator.generate(defense);
    }

    public void combat(Unit defender){
        int attack = this.attack();
        int defend = defender.defend();
        defender.health.takeDamage(attack - defend);
    }
}
