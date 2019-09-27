import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alexon
 */
public interface BoxIterator<T> extends Iterator<T> {
    void addList(List<T> list);
}
