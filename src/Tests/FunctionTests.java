package Tests;
import Model.Tiles.Units.Enemies.Enemy;
import Model.Tiles.Units.Players.Mage;
import Model.Tiles.Units.Players.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionTests {
    Player testDummy;
    Enemy TestEnemy;

    @Test
    public void levelUpTest(){
        testDummy = new Mage("joe",1,2,3,4,5,6,7,8);
        testDummy.levelUp();
        Assertions.assertEquals(2, testDummy.getLevel());
    }
}
