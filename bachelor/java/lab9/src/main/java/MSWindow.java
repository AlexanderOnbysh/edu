import javax.swing.*;

public class MSWindow implements Window {

    private JFrame frame;

    MSWindow() {
        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label;

        label = new JLabel("Windows native window");
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 200, 400));
        panel.add(label);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        this.frame = frame;
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }
}