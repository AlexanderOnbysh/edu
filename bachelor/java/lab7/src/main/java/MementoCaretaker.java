/**
 * Created by alexon
 */
public class MementoCaretaker {
    private Object objMemento;

    public void saveState(Character character) {
        objMemento = character.saveMemento();
    }

    public void restoreState(Character character) {
        character.restoreState(objMemento);
    }

}
