package Model.Game;
import Utils.Callbacks.InputCallback;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Game {
    //private List<Board> boards;
    //private LevelInitializer levelInitializer;
    private final String levelsPath;
    private final MessageCallback messageCallback;
    private final InputCallback inputCallback;
    private final Generator generator;
    private static final int PLAYERS_COUNT = 6;


    public Game(String path, Generator generator, MessageCallback messageCallback, InputCallback inputCallback){
        //boards = new ArrayList<Board>();
        this.generator = generator;
        //this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
        this.inputCallback = inputCallback;
        this.levelsPath = path;
    }

    public void runGame(){
        //Board board = new Board();
        //board.initBoard();
        //Player player = new Player("player", 20, 20, 20).initialize(new Position(0, 0), generator, board.getDeathCallbackPlayer(), messageCallback);
        //Enemy enemy = new Enemy('a', "enemy", 20, 20, 20).initialize(new Position(1, 0), generator, board.getDeathCallbackEnemy(), messageCallback);
        //player.takeDamage(40);
        //enemy.takeDamage(40);
        // print menu and let player choose character
        int playerId = choosePlayer(PLAYERS_COUNT);
       //levelInitializer = new LevelInitializer(playerId, generator, deathCallback, messageCallback);//levelInitializer = new LevelInitializer(playerId, generator, messageCallback);
        Path path = Paths.get(levelsPath);
        try {
            Files.list(path)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        Level level = new Level(playerId, file.toAbsolutePath().toString(), generator, messageCallback, inputCallback);
                        level.initLevel();
                        if(!level.start())
                            throw new RuntimeException("OVER");
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printMenu(){
        messageCallback.send("""
Select player:
1. Jon Snow        Health: 300/300    Attack: 30     Defense: 4      Level: 1     Experience: 0/50    Cooldown: 0/3
2. The Hound       Health: 400/400    Attack: 20     Defense: 6      Level: 1     Experience: 0/50    Cooldown: 0/5
3. Melisandre      Health: 100/100    Attack: 5      Defense: 1      Level: 1     Experience: 0/50    Mana: 75/300      Spell Power: 15
4. Thoros of Myr   Health: 250/250    Attack: 25     Defense: 4      Level: 1     Experience: 0/50    Mana: 37/150      Spell Power: 20
5. Arya Stark      Health: 150/150    Attack: 40     Defense: 2      Level: 1     Experience: 0/50    Energy: 100/100
6. Bronn           Health: 250/250    Attack: 35     Defense: 3      Level: 1     Experience: 0/50    Energy: 100/100
                """);
    }

    private int choosePlayer(int max){
        int choice = 0;
        do {
            try {
                printMenu();
                choice = Integer.parseInt(inputCallback.recieve());
            } catch (Exception _) {
            }
        }while(choice < 1 || choice > max);
        return choice;
    }
}
