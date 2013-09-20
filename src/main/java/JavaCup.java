import javax.swing.*;
import org.javahispano.javacup.gui.principal.PrincipalFrame;

public class JavaCup {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (Exception ignored) {
        }
        new PrincipalFrame();
    }
}
