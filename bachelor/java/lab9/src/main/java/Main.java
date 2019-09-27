
public class Main {
    public static void main(String[] args) {
        AbstractWidgetFactory widgetFactory;

        if (!System.getProperty("os.name").equals("Mac OS X")) {
            widgetFactory = new MacOSXWidgetFactory();
        } else {
            widgetFactory = new MsWindowsWidgetFactory();
        }

        Window window = WidgetFactory.getWindow(widgetFactory);
        window.setTitle("Hello");
    }
}