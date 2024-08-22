package Tests;

import Model.Game.Game;
import Model.Tiles.Units.Players.Mage;
import View.TestView;
import View.View;
import Utils.Generators.FixedGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Tests {
    Game game;
    TestView view;
    @BeforeAll
    public void setUp() {
        ArrayList<Character> actions = new ArrayList<>(Arrays.asList('1', 'd', 'd', 'd'));
        view = new TestView(actions);
        game = new Game("C:\\Users\\roipr\\OneDrive\\Documents\\UNI\\Semester B\\OOB\\OOP_Assignment3\\levels_dir", new FixedGenerator(), view.getMessageCallback(), view.getInputCallback());
    }

    @Test
    public void testChoosePlayer() {
        game.choosePlayer(100);
        //assertEquals(5, result, "2 + 3 should equal 5");
        game.choosePlayer(-1);

    }

    @Test
    public void testJohnSnow() {
        game.choosePlayer(1);
        //assertEquals();
    }
    public void levelUpTest(){
        Mage testDummy;
        testDummy = new Mage("joe",1,2,3,4,5,6,7,8);
        testDummy.levelUp();
        Assertions.assertEquals(2, testDummy.getLevel());
    }




}
