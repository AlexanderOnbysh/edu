package tokens;

/**
 * Created by alexon
 */
public class UselessBoots implements Token {

    @Override
    public String getName() {
        return "UselessBoots: " + this.getArmor();
    }

    @Override
    public int getArmor() {
        return 0;
    }

    @Override
    public void increaseArmor(int value) {
    }

    @Override
    public Token clone() {
        return new UselessBoots();
    }

}
