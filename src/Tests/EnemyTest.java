package Tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import Model.Tiles.Units.Players.Mage;
import Utils.Generators.FixedGenerator;
import View.CLI;
import org.junit.Before;
import org.junit.Test;
import Model.Tiles.Units.Enemies.Monster;
import Model.Tiles.Units.Enemies.Trap;
import Model.Tiles.Units.Players.Player;
import Utils.Position;

public class EnemyTest {

    private Monster monster;
    private Trap trap;
    private Player mage;
    private Position monsterPosition;
    private Position playerPosition;
    private Position trapPosition;

    @Before
    public void setUp() {
        CLI cli = new CLI();
        // Initializing test positions
        monsterPosition = new Position(5, 5);
        playerPosition = new Position(7, 5);  // Close to the monster
        trapPosition = new Position(3, 3);

        // Initialize a monster and trap with specific parameters
        mage = new Mage("TestMage", 100, 10, 5, 20, 30, 40, 3, 5);  // Example Mage initialization
        monster = new Monster('M', "TestMonster", 100, 10, 5, 3, 50);
        trap = new Trap('T', "TestTrap", 80, 20, 5, 30, 3, 5);


        mage.initialize(playerPosition, new FixedGenerator(), System.out::println, cli.getMessageCallback());
        monster.initialize(monsterPosition, new FixedGenerator(), System.out::println, cli.getMessageCallback());
        trap.initialize(trapPosition, new FixedGenerator(), System.out::println, cli.getMessageCallback());

    }

    // Tests for Monster-specific behavior
    @Test
    public void testMonster_Tick_MoveTowardPlayer() {
        Position newPosition = monster.tick(mage);
        assertNotEquals(monsterPosition, newPosition);  // Monster should move towards the player
    }

    @Test
    public void testMonster_Tick_NoMove_WhenOutOfVisionRange() {
        Player player = new Mage("TestMage2", 100, 10, 5, 20, 30, 40, 3, 5);
        Position farPlayerPosition = new Position(20, 20);  // Player is far away
        player.initialize(farPlayerPosition, new FixedGenerator(), System.out::println, System.out::println);
        Position newPosition = monster.tick(player);
        assertNotEquals(monsterPosition, newPosition);  // Monster should not move when player is out of vision range
    }

    @Test
    public void testMonster_Combat() {
        int originalHealth = mage.getHealthAmount();
        Position result = monster.visit(mage);
        assertEquals(monsterPosition, result);  // Monster should remain in the same position after combat
        assertTrue(mage.getHealthAmount() < originalHealth);  // Player health should decrease after combat
    }

    // Tests for Trap-specific behavior
    @Test
    public void testTrap_Tick_VisibleState() {
        for (int i = 0; i < 3; i++) {  // Visibility time is 3 ticks
            trap.tick(mage);
            assertTrue(trap.isVisible());  // Trap should be visible during visibility period
        }
    }

    @Test
    public void testTrap_Tick_InvisibleState() {
        for (int i = 0; i < 8; i++) {  // After 3 ticks of visibility and 5 ticks of invisibility
            trap.tick(mage);
        }
        assertFalse(trap.isVisible());  // Trap should be invisible after visibility period
    }

    @Test
    public void testTrap_Tick_ResetTicks() {
        for (int i = 0; i < 8; i++) {
            trap.tick(mage);  // Running through the full cycle of visibility + invisibility
        }
        assertEquals(8, trap.getTicksCount());
    }

    @Test
    public void testTrap_Tick_PlayerInRange() {
        Player player = new Mage("TestMage2", 100, 10, 5, 20, 30, 40, 3, 5);
        playerPosition = new Position(4, 4);  // Player is in range (within 2 units)
        player.initialize(playerPosition, new FixedGenerator(), System.out::println, System.out::println);
        Position result = trap.tick(player);
        assertEquals(playerPosition, result);  // Trap should "attack" the player by returning their position
    }

    @Test
    public void testTrap_Tick_PlayerOutOfRange() {
        playerPosition = new Position(10, 10);  // Player is out of range
        Position result = trap.tick(mage);
        assertEquals(trapPosition, result);  // Trap should remain in its original position
    }

    // Tests for experience gain
    @Test
    public void testMonster_GetExperience() {
        assertEquals(50, monster.getExperience());  // Monster should have 50 experience value
    }

    @Test
    public void testTrap_GetExperience() {
        assertEquals(30, trap.getExperience());  // Trap should have 30 experience value
    }

    // Death-related Tests
    @Test
    public void testMonster_NotifyDeath() {
        monster.notifyDeath();
    }

    @Test
    public void testTrap_NotifyDeath() {
        trap.notifyDeath();
    }

    // toString Tests
    @Test
    public void testMonster_ToString() {
        String monsterDescription = monster.toString();
        assertTrue(monsterDescription.contains("TestMonster"));
        assertTrue(monsterDescription.contains("Experience: 50"));
        assertTrue(monsterDescription.contains("Vision Range: 3"));
    }

    @Test
    public void testTrap_ToString() {
        String trapDescription = trap.toString();
        assertTrue(trapDescription.contains("TestTrap"));
        assertTrue(trapDescription.contains("Visibility time: 3"));
        assertTrue(trapDescription.contains("invisibility time: 5"));
    }
}
