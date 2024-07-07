package Model.Utils.Generators;

public class FixedGenerator implements Generator{

    @Override
    public int generate(int max) {
        return max / 2;
    }
}
