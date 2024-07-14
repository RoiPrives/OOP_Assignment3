//package Control.Initializers;
//
//import Model.Game.Board;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class BoardFactory {
//
//
//    public BoardFactory(){
//    }
//    public void initialize(String levelsPath, List<Board> boards){
//        Path path = Paths.get(levelsPath);
//        try {
//            Files.list(path)
//                    .filter(Files::isRegularFile)
//                    .forEach(file -> {
//                        System.out.println("File: " + file.getFileName());
//                        // Perform your file processing here
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
