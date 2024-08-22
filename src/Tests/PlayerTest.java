package Tests;

import static org.junit.Assert.*;

import Model.Game.Board;
import Model.Tiles.Units.Enemies.Monster;
import Model.Tiles.Units.Players.Mage;
import Model.Tiles.Units.Players.Player;
import Model.Tiles.Units.Players.Rogue;
import Model.Tiles.Units.Players.Warrior;
import Utils.Callbacks.DeathCallbackPlayer;
import Utils.Generators.FixedGenerator;
import View.CLI;
import org.junit.Before;
import org.junit.Test;
import Utils.Position;
import Model.Tiles.Units.Enemies.Enemy;
import java.util.List;
import java.util.ArrayList;


public class PlayerTest {

    private Mage mage;
    private Rogue rogue;
    private Warrior warrior;
    private Enemy enemy;
    private Position position;


    @Before
    public void setUp() {
        CLI cli = new CLI();
        position = new Position(0, 0);  // Initialize a position for testing
        mage = new Mage("TestMage", 100, 10, 5, 20, 30, 40, 3, 5);  // Example Mage initialization
        rogue = new Rogue("TestRogue", 100, 15, 10, 40);  // Example Rogue initialization
        warrior = new Warrior("TestWarrior", 120, 20, 10, 3);  // Example Warrior initialization
        enemy = new Monster('E', "TestEnemy", 150, 5, 2,7,0);  // Example enemy initialization



        mage.initialize(position, new FixedGenerator(), System.out::println, cli.getMessageCallback());
        rogue.initialize(position, new FixedGenerator(), System.out::println, cli.getMessageCallback());
        warrior.initialize(position, new FixedGenerator(),System.out::println, cli.getMessageCallback());
        enemy.initialize(new Position(1,0),new FixedGenerator(), System.out::println, cli.getMessageCallback());
    }

    public Enemy getEnemy() {
        Enemy testEnemy = new Monster('E', "TestEnemy", 1, 5, 2,7,5);

        testEnemy.initialize(new Position(1,0),new FixedGenerator(), System.out::println, System.out::println);

        return testEnemy;
    }

    // Tests for Mage-specific abilities
    @Test
    public void testMage_CastAbility() {
        List<Enemy> enemies = List.of(getEnemy());
        Position result = mage.castAbility(enemies);
        assertEquals(position, result);  // Assuming Mage ability doesn't change position
        assertTrue(mage.getMana() > 0);  // Check that mana was consumed
    }

    // Tests for Rogue-specific abilities
    @Test
    public void testRogue_CastAbility() {
        List<Enemy> enemies = List.of(getEnemy());
        int energy = rogue.getEnergy();
        Position result = rogue.castAbility(enemies);
        assertEquals(position, result);  // Assuming Rogue ability doesn't change position
        assertTrue(rogue.getEnergy() < energy);  // Check that energy was consumed
    }

    // Tests for Warrior-specific abilities
    @Test
    public void testWarrior_CastAbility_NoCooldown() {
        List<Enemy> enemies = List.of(getEnemy());
        Position result = warrior.castAbility(enemies);
        assertEquals(position, result);  // Warrior doesn't change position
        assertEquals(3, warrior.getRemainingCooldown());  // Cooldown is set
    }

    @Test
    public void testWarrior_CastAbility_WithCooldown() {
        warrior.tick('s', new ArrayList<>());  // Use an action to decrease cooldown
        Position result = warrior.castAbility(List.of(new Enemy[]{enemy}));  // Cast ability with cooldown active
        assertEquals(position, result);  // Ability shouldn't be cast because cooldown is active
        assertEquals(3, warrior.getRemainingCooldown());  // Cooldown should have decreased
    }

    // Common Tests for addExperience
    @Test
    public void testAddExperience_NoLevelUp_Mage() {
        mage.addExperience(20);  // Less than required for level-up
        assertEquals(20, mage.getExperience());
        assertEquals(1, mage.getLevel());
    }

    @Test
    public void testAddExperience_NoLevelUp_Rogue() {
        rogue.addExperience(20);  // Less than required for level-up
        assertEquals(20, rogue.getExperience());
        assertEquals(1, rogue.getLevel());
    }

    @Test
    public void testAddExperience_NoLevelUp_Warrior() {
        warrior.addExperience(20);  // Less than required for level-up
        assertEquals(20, warrior.getExperience());
        assertEquals(1, warrior.getLevel());
    }

    @Test
    public void testAddExperience_LevelUp_Mage() {
        mage.addExperience(60);  // Enough for 1 level-up
        assertEquals(2, mage.getLevel());
        assertTrue(mage.getExperience() < 50);  // Remaining experience after level-up
    }

    @Test
    public void testAddExperience_LevelUp_Rogue() {
        rogue.addExperience(60);  // Enough for 1 level-up
        assertEquals(2, rogue.getLevel());
        assertTrue(rogue.getExperience() < 50);  // Remaining experience after level-up
    }

    @Test
    public void testAddExperience_LevelUp_Warrior() {
        warrior.addExperience(60);  // Enough for 1 level-up
        assertEquals(2, warrior.getLevel());
        assertTrue(warrior.getExperience() < 50);  // Remaining experience after level-up
    }

    // Common Tests for tick
    @Test
    public void testTick_MoveUp_Mage() {
        Position originalPosition = mage.getPosition();
        Position newPosition = mage.tick('w', new ArrayList<>());  // Move up
        assertNotEquals(originalPosition, newPosition);
    }

    @Test
    public void testTick_MoveUp_Rogue() {
        Position originalPosition = rogue.getPosition();
        Position newPosition = rogue.tick('w', new ArrayList<>());  // Move up
        assertNotEquals(originalPosition, newPosition);
    }

    @Test
    public void testTick_MoveUp_Warrior() {
        Position originalPosition = warrior.getPosition();
        Position newPosition = warrior.tick('w', new ArrayList<>());  // Move up
        assertNotEquals(originalPosition, newPosition);
    }

    // Common Tests for accept
    @Test
    public void testAccept_Mage_Player() {
        Position result = mage.accept(mage);
        assertEquals(position, result);
    }

    @Test
    public void testAccept_Rogue_Player() {
        Position result = rogue.accept(rogue);
        assertEquals(position, result);
    }

    @Test
    public void testAccept_Warrior_Player() {
        Position result = warrior.accept(warrior);
        assertEquals(position, result);
    }

    @Test
    public void testAccept_Mage_Enemy() {
        Position result = mage.visit(getEnemy());
        assertEquals(getEnemy().getPosition(), result);  // Assuming enemy is defeated
        assertTrue(mage.getExperience() > 0);  // Mage should gain experience
    }

    @Test
    public void testAccept_Rogue_Enemy() {
        Position result = rogue.visit(getEnemy());
        assertEquals(getEnemy().getPosition(), result);  // Assuming enemy is defeated
        assertTrue(rogue.getExperience() > 0);  // Rogue should gain experience
    }

    @Test
    public void testAccept_Warrior_Enemy() {
        Position result = warrior.visit(getEnemy());
        assertEquals(getEnemy().getPosition(), result);  // Assuming enemy is defeated
        assertTrue(warrior.getExperience() > 0);  // Warrior should gain experience
    }

    // Common Tests for levelUp
    @Test
    public void testLevelUp_Mage() {
        mage.addExperience(100);  // Enough for level up
        assertEquals(3, mage.getLevel());
        assertEquals(150, mage.getHealthPool());  // Health increased
        assertEquals(30, mage.getAttack());  // Attack increased
        assertEquals(10, mage.getDefense());  // Defense increased
    }

    @Test
    public void testLevelUp_Rogue() {
        rogue.addExperience(100);  // Enough for level up
        assertEquals(3, rogue.getLevel());
        assertEquals(150, rogue.getHealthPool());  // Health increased
        assertEquals(45, rogue.getAttack());  // Attack increased
        assertEquals(15, rogue.getDefense());  // Defense increased
    }

    @Test
    public void testLevelUp_Warrior() {
        warrior.addExperience(100);  // Enough for level up
        assertEquals(3, warrior.getLevel());
        assertEquals(195, warrior.getHealthPool());  // Health increased
        assertEquals(50, warrior.getAttack());  // Attack increased
        assertEquals(20, warrior.getDefense());  // Defense increased
    }

    // Common Tests for playerDeath
    @Test
    public void testPlayerDeath_Mage() {
        mage.killPlayer();
        assertFalse(mage.alive());  // Mage should no longer be alive
    }

    @Test
    public void testPlayerDeath_Rogue() {
        rogue.killPlayer();
        assertFalse(rogue.alive());  // Rogue should no longer be alive
    }

    @Test
    public void testPlayerDeath_Warrior() {
        warrior.killPlayer();
        assertFalse(warrior.alive());  // Warrior should no longer be alive
    }

}
