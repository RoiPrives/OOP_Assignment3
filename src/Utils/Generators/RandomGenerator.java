package Utils.Generators;
import java.util.Random;

public class RandomGenerator implements Generator {
    private Random random;

    public RandomGenerator() {
        random = new Random();
    }
    @Override
    public int generate(int max) {
        return random.nextInt(max);
    }
}
