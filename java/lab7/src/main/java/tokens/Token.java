package tokens;

public interface Token{
    String getName();

    int getArmor();

    void increaseArmor(int value);

    Token clone();
}
