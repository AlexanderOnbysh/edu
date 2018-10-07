import tokens.Token;

import java.awt.*;
import java.util.ArrayList;

public class Character {
    private int health;
    private Point position;
    private ArrayList<Token> tokens = new ArrayList<>();


    Character(int health, Point position) {
        this.health = health;
        this.position = position;
    }

    int getHealth() {
        return health;
    }

    Point getPosition() {
        return this.position;
    }

    void setPosition(Point newPosition) {
        this.position.setLocation(newPosition);
    }

    void addToken(Token token) {
        this.tokens.add(token);
    }

    int getArmor() {
        int armor = 0;
        for (Token token : this.tokens) {
            armor += token.getArmor();
        }
        return armor;
    }

    void increaseArmor(int inc) {
        for (Token token : this.tokens) {
            token.increaseArmor(inc);
        }
    }

    CharacterMemento saveMemento() {
        return new CharacterMemento(this.health, this.position, this.tokens);
    }

    void restoreState(Object characterMemento) {
        CharacterMemento state = (CharacterMemento) characterMemento;
        this.health = state.health;
        this.position = (Point) state.position.clone();
        this.tokens.clear();
        for (Token p : state.tokens) {
            this.tokens.add(p.clone());
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Character:");
        str.append(" health: ");
        str.append(this.health);
        str.append(" position: ");
        str.append(this.position.toString());
        str.append(" armor: ");
        str.append(this.getArmor());

        for (Token t : this.tokens) {
            str.append("\n\t");
            str.append(t.getName());
        }
        return str.toString();
    }

    public class CharacterMemento {
        int health;
        Point position;
        ArrayList<Token> tokens = new ArrayList<>();

        CharacterMemento(int health, Point position, ArrayList<Token> tokensToSave) {
            this.health = health;
            this.position = (Point) position.clone();
            for (Token p : tokensToSave) {
                this.tokens.add(p.clone());
            }
        }
    }
}
