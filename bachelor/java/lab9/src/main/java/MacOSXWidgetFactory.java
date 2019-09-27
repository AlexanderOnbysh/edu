
public class MacOSXWidgetFactory implements AbstractWidgetFactory {
    public Window createWindow() {
        return new MacOSXWindow();
    }
}