package Model.Game;
import Utils.Callbacks.InputCallback;
import Utils.Callbacks.MessageCallback;
import Utils.Generators.Generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Game {
    private final String levelsPath;
    private final MessageCallback messageCallback;
    private final InputCallback inputCallback;
    private final Generator generator;
    private static final int PLAYERS_COUNT = 7;


    public Game(String path, Generator generator, MessageCallback messageCallback, InputCallback inputCallback){
        this.generator = generator;
        this.messageCallback = messageCallback;
        this.inputCallback = inputCallback;
        this.levelsPath = path;
    }

    public void runGame(){
        // print menu and let player choose character
        int playerId = choosePlayer(PLAYERS_COUNT);
        Path path = Paths.get(levelsPath);
        try {
            List<Path> files = Files.list(path)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            for (Path file : files) {
                Level level = new Level(playerId, file.toAbsolutePath().toString(), generator, messageCallback, inputCallback);
                level.initLevel();
                if (!level.start()) {
                    messageCallback.send("You lost.");
                    messageCallback.send(level.getBoard());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
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
7. Ygritte         Health: 220/220    Attack: 30     Defense: 2      Level: 1     Experience: 0/50    Arrows: 10        Range: 6""");
    }

    public int choosePlayer(int max){
        int choice = 0;
        do {
            try {
                printMenu();
                choice = Integer.parseInt(inputCallback.recieve());
                if(choice < 1 || choice > max){
                    messageCallback.send("Invalid Choice");
                }
            } catch (Exception _) {
            }
        }while(choice < 1 || choice > max);
        return choice;
    }
}
