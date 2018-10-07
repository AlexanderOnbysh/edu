import tokens.Helmet;
import tokens.UselessBoots;

import java.awt.*;

/**
 * Created by alexon
 */
public class Main {
    public static void main(String[] args) {

        MementoCaretaker caretaker = new MementoCaretaker();

        Helmet item1 = new Helmet(10);
        UselessBoots item2 = new UselessBoots();

        Character character = new Character(100, new Point(1, 1));
        character.addToken(item1);
        character.addToken(item2);
        System.out.println("State 1");
        System.out.println(character.toString());
        caretaker.saveState(character);

        character.increaseArmor(15);
        character.setPosition(new Point(5, 5));
        System.out.println("State 2");
        System.out.println(character.toString());

        System.out.println("Restore state 1");
        caretaker.restoreState(character);
        System.out.println(character.toString());
    }
}
