package tokens;

public class Helmet implements Token {
    private int armor;

    public Helmet(int armor) {
        this.armor = armor;
    }

    Helmet(Helmet helmet) {
        this.armor = helmet.armor;
    }

    @Override
    public String getName() {
        return "SuperHelmet: " + this.getArmor();
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public void increaseArmor(int value) {
        this.armor += value;
    }

    @Override
    public Token clone() {
        return new Helmet(this.armor);
    }

}
