class WidgetFactory {
    static Window getWindow(AbstractWidgetFactory factory) {
        return factory.createWindow();
    }
}
