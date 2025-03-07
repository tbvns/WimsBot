import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class main {
    public static JFrame mainFrame;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        JFrame frame = new JFrame("Wims bot go brrrrrrr");
        mainFrame = frame;

        frame.setSize(100, 100);

        JPanel panel = new JPanel(){{
            add(new JButton("Login"){{
                addActionListener(a -> {
                    Scrapper.setup();
                });
            }});
        }};
        frame.setContentPane(panel);

        frame.setVisible(true);
    }
}
